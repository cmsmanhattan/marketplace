package com.cbsinc.cms.controllers;

/**
 * Status codes of a product offer as stored in the database.
 */
public interface OfferStatus {

	final static int PRODUCT_OFFER_NOTSUBMITED = -1;
	final static int PRODUCT_OFFER_SUBMITED = 0;
	final static int PRODUCT_OFFER_ACCEPTED = 1;
	final static int PRODUCT_OFFER_DECLINED = 2;
	final static int PRODUCT_COUNTER_OFFER = 3;

}
