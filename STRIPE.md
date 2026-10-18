# Stripe — интеграция и резервный канал

## Что сделано

Пополнение счёта (`PrePay.jsp → Pay.jsp`) теперь идёт через **Stripe
Checkout** как основной канал. Старый банковский шлюз
(`pgate.grabko.com`, форма в `pay.xsl`, опрос `CheckPaymentResult`) не
удалён и не изменён — он стал **резервным** и включается автоматически,
когда Stripe недоступен, выключен, не поддерживает валюту или пользователь
выбрал его сам.

Новый пакет `com.cbsinc.cms.payments`:

| Класс | Назначение |
|---|---|
| `PaymentConfig` | читает `payment.properties`; любой ключ переопределяется системным свойством или переменной окружения |
| `PaymentChannel` | `STRIPE` / `LEGACY` |
| `StripeClient` | два вызова Stripe API через `java.net.http` — создать Checkout Session, прочитать её |
| `StripeWebhookVerifier` | проверка подписи `Stripe-Signature` (HMAC-SHA256, окно времени, constant-time сравнение) |
| `PaymentSettlement` | идемпотентное зачисление: `account_hist` → `account` → `orders`, под блокировкой строки |
| `PaymentRouter` | выбор канала, circuit breaker, создание сессии |
| `StripeWebhookServlet` | `/stripe/webhook` — **основной путь зачисления** |
| `StripeReturnServlet` | `/stripe/return` — куда Stripe возвращает браузер |

Изменённые файлы: `PayAction`, `PayBean`, `OperationAmountBean`,
`FrontControllers` (исключение для `session_id`), `Pay.jsp`,
`PrePay.jsp`, `AccountHistory.jsp`, все 12 `pay.xsl`, `web.xml`,
`pom.xml` не трогался — зависимостей не добавлено (`org.json` уже есть).

Новые файлы: `src/payment.properties`, `sql/stripe_migration.sql`.

## Поток

```
PrePay.jsp ──POST Amount, currency_id──▶ Pay.jsp (PayAction)
                                              │
                                   addMoneyStart() → account_hist (complete=0, active=1)
                                              │
                                   PaymentRouter.route()
                                     ├─ Stripe? ──▶ POST /v1/checkout/sessions
                                     │                │ ok   → 303 → Stripe Checkout
                                     │                │ fail → счётчик, circuit, ↓
                                     └─ Legacy ──▶ Pay.jsp рендерит банковскую форму (как раньше)

Stripe ──checkout.session.completed──▶ /stripe/webhook ──▶ PaymentSettlement.settleSuccess()
Браузер ─────────────────────────────▶ /stripe/return  ──▶ GET /v1/checkout/sessions/{id}
                                                            paid? → settleSuccess() (no-op, если webhook успел)
                                                            → AccountHistory.jsp?pay_result=ok
```

`account_hist.id` передаётся в Stripe как `client_reference_id` и в
`metadata.account_hist_id`, поэтому webhook находит строку без какого-либо
состояния на нашей стороне. Между `account_hist` и Checkout Session связь
один-к-одному: `Idempotency-Key` выводится из id строки и суммы, повторный
запрос после сетевой ошибки возвращает ту же сессию, а не вторую.

## Резервный канал — когда включается

`PaymentRouter` отдаёт `LEGACY` в таком порядке:

1. `payment.primary = legacy` — всегда.
2. `stripe.enabled = false` или пустой `stripe.secret_key`.
3. Запрос с параметром `channel=legacy` (ссылка «Use bank gateway instead»
   в `pay.xsl`).
4. Валюта не входит в `stripe.currencies`. Крипто-коды из таблицы
   `currency` (`BTC`, `LTC`, `ETH`) в Stripe не уходят никогда.
5. Открыт circuit breaker: после `payment.circuit_failures` подряд
   неудач создания сессии все платежи идут в банк
   `payment.circuit_open_seconds` секунд. Иначе при падении Stripe каждый
   покупатель ждал бы таймаут.
6. Создание сессии бросило исключение и `payment.fallback_to_legacy = true`.

Покупатель переключения не видит: тот же `Pay.jsp`, только вместо
редиректа — форма банка. В логе каждое решение записано:
`Payment routed to legacy gateway: <причина>`.

## Настройка

### 1. Ключи

Секреты **не** в `payment.properties` — в окружении Tomcat:

```bash
export STRIPE_SECRET_KEY=sk_live_...
export STRIPE_WEBHOOK_SECRET=whsec_...
export STRIPE_PUBLIC_BASE_URL=https://shop.example.com
```

или `-Dstripe.secret_key=...` в `CATALINA_OPTS`. Файл нужен для
несекретных настроек и как шаблон.

`stripe.public_base_url` обязателен за reverse proxy: без него URL
возврата строится из `request.getScheme()/getServerName()`, а это адрес
Tomcat, а не сайта. С `X-Forwarded-Proto`/`X-Forwarded-Host` тоже
работает, но явное значение надёжнее.

### 2. Webhook в Stripe Dashboard

Developers → Webhooks → Add endpoint:

- URL: `https://shop.example.com/stripe/webhook`
- События: `checkout.session.completed`,
  `checkout.session.async_payment_succeeded`,
  `checkout.session.async_payment_failed`, `checkout.session.expired`,
  `charge.refunded`, `charge.dispute.created`, `charge.dispute.funds_reinstated`
  (последние три — возвраты и диспуты, проход 32)
- Signing secret → `STRIPE_WEBHOOK_SECRET`

Без секрета сервлет отвечает 503 на всё. Это намеренно: webhook,
принимающий неподписанные события, позволяет любому зачислить себе деньги.

### 3. Миграция БД (необязательно)

```bash
mysql cmsdb < sql/stripe_migration.sql
```

Добавляет `account_hist.PAY_CHANNEL` и `PAY_REF` (id сессии Stripe). Без
них зачисление работает, но сверка с Stripe Dashboard будет по суммам и
времени, а не по id. Код проверяет наличие колонок один раз при старте и
пишет предупреждение, если их нет.

### 4. Тест

Тестовые ключи `sk_test_…` + карта `4242 4242 4242 4242`. Для webhook
локально:

```bash
stripe listen --forward-to localhost:8080/stripe/webhook
```

Проверки после развёртывания:

```bash
# webhook без подписи — должен быть 400
curl -s -o /dev/null -w '%{http_code}\n' -X POST -d '{}' https://host/stripe/webhook

# возврат с чужим/выдуманным session_id — должен быть редирект на PrePay.jsp?pay_result=error
curl -s -o /dev/null -w '%{redirect_url}\n' 'https://host/stripe/return?result=success&account_hist_id=1&session_id=cs_test_fake'

# повтор события из Dashboard → в логе "already settled; ignoring duplicate"
```

## Идемпотентность и гонки

`PaymentSettlement` берёт `SELECT … FOR UPDATE` на строке `account_hist`,
и только если `complete = 0` — зачисляет. Webhook и страница возврата
могут прийти в любом порядке и сколько угодно раз; счёт увеличивается
ровно один раз. Stripe присылает события минимум один раз и позволяет
переотправить из Dashboard — всё это безопасно.

Если Stripe сообщил сумму или валюту, отличные от `account_hist`, строка
закрывается с `rezult_cd = mismatch`, счёт **не** пополняется, в лог
уходит ERROR. Это ситуация для ручного разбора, автоматически её решать
нельзя.

## Что нашлось попутно и исправлено

- **`OperationAmountBean.addMoneyStart`** склеивал в `INSERT` все
  HTTP-заголовки запроса (`user_header`). `User-Agent` с кавычкой ломал
  каждое пополнение, а специально составленный — переписывал запрос.
  Колонка `varchar(500)`, заголовки обычно длиннее — `INSERT` падал. Теперь
  экранируется и режется до 480.
- Тот же метод при откате **возвращал id** из sequence, и покупатель
  уходил в банк с номером заказа, которого нет в базе. Теперь `""`,
  `PayAction` проверяет.
- `Amount` в `PayAction` не проверялся: мусор → страница ошибки после
  создания строки; отрицательное число → отрицательное пополнение.
  Теперь: положительное, до двух знаков, не больше `MAX_TOPUP`.
- **Не исправлял, но обязан сказать:** `CheckPaymentResult.end_addmoney`
  (зачисление по старому шлюзу) считает
  `total_amount = amount + add_amount * rate`, где `amount` — локальная
  переменная, равная 0, а `rate` записывается как 0 при создании строки.
  То есть при каждом успешном платеже через банк `account.amount`
  становится **0**. `PaymentSettlement` эту арифметику не повторяет — там
  `новый баланс = текущий + add_amount` под блокировкой. Если резервный
  канал реально используется, `end_addmoney` стоит перевести на
  `PaymentSettlement.settleSuccess()` — это одна замена вызова.

## Ограничения

- Stripe SDK не подключён: в контейнере нет сети, проверить артефакт и
  его API нельзя. Клиент — 150 строк на `java.net.http` под два вызова
  REST. Если захотите SDK (`com.stripe:stripe-java`), заменяется только
  `StripeClient`.
- Компиляции не было — JRE без `javac`. Структура всех 435 файлов и
  сигнатуры вызываемых методов сверены по исходникам.
- Возвраты (refunds) и споры (disputes) не обрабатываются — их события
  игнорируются с 200. Если нужны — добавить ветки в `StripeWebhookServlet`
  и `PaymentSettlement.settleRefund()`.
- Валюта Stripe берётся из `currency.currency_cd`; курс `currency.rate`
  не применяется, как и в старом канале.
