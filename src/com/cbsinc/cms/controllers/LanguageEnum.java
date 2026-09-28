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
public enum LanguageEnum {

	UNKNOWN(-1), RU(1), EN(2);

	LanguageEnum(int id) {
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
			return "Russian";
		case 2:
			return "English";
		}

		return CurrencyType.UNKNOWN;
	}

	public String getCode() {
		switch (id) {
		case 1:
			return "ru";
		case 2:
			return "en";
		}

		return CurrencyType.UNKNOWN;
	}
}
