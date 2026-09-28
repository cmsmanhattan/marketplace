package com.cbsinc.cms.payments;

/**
 * Which channel a top-up is sent through.
 *
 * STRIPE - Stripe Checkout (hosted page, card + wallets), settled by webhook.
 * LEGACY - the original bank gateway form rendered by pay.xsl, settled by the
 * existing CheckPaymentResult polling.
 */
public enum PaymentChannel {
	STRIPE("stripe"), LEGACY("legacy");

	private final String code;

	PaymentChannel(String code) {
		this.code = code;
	}

	/** Short code stored in account_hist.pay_channel and shown in templates. */
	public String code() {
		return code;
	}
}
