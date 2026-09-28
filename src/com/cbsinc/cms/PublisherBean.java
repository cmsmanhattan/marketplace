package com.cbsinc.cms;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

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

@PageModel(Id = "publisherBeanId", scope = Scope.SESSION)
//@PageXsltView(jspName = "Productlist.jsp", xsltName = "Productlist.xsl", responseType = Type.XML)
public class PublisherBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -4418477184811109279L;

	static private Logger log = Logger.getLogger(PublisherBean.class);

	private String sample = "Start value";

	private String strSoftName = "";

	private String strSoftURL = "";

	public String strSoftDescription = "";

	private String strSoftVersion = "";

	private String strSoftCost = "0";

	private String strCurrency = "0";

	private String strOwner = "";

	private String serialNubmer = "";

	private String fileId = "-1";

	private String typeId = "-1";

	private String filename = "-1";

	private String phonetypeId = "-1";

	private String prognameId = "-1";

	private String imageId = "-1";

	private String bigimageId = "-1";

	private String catalogImageId = "-1";

	private String imgname = "";

	private String bigimgname = "";

	private String catalogImgname = "";

	private String userId = "-1";

	private String phonemodelId = "-1";

	private String licenceId = "-1";

	// private String catalog_id = "-1";

	private String siteId = "0";

	private String salelogicId = "0";

	private String cardNumber = "0";

	private String cardCode = "0";

	private String typeCardId = "0";

	private String productCodeId = "0";

	public String productFulldescription = "";

	private String portlettypeId = "0";

	private String parentPortlettypeId = "0";

	public String getParentPortlettypeId() {
		return parentPortlettypeId;
	}

	public void setParentPortlettypeId(String parentPortlettypeId) {
		this.parentPortlettypeId = parentPortlettypeId;
	}

	private String softId = "-1";

	private String creteria1Id = "0";

	private String creteria2Id = "0";

	private String creteria3Id = "0";

	private String creteria4Id = "0";

	private String creteria5Id = "0";

	private String creteria6Id = "0";

	private String creteria7Id = "0";

	private String creteria8Id = "0";

	private String creteria9Id = "0";

	private String creteria10Id = "0";

	private String dayId = "0";

	private String mountId = "0";

	private String yearId = "0";

	private String save = "false";

	private String action = "";

	// CHECKED

	private String strShowOffer = "false";
    private String showOfferChecked = "CHECKED" ;

	private String strShowAction = "false";
    private String showActionChecked = "CHECKED" ;

	private String strShowForum = "false";
	// private String show_forum_checked = "CHECKED" ;
	private String showForumChecked = "";

	private String strShowRatimg1 = "false";
	// private String strShow_ratimg1_checked = "CHECKED" ;
	private String strShowRatimg1Checked = "";

	private String strShowRatimg2 = "false";
	// private String strShow_ratimg2_checked = "CHECKED" ;
	private String strShowRatimg2Checked = "";

	private String strShowRatimg3 = "false";
	private String strShowRatimg3Checked = "";
	// private String strShow_ratimg3_checked = "CHECKED" ;

	private String strSoftName2 = "";

	private String amount1 = "0";
	private String amount2 = "0";
	private String amount3 = "0";

	private String jspUrl = "";

	private String strSearch2 = "";

	private String selectCatalogImages = "";
	private String selectBigImages = "";
	private String selectSmallImages = "";

	private String selectCatalogImageUrl = "";
	private String selectBigImageUrl = "";
	private String selectSmallImageUrl = "";
	private String selectFiles = "";

	private String criteria1Label = "";
	private String criteria2Label = "";
	private String criteria3Label = "";
	private String criteria4Label = "";
	private String criteria5Label = "";
	private String criteria6Label = "";
	private String criteria7Label = "";
	private String criteria8Label = "";
	private String criteria9Label = "";
	private String criteria10Label = "";

	private String creteria1Name = "";
	private String creteria2Name = "";
	private String creteria3Name = "";
	private String creteria4Name = "";
	private String creteria5Name = "";
	private String creteria6Name = "";
	private String creteria7Name = "";
	private String creteria8Name = "";
	private String creteria9Name = "";
	private String creteria10Name = "";

	private String selectCurrencyCd = "";
	private String selectTreeCatalog = "";
	private String selectCreteria1Id = "";
	private String selectCreteria2Id = "";
	private String selectCreteria3Id = "";
	private String selectCreteria4Id = "";
	private String selectCreteria5Id = "";
	private String selectCreteria6Id = "";
	private String selectCreteria7Id = "";
	private String selectCreteria8Id = "";
	private String selectCreteria9Id = "";
	private String selectCreteria10Id = "";

	private String selectDayfromId = "";
	private String selectMountfromId = "";
	private String selectYearfromId = "";
	private String selectDaytoId = "";
	private String selectMounttoId = "";
	private String selectYeartoId = "";

	private String selectPath = "";

	private String nameOfPage = "";

	public String getNameOfPage() {
		return nameOfPage;
	}

	public void setNameOfPage(String nameOfPage) {
		this.nameOfPage = nameOfPage;
	}

	// Access sample property
	public String getSample() {
		return sample;
	}

	// Access sample property
	public void setSample(String newValue) {
		if (newValue != null) {
			sample = newValue;
		}
	}

	public void setStrSoftName(String strSoftName) throws UnsupportedEncodingException {
		// this.strSoftName = norm(strSoftName) ;
		this.strSoftName = strSoftName;
	}

	public String getStrSoftName() {
		return strSoftName;
	}

	public void setStrSoftURL(String strSoftURL) {
		this.strSoftURL = strSoftURL;
	}

	public String getStrSoftURL() {
		return strSoftURL;
	}

	public void setStrSoftDescription(String strSoftDescription) {
		try {
			if (strSoftDescription.indexOf("\n") != -1) {
				strSoftDescription = "\n<r>" + strSoftDescription.replaceAll("\n", "</r><r>");
				if (strSoftDescription.substring(strSoftDescription.length() - 3, strSoftDescription.length())
						.equals("<r>")) {
					strSoftDescription = strSoftDescription.substring(0, strSoftDescription.length() - 3);
				} else
					strSoftDescription = strSoftDescription + "</r>";
			} else {
				strSoftDescription = "\n<r>" + strSoftDescription + "</r>";
			}

			this.strSoftDescription = strSoftDescription;

		} catch (Exception ex) {
			log.error(ex);
		}

	}

	public String getStrSoftDescription() {
		// BASE64Encoder encoder = new BASE64Encoder();
		// return encoder.encode(strSoftDescription.getBytes()) ;
		try {
			strSoftDescription = strSoftDescription.replaceAll("</r><r>", "\n");
			strSoftDescription = strSoftDescription.replaceAll("<r>", "");
			strSoftDescription = strSoftDescription.replaceAll("</r>", "");
		} catch (Exception ex) {
			log.error(ex);
		}
		return this.strSoftDescription.trim();
	}

	public void setStrSoftVersion(String strSoftVersion) {
		this.strSoftVersion = strSoftVersion;
	}

	public String getStrSoftVersion() {
		return strSoftVersion;
	}

	public void setStrSoftCost(String strSoftCost) {
		this.strSoftCost = strSoftCost;
	}

	public String getStrSoftCost() {
		return strSoftCost;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrOwner(String strOwner) {
		this.strOwner = strOwner;
	}

	public String getStrOwner() {
		return strOwner;
	}

	public void setSerialNubmer(String serialNubmer) {
		this.serialNubmer = serialNubmer;
	}

	public String getSerialNubmer() {
		return serialNubmer;
	}

	public void setFileId(String fileId) {
		if (fileId.equals(""))
			fileId = "-1";
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setPhonetypeId(String phonetypeId) {
		this.phonetypeId = phonetypeId;
	}

	public String getPhonetypeId() {
		return phonetypeId;
	}

	public void setPrognameId(String prognameId) {
		this.prognameId = prognameId;
	}

	public String getPrognameId() {
		return prognameId;
	}

	public void setImageId(String imageId) {
		if (imageId.equals(""))
			imageId = "-1";
		this.imageId = imageId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getImgname() {
		return imgname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhonemodelId() {
		return phonemodelId;
	}

	public void setPhonemodelId(String phonemodelId) {
		this.phonemodelId = phonemodelId;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSalelogicId() {
		return salelogicId;
	}

	public void setSalelogicId(String salelogicId) {
		if (salelogicId.equals(""))
			salelogicId = "0";
		this.salelogicId = salelogicId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getTypeCardId() {
		return typeCardId;
	}

	public void setTypeCardId(String typeCardId) {
		this.typeCardId = typeCardId;
	}

	public String getProductCodeId() {
		return productCodeId;
	}

	public void setProductCodeId(String productCodeId) {
		if (productCodeId.equals(""))
			productCodeId = "0";
		this.productCodeId = productCodeId;
	}

	public String getProductFulldescription() {

		try {
			productFulldescription = productFulldescription.replaceAll("</r><r>", "\n");
			productFulldescription = productFulldescription.replaceAll("<r>", "");
			productFulldescription = productFulldescription.replaceAll("</r>", "");
		} catch (Exception ex) {
			log.error(ex);
		}

		return productFulldescription.trim();
	}

	public void setProductFulldescription(String productFulldescription) {

		try {
			if (strSoftDescription.indexOf("\n") != -1) {
				productFulldescription = "\n<r>" + productFulldescription.replaceAll("\n", "</r><r>");
				if (productFulldescription
						.substring(productFulldescription.length() - 3, productFulldescription.length())
						.equals("<r>")) {
					productFulldescription = productFulldescription.substring(0,
							productFulldescription.length() - 3);
				} else
					productFulldescription = productFulldescription + "</r>";
			} else {
				productFulldescription = "\n<r>" + productFulldescription + "</r>";
			}
			productFulldescription = productFulldescription.replaceAll("&", "and");
			productFulldescription = productFulldescription.replaceAll("'", "`");
			this.productFulldescription = productFulldescription;

		} catch (Exception ex) {
			log.error(ex);
		}
	}

	public String getBigimageId() {
		return bigimageId;
	}

	public void setBigimageId(String bigimageId) {
		if (bigimageId.equals(""))
			bigimageId = "-1";
		this.bigimageId = bigimageId;
	}

	public String getBigimgname() {
		return bigimgname;
	}

	public void setBigimgname(String bigimgname) {
		this.bigimgname = bigimgname;
	}


	public String getCatalogImageId() {
		return catalogImageId;
	}

	public void setCatalogImageId(String catalogImageId) {
		this.catalogImageId = catalogImageId;
	}

	public String getCatalogImgname() {
		return catalogImgname;
	}

	public void setCatalogImgname(String catalogImgname) {
		this.catalogImgname = catalogImgname;
	}

	public String getPortlettypeId() {
		return portlettypeId;
	}

	public void setPortlettypeId(String portlettypeId) {
		this.portlettypeId = portlettypeId;
	}

	public String getSoftId() {
		return softId;
	}

	public void setSoftId(String softId) {
		this.softId = softId;
	}

	public String getCreteria10Id() {
		return creteria10Id;
	}

	public void setCreteria10Id(String creteria10Id) {
		if (creteria10Id.equals(""))
			creteria10Id = "0";
		this.creteria10Id = creteria10Id;
	}

	public String getCreteria1Id() {
		return creteria1Id;
	}

	public void setCreteria1Id(String creteria1Id) {
		if (creteria1Id.equals(""))
			creteria1Id = "0";
		this.creteria1Id = creteria1Id;
	}

	public String getCreteria2Id() {
		return creteria2Id;
	}

	public void setCreteria2Id(String creteria2Id) {
		if (creteria2Id.equals(""))
			creteria2Id = "0";
		this.creteria2Id = creteria2Id;
	}

	public String getCreteria3Id() {
		return creteria3Id;
	}

	public void setCreteria3Id(String creteria3Id) {
		if (creteria3Id.equals(""))
			creteria3Id = "0";
		this.creteria3Id = creteria3Id;
	}

	public String getCreteria4Id() {
		return creteria4Id;
	}

	public void setCreteria4Id(String creteria4Id) {
		if (creteria4Id.equals(""))
			creteria4Id = "0";
		this.creteria4Id = creteria4Id;
	}

	public String getCreteria5Id() {
		return creteria5Id;
	}

	public void setCreteria5Id(String creteria5Id) {
		if (creteria5Id.equals(""))
			creteria5Id = "0";
		this.creteria5Id = creteria5Id;
	}

	public String getCreteria6Id() {
		return creteria6Id;
	}

	public void setCreteria6Id(String creteria6Id) {
		if (creteria6Id.equals(""))
			creteria6Id = "0";
		this.creteria6Id = creteria6Id;
	}

	public String getCreteria7Id() {
		return creteria7Id;
	}

	public void setCreteria7Id(String creteria7Id) {
		if (creteria7Id.equals(""))
			creteria7Id = "0";
		this.creteria7Id = creteria7Id;
	}

	public String getCreteria8Id() {
		return creteria8Id;
	}

	public void setCreteria8Id(String creteria8Id) {
		if (creteria8Id.equals(""))
			creteria8Id = "0";
		this.creteria8Id = creteria8Id;
	}

	public String getCreteria9Id() {
		return creteria9Id;
	}

	public void setCreteria9Id(String creteria9Id) {
		if (creteria9Id.equals(""))
			creteria9Id = "0";
		this.creteria9Id = creteria9Id;
	}

	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	public String getMountId() {
		return mountId;
	}

	public void setMountId(String mountId) {
		this.mountId = mountId;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPartCriteria(String _criteriaId, boolean isSpace) {

		try {
			if (Long.parseLong(_criteriaId) < 0)
				_criteriaId = "0";
		} catch (Exception ex) {
			log.error(ex);
		}
		return !isSpace ? "" : " and catalog_id = " + _criteriaId;
	}




	public String getShowOfferChecked() {
		return showOfferChecked;
	}

	public void setShowOfferChecked(String showOfferChecked) {
		this.showOfferChecked = showOfferChecked;
	}

	public String getShowActionChecked() {
		return showActionChecked;
	}

	public void setShowActionChecked(String showActionChecked) {
		this.showActionChecked = showActionChecked;
	}

	public String getStrShowOffer() {
		if (strShowOffer.equals("true"))
			showOfferChecked = "CHECKED";
		if (strShowOffer.equals("false"))
			showOfferChecked = "";
		return strShowOffer;
	}

	public void setStrShowOffer(String strShowOffer) {
		this.strShowOffer = strShowOffer;
	}

	public String getStrShowAction() {
		if (strShowAction.equals("true"))
			showActionChecked = "CHECKED";
		if (strShowAction.equals("false"))
			showActionChecked = "";
		return strShowAction;
	}

	public void setStrShowAction(String strShowAction) {
		this.strShowAction = strShowAction;
	}

	public String getStrShowForum() {

		if (strShowForum.equals("true"))
			showForumChecked = "CHECKED";
		if (strShowForum.equals("false"))
			showForumChecked = "";

		return strShowForum;
	}

	public void setStrShowForum(String strShowForum) {
		this.strShowForum = strShowForum;
	}

	public String getStrSoftName2() {
		return strSoftName2;
	}

	public void setStrSoftName2(String strSoftName2) {
		this.strSoftName2 = strSoftName2;
	}

	public String getStrShowRatimg1() {
		return strShowRatimg1;
	}

	public void setStrShowRatimg1(String strShowRatimg1) {
		this.strShowRatimg1 = strShowRatimg1;
	}

	public String getStrShowRatimg2() {
		return strShowRatimg2;
	}

	public void setStrShowRatimg2(String strShowRatimg2) {
		this.strShowRatimg2 = strShowRatimg2;
	}

	public String getStrShowRatimg3() {
		return strShowRatimg3;
	}

	public void setStrShowRatimg3(String strShowRatimg3) {
		this.strShowRatimg3 = strShowRatimg3;
	}

	public String getAmount1() {
		return amount1;
	}

	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}

	public String getAmount2() {
		return amount2;
	}

	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}

	public String getAmount3() {
		return amount3;
	}

	public void setAmount3(String amount3) {
		this.amount3 = amount3;
	}

	public String getShowForumChecked() {
		if (strShowForum.equals("true")) {
			setShowForumChecked("CHECKED");
		}
		return showForumChecked;
	}

	public void setShowForumChecked(String showForumChecked) {
		this.showForumChecked = showForumChecked;
	}

	public String getStrShowRatimg1Checked() {

		if (strShowRatimg1.equals("true")) {
			setStrShowRatimg1Checked("CHECKED");
		}
		return strShowRatimg1Checked;
	}

	public void setStrShowRatimg1Checked(String strShowRatimg1Checked) {
		this.strShowRatimg1Checked = strShowRatimg1Checked;
	}

	public String getStrShowRatimg2Checked() {
		if (strShowRatimg2.equals("true")) {
			setStrShowRatimg2Checked("CHECKED");
		}
		return strShowRatimg2Checked;
	}

	public void setStrShowRatimg2Checked(String strShowRatimg2Checked) {
		this.strShowRatimg2Checked = strShowRatimg2Checked;
	}

	public String getStrShowRatimg3Checked() {
		if (strShowRatimg3.equals("true")) {
			setStrShowRatimg3Checked("CHECKED");
		}
		return strShowRatimg3Checked;
	}

	public void setStrShowRatimg3Checked(String strShowRatimg3Checked) {
		this.strShowRatimg3Checked = strShowRatimg3Checked;
	}

	public String getJspUrl() {
		return jspUrl;
	}

	public void setJspUrl(String jspUrl) {
		this.jspUrl = jspUrl;
	}

	public String getStrSearch2() {
		return strSearch2;
	}

	public void setStrSearch2(String strSearch2) {
		this.strSearch2 = strSearch2;
	}

	public String getCreteria1Name() {
		return creteria1Name;
	}

	public void setCreteria1Name(String creteria1Name) {
		this.creteria1Name = creteria1Name;
	}

	public String getCreteria10Name() {
		return creteria10Name;
	}

	public void setCreteria10Name(String creteria10Name) {
		this.creteria10Name = creteria10Name;
	}

	public String getCreteria2Name() {
		return creteria2Name;
	}

	public void setCreteria2Name(String creteria2Name) {
		this.creteria2Name = creteria2Name;
	}

	public String getCreteria3Name() {
		return creteria3Name;
	}

	public void setCreteria3Name(String creteria3Name) {
		this.creteria3Name = creteria3Name;
	}

	public String getCreteria4Name() {
		return creteria4Name;
	}

	public void setCreteria4Name(String creteria4Name) {
		this.creteria4Name = creteria4Name;
	}

	public String getCreteria5Name() {
		return creteria5Name;
	}

	public void setCreteria5Name(String creteria5Name) {
		this.creteria5Name = creteria5Name;
	}

	public String getCreteria6Name() {
		return creteria6Name;
	}

	public void setCreteria6Name(String creteria6Name) {
		this.creteria6Name = creteria6Name;
	}

	public String getCreteria7Name() {
		return creteria7Name;
	}

	public void setCreteria7Name(String creteria7Name) {
		this.creteria7Name = creteria7Name;
	}

	public String getCreteria8Name() {
		return creteria8Name;
	}

	public void setCreteria8Name(String creteria8Name) {
		this.creteria8Name = creteria8Name;
	}

	public String getCreteria9Name() {
		return creteria9Name;
	}

	public void setCreteria9Name(String creteria9Name) {
		this.creteria9Name = creteria9Name;
	}

	public String getCriteria1Label() {
		return criteria1Label;
	}

	public void setCriteria1Label(String criteria1Label) {
		this.criteria1Label = criteria1Label;
	}

	public String getCriteria10Label() {
		return criteria10Label;
	}

	public void setCriteria10Label(String criteria10Label) {
		this.criteria10Label = criteria10Label;
	}

	public String getCriteria2Label() {
		return criteria2Label;
	}

	public void setCriteria2Label(String criteria2Label) {
		this.criteria2Label = criteria2Label;
	}

	public String getCriteria3Label() {
		return criteria3Label;
	}

	public void setCriteria3Label(String criteria3Label) {
		this.criteria3Label = criteria3Label;
	}

	public String getCriteria4Label() {
		return criteria4Label;
	}

	public void setCriteria4Label(String criteria4Label) {
		this.criteria4Label = criteria4Label;
	}

	public String getCriteria5Label() {
		return criteria5Label;
	}

	public void setCriteria5Label(String criteria5Label) {
		this.criteria5Label = criteria5Label;
	}

	public String getCriteria6Label() {
		return criteria6Label;
	}

	public void setCriteria6Label(String criteria6Label) {
		this.criteria6Label = criteria6Label;
	}

	public String getCriteria7Label() {
		return criteria7Label;
	}

	public void setCriteria7Label(String criteria7Label) {
		this.criteria7Label = criteria7Label;
	}

	public String getCriteria8Label() {
		return criteria8Label;
	}

	public void setCriteria8Label(String criteria8Label) {
		this.criteria8Label = criteria8Label;
	}

	public String getCriteria9Label() {
		return criteria9Label;
	}

	public void setCriteria9Label(String criteria9Label) {
		this.criteria9Label = criteria9Label;
	}

	public String getSelectBigImages() {
		return selectBigImages;
	}

	public void setSelectBigImages(String selectBigImages) {
		this.selectBigImages = selectBigImages;
	}


	public String getSelectCatalogImages() {
		return selectCatalogImages;
	}

	public void setSelectCatalogImages(String selectCatalogImages) {
		this.selectCatalogImages = selectCatalogImages;
	}

	public String getSelectCreteria1Id() {
		return selectCreteria1Id;
	}

	public void setSelectCreteria1Id(String selectCreteria1Id) {
		this.selectCreteria1Id = selectCreteria1Id;
	}

	public String getSelectCreteria10Id() {
		return selectCreteria10Id;
	}

	public void setSelectCreteria10Id(String selectCreteria10Id) {
		this.selectCreteria10Id = selectCreteria10Id;
	}

	public String getSelectCreteria2Id() {
		return selectCreteria2Id;
	}

	public void setSelectCreteria2Id(String selectCreteria2Id) {
		this.selectCreteria2Id = selectCreteria2Id;
	}

	public String getSelectCreteria3Id() {
		return selectCreteria3Id;
	}

	public void setSelectCreteria3Id(String selectCreteria3Id) {
		this.selectCreteria3Id = selectCreteria3Id;
	}

	public String getSelectCreteria4Id() {
		return selectCreteria4Id;
	}

	public void setSelectCreteria4Id(String selectCreteria4Id) {
		this.selectCreteria4Id = selectCreteria4Id;
	}

	public String getSelectCreteria5Id() {
		return selectCreteria5Id;
	}

	public void setSelectCreteria5Id(String selectCreteria5Id) {
		this.selectCreteria5Id = selectCreteria5Id;
	}

	public String getSelectCreteria6Id() {
		return selectCreteria6Id;
	}

	public void setSelectCreteria6Id(String selectCreteria6Id) {
		this.selectCreteria6Id = selectCreteria6Id;
	}

	public String getSelectCreteria7Id() {
		return selectCreteria7Id;
	}

	public void setSelectCreteria7Id(String selectCreteria7Id) {
		this.selectCreteria7Id = selectCreteria7Id;
	}

	public String getSelectCreteria8Id() {
		return selectCreteria8Id;
	}

	public void setSelectCreteria8Id(String selectCreteria8Id) {
		this.selectCreteria8Id = selectCreteria8Id;
	}

	public String getSelectCreteria9Id() {
		return selectCreteria9Id;
	}

	public void setSelectCreteria9Id(String selectCreteria9Id) {
		this.selectCreteria9Id = selectCreteria9Id;
	}

	public String getSelectCurrencyCd() {
		return selectCurrencyCd;
	}

	public void setSelectCurrencyCd(String selectCurrencyCd) {
		this.selectCurrencyCd = selectCurrencyCd;
	}

	public String getSelectDayfromId() {
		return selectDayfromId;
	}

	public void setSelectDayfromId(String selectDayfromId) {
		this.selectDayfromId = selectDayfromId;
	}

	public String getSelectDaytoId() {
		return selectDaytoId;
	}

	public void setSelectDaytoId(String selectDaytoId) {
		this.selectDaytoId = selectDaytoId;
	}

	public String getSelectMountfromId() {
		return selectMountfromId;
	}

	public void setSelectMountfromId(String selectMountfromId) {
		this.selectMountfromId = selectMountfromId;
	}

	public String getSelectMounttoId() {
		return selectMounttoId;
	}

	public void setSelectMounttoId(String selectMounttoId) {
		this.selectMounttoId = selectMounttoId;
	}

	public String getSelectPath() {
		return selectPath;
	}

	public void setSelectPath(String selectPath) {
		this.selectPath = selectPath;
	}

	public String getSelectSmallImages() {
		return selectSmallImages;
	}

	public void setSelectSmallImages(String selectSmallImages) {
		this.selectSmallImages = selectSmallImages;
	}

	public String getSelectTreeCatalog() {
		return selectTreeCatalog;
	}

	public void setSelectTreeCatalog(String selectTreeCatalog) {
		this.selectTreeCatalog = selectTreeCatalog;
	}

	public String getSelectYearfromId() {
		return selectYearfromId;
	}

	public void setSelectYearfromId(String selectYearfromId) {
		this.selectYearfromId = selectYearfromId;
	}

	public String getSelectYeartoId() {
		return selectYeartoId;
	}

	public void setSelectYeartoId(String selectYeartoId) {
		this.selectYeartoId = selectYeartoId;
	}

	public String getSelectBigImageUrl() {
		return selectBigImageUrl;
	}

	public void setSelectBigImageUrl(String selectBigImageUrl) {
		this.selectBigImageUrl = selectBigImageUrl;
	}


	public String getSelectCatalogImageUrl() {
		return selectCatalogImageUrl;
	}

	public void setSelectCatalogImageUrl(String selectCatalogImageUrl) {
		this.selectCatalogImageUrl = selectCatalogImageUrl;
	}

	public String getSelectFiles() {
		return selectFiles;
	}

	public void setSelectFiles(String selectFiles) {
		this.selectFiles = selectFiles;
	}

	public String getSelectSmallImageUrl() {
		return selectSmallImageUrl;
	}

	public void setSelectSmallImageUrl(String selectSmallImageUrl) {
		this.selectSmallImageUrl = selectSmallImageUrl;
	}

}
