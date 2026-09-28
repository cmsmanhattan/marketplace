package com.cbsinc.cms.dto.pages.registration;

import java.util.List;

import com.cbsinc.cms.dto.CityItem;
import com.cbsinc.cms.dto.CountryItem;
import com.cbsinc.cms.dto.CurrenciesItem;
import com.cbsinc.cms.dto.MenuItem;
import com.cbsinc.cms.dto.SiteItem;

public class CMSRegistrationPageModel {

	String version;
	String name;

	String title;
	String subjectSite;
	String siteName;
	String host;
	String domain;
	String login;
	String passwdord;
	String firstname;
	String lastname;
	String company;
	String email;
	String phone;
	String mphone;
	String fax;
	String icq;
	String website;
	String question;
	String answer;
	String cityId;
	String siteId;
	String message;
	String countryId;
	String currencyId;

	List<SiteItem> site;

	List<CountryItem> country;

	List<CityItem> city;

	List<CurrenciesItem> currencies;

	List<MenuItem> menu;

}
