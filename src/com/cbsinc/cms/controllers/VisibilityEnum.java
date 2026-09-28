package com.cbsinc.cms.controllers;

/**
 *
 * query = "INSERT INTO typesoft(type_id, active, user_id, tax, type_lable,
 * type_desc) VALUES (2, true, 1, 0.0, 'Make item visible in Marker place',
 * 'Make item visible in Marker place')";
 *
 *
 * @author konst
 *
 */
public enum VisibilityEnum {

	UNKNOWN(-1), DEFAUILT_MODE(0), HIDE(1), MARKET_PLACE(2), YOUR_PAGE(3);

	VisibilityEnum(int id) {
		this.id = id;
	}

	private int id = 0;

	public int getId() {
		return id;
	}

	public String getDescr() {
		switch (id) {
		case 0:
			return "Default mode";
		case 1:
			return "Make item invisible on your page";
		case 2:
			return "Make item visible in Marker place";
		case 3:
			return "Make item visible on your page";

		}

		return CurrencyType.UNKNOWN;
	}

}
