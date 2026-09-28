package com.cbsinc.cms.controllers;

/**
 * "Action" in the original code, the database (ACTION_BID_STATUS_ID, table
 * actionbidstatus, catalog -13), the JSP name ActionBids.jsp and the
 * request values do_bid / cancel_action / accept_win_bid all mean AUCTION,
 * not an Action controller. Java identifiers were renamed to "auction";
 * database and template names were left as they are.
 */
public interface AuctionBidStatus {

	final static int PRODUCT_AUCTION_BID_NOTSUBMITED = -1;
	final static int PRODUCT_AUCTION_BID_SUBMITED = 0;
	final static int PRODUCT_AUCTION_BID_CANCELED = 1;
	final static int PRODUCT_AUCTION_BID_WON = 2;
	final static int PRODUCT_AUCTION_BID_DID_NOT_WIN = 3;

}
