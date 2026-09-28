package com.cbsinc.cms.controllers;

/**
 *
 * final static String USD = "1"; // U.S Dollar final static String EUR = "2";
 * // EURO final static String RUB = "3"; // RF Ruble final static String CNY =
 * "4"; // China money final static String JPY = "4"; // JPY ¥ - Japan Yen final
 * static String TRY = "5"; // Turkey Lira final static String MXN = "6"; //
 * Mexico Peso final static String CAD = "7"; // Canada Dollar final static
 * String GBP = "8"; // United Kingdom Pound final static String BRL = "9"; //
 * BRL R$ - Brazil Real final static String INR = "10"; // INR ₹ - India Rupee
 * final static String BTC = "11"; // Bitcoin final static String LTC = "12"; //
 * Litecoin final static String ETH = "13"; // Ethereum final static String
 * PYUSD = "14"; // PayPal Dollar final static String CBDC = "15"; // U.S
 * Digital Dollar
 *
 * @author konst
 *
 */
public enum CurrencyEnum {

	UNKNOWN(-1), USD(1), EUR(2), RUB(3), CNY(4), JPY(5), TRY(6), MXN(7), CAD(8), GBP(9), BRL(10), INR(11), BTC(12),
	LTC(13), ETH(14), PYUSD(15), CBDC(16);

	CurrencyEnum(int id) {
		this.id = id;
	}

	private int id = 0;

	public int getId() {
		return id;
	}

	public String getStrId() {
		return String.valueOf(id);
	}

	public String getDescr() {
		switch (id) {
		case 1:
			return CurrencyType.USD;
		case 2:
			return CurrencyType.EUR;
		case 3:
			return CurrencyType.RUB;
		case 4:
			return CurrencyType.CNY;
		case 5:
			return CurrencyType.JPY;
		case 6:
			return CurrencyType.TRY;
		case 7:
			return CurrencyType.MXN;
		case 8:
			return CurrencyType.GBP;
		case 9:
			return CurrencyType.BRL;
		case 10:
			return CurrencyType.INR;
		case 11:
			return CurrencyType.BTC;
		case 12:
			return CurrencyType.LTC;
		case 13:
			return CurrencyType.ETH;
		case 14:
			return CurrencyType.PYUSD;
		case 15:
			return CurrencyType.CBDC;

		}

		return CurrencyType.UNKNOWN;
	}

}
