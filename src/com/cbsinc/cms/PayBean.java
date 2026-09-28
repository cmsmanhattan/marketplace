package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.PageXsltView;
import com.cbsinc.cms.annotations.Scope;
import com.cbsinc.cms.annotations.Type;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code. You can not use it and you
 * cannot change it without written permission from Konstantin Grabko Email:
 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002-2025
 * </p>
 * <p>
 * Company: CENTER BUSINESS SOLUTIONS INC
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

@PageXsltView(jspName = "Pay.jsp", xsltName = "pay.xsl", responseType = Type.XML)
@PageModel(Id = "payBeanId", scope = Scope.SESSION)
public class PayBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -5025445874531411579L;

	static private Logger log = Logger.getLogger(PayBean.class);

	public String getCurrencyCode(String currencyId) {
		String currencyCd = "";
		// String query = "SELECT account.amount, currency.currency_lable,
		// currency.currency_desc FROM account LEFT OUTER JOIN public.currency
		// ON account.currency_id = currency.currency_id WHERE account.user_id =
		// " + currency_id ;
		String query = "SELECT  currency.currency_cd FROM currency WHERE currency.currency_id = " + currencyId;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			currencyCd = (String) Adp.getValueAt(0, 0);
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

		return currencyCd;
	}

	public String getCurrencyLable(String currencyId) {
		String currencyCd = "";
		// String query = "SELECT account.amount, currency.currency_lable,
		// currency.currency_desc FROM account LEFT OUTER JOIN public.currency
		// ON account.currency_id = currency.currency_id WHERE account.user_id =
		// " + currency_id ;
		String query = "SELECT  currency.currency_lable FROM currency WHERE currency.currency_id = " + currencyId;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			currencyCd = (String) Adp.getValueAt(0, 0);
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

		return currencyCd;
	}

	public String getPaySystemCode(String paysystemId) {
		String paysystemCd = "";
		String query = "SELECT  paysystem.paysystem_cd FROM paysystem WHERE paysystem.paysystem_id = " + paysystemId;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			paysystemCd = (String) Adp.getValueAt(0, 0);
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

		setPaySystem(paysystemCd);
		return paysystemCd;
	}

	public void setPaySystem(String paysystemCd) {
		paysystemCd = paysystemCd.trim();
		choosenTypeCreditCard = "0";
		cardPayment = "0";
		walletPayment = "0";
		webMoneyPayment = "0";
		rapidaPayment = "0";
		payCashPayment = "0";
		EPortPayment = "0";
		kreditPilotPayment = "0";
		if ("CardPaymentVisa".compareTo(paysystemCd) == 0) {
			cardPayment = "1";
			choosenTypeCreditCard = "1";
		} else if ("CardPaymentMaster".compareTo(paysystemCd) == 0) {
			cardPayment = "1";
			choosenTypeCreditCard = "2";
		} else if ("WebMoneyPayment".compareTo(paysystemCd) == 0) {
			webMoneyPayment = "1";
			walletPayment = "1";
		} else if ("RapidaPayment".compareTo(paysystemCd) == 0) {
			rapidaPayment = "1";
			walletPayment = "1";
		} else if ("PayCashPayment".compareTo(paysystemCd) == 0) {
			payCashPayment = "1";
			walletPayment = "1";
		} else if ("EPortPayment".compareTo(paysystemCd) == 0) {
			EPortPayment = "1";
			walletPayment = "1";
		} else if ("KreditPilotPayment".compareTo(paysystemCd) == 0) {
			kreditPilotPayment = "1";
			walletPayment = "1";
		}

	}

	public void setPaySystemOld(String paysystemCd) {
		paysystemCd = paysystemCd.trim();
		if ("CardPaymentVisa".compareTo(paysystemCd) == 0) {
			cardPayment = "1";
			choosenTypeCreditCard = "1";
			return;
		} else
			cardPayment = "0";
		if ("CardPaymentMaster".compareTo(paysystemCd) == 0) {
			cardPayment = "1";
			choosenTypeCreditCard = "2";
			return;
		} else
			cardPayment = "0";
		if ("WalletPayment".compareTo(paysystemCd) == 0) {
			walletPayment = "1";
			return;
		} else
			walletPayment = "0";
		if ("WebMoneyPayment".compareTo(paysystemCd) == 0) {
			webMoneyPayment = "1";
			return;
		} else
			webMoneyPayment = "0";
		if ("RapidaPayment".compareTo(paysystemCd) == 0) {
			rapidaPayment = "1";
			return;
		} else
			rapidaPayment = "0";
		if ("PayCashPayment".compareTo(paysystemCd) == 0) {
			payCashPayment = "1";
			return;
		} else
			payCashPayment = "0";
		if ("EPortPayment".compareTo(paysystemCd) == 0) {
			EPortPayment = "1";
			return;
		} else
			EPortPayment = "0";
		if ("KreditPilotPayment".compareTo(paysystemCd) == 0) {
			kreditPilotPayment = "1";
			return;
		} else
			kreditPilotPayment = "0";
	}

	public void setStatusInrocess(String orderId) throws Exception {

		String query = "";
		QueryManager Adp = new QueryManager();
		try {
			Adp.beginTransaction();

			query = "update orders  set paystatus_id = 2 where order_id = " + orderId;
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public float string2Float(String s) {
		if (s == null)
			return 0.0F;
		float d;
		try {
			d = Float.parseFloat(s);
		} catch (NumberFormatException ex) {
			d = -1F;
		}
		return d;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountHistId() {
		return accountHistId;
	}

	public void setAccountHistId(String accountHistId) {
		this.accountHistId = accountHistId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getPaysystemId() {
		return paysystemId;
	}

	public void setPaysystemId(String paysystemId) {
		this.paysystemId = paysystemId;
	}

	public String getSubtotalP() {
		return subtotalP;
	}

	public void setSubtotalP(String subtotalP) {
		this.subtotalP = subtotalP;
	}

	public String getShopIDP() {
		return shopIDP;
	}

	public void setShopIDP(String shopIDP) {
		this.shopIDP = shopIDP;
	}

	public String getCurrencyCd() {
		return currencyCd;
	}

	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	public String getPaysystemCd() {
		return paysystemCd;
	}

	public void setPaysystemCd(String paysystemCd) {
		this.paysystemCd = paysystemCd;
	}

	public String getCardPayment() {
		return cardPayment;
	}

	public void setCardPayment(String cardPayment) {
		this.cardPayment = cardPayment;
	}

	public String getWalletPayment() {
		return walletPayment;
	}

	public void setWalletPayment(String walletPayment) {
		this.walletPayment = walletPayment;
	}

	public String getWebMoneyPayment() {
		return webMoneyPayment;
	}

	public void setWebMoneyPayment(String webMoneyPayment) {
		this.webMoneyPayment = webMoneyPayment;
	}

	public String getRapidaPayment() {
		return rapidaPayment;
	}

	public void setRapidaPayment(String rapidaPayment) {
		this.rapidaPayment = rapidaPayment;
	}

	public String getPayCashPayment() {
		return payCashPayment;
	}

	public void setPayCashPayment(String payCashPayment) {
		this.payCashPayment = payCashPayment;
	}

	public String getEPortPayment() {
		return EPortPayment;
	}

	public void setEPortPayment(String EPortPayment) {
		this.EPortPayment = EPortPayment;
	}

	public String getKreditPilotPayment() {
		return kreditPilotPayment;
	}

	public void setKreditPilotPayment(String kreditPilotPayment) {
		this.kreditPilotPayment = kreditPilotPayment;
	}

	public String getChoosenTypeCreditCard() {
		return choosenTypeCreditCard;
	}

	public void setChoosenTypeCreditCard(String choosenTypeCreditCard) {
		this.choosenTypeCreditCard = choosenTypeCreditCard;
	}

	private String description;

	private String amount;

	private String accountHistId;

	private String currencyId;

	private String paysystemId;

	private String subtotalP;

	private String shopIDP;

	private String currencyCd;

	private String paysystemCd;

	// Payment routing (see com.cbsinc.cms.payments.PaymentRouter).
	private String paymentChannel = "legacy";
	private String paymentReason = "";
	private String stripeCheckoutUrl = "";
	private String stripeSessionId = "";

	private String cardPayment = "0";

	private String walletPayment = "0";

	private String webMoneyPayment = "0";

	private String rapidaPayment = "0";

	private String payCashPayment = "0";

	private String EPortPayment = "0";

	private String kreditPilotPayment = "0";

	private String choosenTypeCreditCard = "1";


	/** "stripe" or "legacy": which channel this top-up was sent through. */
	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	/** Why the router picked the channel; shown in templates for operators. */
	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	/** Stripe Checkout URL, non-empty only when paymentChannel is "stripe". */
	public String getStripeCheckoutUrl() {
		return stripeCheckoutUrl;
	}

	public void setStripeCheckoutUrl(String stripeCheckoutUrl) {
		this.stripeCheckoutUrl = stripeCheckoutUrl;
	}

	public String getStripeSessionId() {
		return stripeSessionId;
	}

	public void setStripeSessionId(String stripeSessionId) {
		this.stripeSessionId = stripeSessionId;
	}

}
