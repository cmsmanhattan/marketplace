package com.cbsinc.cms.export.hsql;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
 * собственностью.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.QueryManager;

public class Export {

	static private Logger log = Logger.getLogger(Export.class);

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */

	// INSERT INTO CRETERIA1
	// VALUES(0,'\u041d\u0435\u0442',TRUE,NULL,0,0,'\u0412\u044b\u0443\u0441\u043a')
	// INSERT INTO FILE
	// VALUES(102,'jtextproc0.2.zip',NULL,2,NULL,'C:/apache-tomcat-6.0.7/webapps/ishop/files/102.zip')
	// INSERT INTO SOFT
	// VALUES(119,'test17898','ss','',0.0E0,3,'',-1,NULL,NULL,TRUE,2,-1,NULL,-1,100,NULL,NULL,NULL,-1,-1,103,0,105,0,0,NULL,NULL,FALSE,FALSE,0,0,'rrgtr',2,'t',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)
	// INSERT INTO CITY
	// VALUES(2,95,'\u041c\u043e\u0441\u043a\u0432\u0430','\u041c\u043e\u0441\u043a\u0432\u0430',1,1)
	// INSERT INTO IMAGES VALUES(101,'bg-actor.gif','imgpositions/101.gif',2)
	// INSERT INTO BIG_IMAGES VALUES(100,'112.jpg','big_imgpositions/100.jpg',2)
	// INSERT INTO CATALOG
	// VALUES(9,100,NULL,'\u0420\u043e\u043a',TRUE,1.0E0,NULL,2,NULL,0,NULL)

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		QueryManager qm = null;
		qm = new QueryManager();
		qm.beginTransaction();
		FileOutputStream fs = new FileOutputStream("hsql." + new java.util.Date().getTime());
		String query = "";
		StringBuilder buff = new StringBuilder();

		query = "select  soft_id , name ,  description ,  version ,"
				+ " cost ,  currency ,  serial_nubmer ,	  file_id ,  type_id , "
				+ " levelup , active ,  user_id ,  phonetype_id ,  progname_id , "
				+ " image_id ,  bigimage_id ,  weight ,  count ,  lang_id , "
				+ " phonemodel_id ,  licence_id ,  catalog_id ,  salelogic_id ,"
				+ " tree_id , card_number ,  card_code ,  start_dial_time ,	  end_dial_time , "
				+ " start_activation_card ,  end_activation_card , type_card_id ,  product_code , "
				+ " fulldescription , site_id ,  search , portlettype_id ,  creteria1_id , "
				+ " creteria2_id ,  creteria3_id ,  creteria4_id ,  creteria5_id ,  creteria6_id , "
				+ " creteria7_id ,  creteria8_id , creteria9_id ,  creteria10_id from soft order by soft_id ";

		try {
			fs.write("-- soft --\n\n".getBytes());

			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {

				String softId = qm.getValueAt(i, 0).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 0);
				String name = qm.getValueAt(i, 1).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 1);
				name = name.replaceAll("'", "^");
				String description = qm.getValueAt(i, 2).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 2);
				description = description.replaceAll("'", "^");

				String version = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String cost = qm.getValueAt(i, 4).length() == 0 ? "0" : "" + qm.getValueAt(i, 4);
				String currency = qm.getValueAt(i, 5).length() == 0 ? "3" : "" + qm.getValueAt(i, 5);
				String serialNubmer = qm.getValueAt(i, 6).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 6);
				String fileId = qm.getValueAt(i, 7).length() == 0 ? "-1" : "" + qm.getValueAt(i, 7);
				String typeId = qm.getValueAt(i, 8).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 8);
				String levelup = qm.getValueAt(i, 9).length() == 0 ? "0" : "" + qm.getValueAt(i, 9);
				String active = qm.getValueAt(i, 10).equals("t") ? "TRUE" : "FALSE";
				String userId = qm.getValueAt(i, 11).length() == 0 ? "1" : "" + qm.getValueAt(i, 11);
				String phonetypeId = qm.getValueAt(i, 12).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 12);
				String prognameId = qm.getValueAt(i, 13).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 13);
				String imageId = qm.getValueAt(i, 14).length() == 0 ? "-1" : "" + qm.getValueAt(i, 14);
				String bigimageId = qm.getValueAt(i, 15).length() == 0 ? "-1" : "" + qm.getValueAt(i, 15);
				String weight = qm.getValueAt(i, 16).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 16);
				String count = qm.getValueAt(i, 17).length() == 0 ? "1" : "" + qm.getValueAt(i, 17);
				String langId = qm.getValueAt(i, 18).length() == 0 ? "1" : "" + qm.getValueAt(i, 18);
				String phonemodelId = qm.getValueAt(i, 19).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 19);
				String licenceId = qm.getValueAt(i, 20).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 20);
				String catalogId = qm.getValueAt(i, 21).length() == 0 ? "0" : "" + qm.getValueAt(i, 21);
				String salelogicId = qm.getValueAt(i, 22).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 22);
				String treeId = qm.getValueAt(i, 23).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 23);
				String cardNumber = qm.getValueAt(i, 24).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 24);
				String cardCode = qm.getValueAt(i, 25).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 25);
				String startDialTime = qm.getValueAt(i, 26).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 26);
				String endDialTime = qm.getValueAt(i, 27).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 27);
				String startActivationCard = qm.getValueAt(i, 28).equals("t") ? "TRUE" : "FALSE";
				String endActivationCard = qm.getValueAt(i, 29).equals("t") ? "TRUE" : "FALSE";
				String typeCardId = qm.getValueAt(i, 30).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 30);
				String productCode = qm.getValueAt(i, 31).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 31);
				String fulldescription = qm.getValueAt(i, 32).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 32);
				fulldescription = fulldescription.replaceAll("'", "^");
				String siteId = qm.getValueAt(i, 33).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 33);
				String search = qm.getValueAt(i, 34).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 34);
				String portlettypeId = qm.getValueAt(i, 35).length() == 0 ? "0" : "" + qm.getValueAt(i, 35);
				String creteria1Id = qm.getValueAt(i, 36).length() == 0 ? "0" : "" + qm.getValueAt(i, 36);
				String creteria2Id = qm.getValueAt(i, 37).length() == 0 ? "0" : "" + qm.getValueAt(i, 37);
				String creteria3Id = qm.getValueAt(i, 38).length() == 0 ? "0" : "" + qm.getValueAt(i, 38);
				String creteria4Id = qm.getValueAt(i, 39).length() == 0 ? "0" : "" + qm.getValueAt(i, 39);
				String creteria5Id = qm.getValueAt(i, 40).length() == 0 ? "0" : "" + qm.getValueAt(i, 40);
				String creteria6Id = qm.getValueAt(i, 41).length() == 0 ? "0" : "" + qm.getValueAt(i, 41);
				String creteria7Id = qm.getValueAt(i, 42).length() == 0 ? "0" : "" + qm.getValueAt(i, 42);
				String creteria8Id = qm.getValueAt(i, 43).length() == 0 ? "0" : "" + qm.getValueAt(i, 43);
				String creteria9Id = qm.getValueAt(i, 44).length() == 0 ? "0" : "" + qm.getValueAt(i, 44);
				String creteria10Id = qm.getValueAt(i, 45).length() == 0 ? "0" : "" + qm.getValueAt(i, 45);

				buff.append("INSERT INTO SOFT VALUES(" + softId + ",'" + name + "','" + description + "','" + version
						+ "'," + cost + "," + currency + ",'" + serialNubmer + "'," + fileId + "," + typeId + ","
						+ levelup + "," + active + "," + userId + "," + phonetypeId + "," + prognameId + ","
						+ imageId + "," + bigimageId + "," + weight + "," + count + "," + langId + ","
						+ phonemodelId + "," + licenceId + "," + catalogId + "," + salelogicId + "," + treeId + ","
						+ cardNumber + "," + cardCode + "," + startDialTime + "," + endDialTime + ","
						+ startActivationCard + "," + endActivationCard + "," + typeCardId + "," + productCode
						+ ",'" + fulldescription + "'," + siteId + ",'" + search + "'," + portlettypeId + ","
						+ creteria1Id + "," + creteria2Id + "," + creteria3Id + "," + creteria4Id + ","
						+ creteria5Id + "," + creteria6Id + "," + creteria7Id + "," + creteria8Id + ","
						+ creteria9Id + "," + creteria10Id + ") \n");
			}
			fs.write(buff.toString().getBytes());

			query = "select catalog_id , user_id , lable , active ,  tax ,  description , "
					+ "site_id , lang_id , parent_id , catalog_image_id  from catalog";

			fs.write("-- Calatalog --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String userId = qm.getValueAt(i, 1).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 1);
				String active = qm.getValueAt(i, 3).equals("t") ? "TRUE" : "FALSE";
				String tax = qm.getValueAt(i, 4).length() == 0 ? "1" : "" + qm.getValueAt(i, 4);
				String descript = qm.getValueAt(i, 5).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 5);
				String lang = qm.getValueAt(i, 7).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 7);
				String parentId = qm.getValueAt(i, 8).length() == 0 ? "0" : "" + qm.getValueAt(i, 8);
				String catalogImageId = qm.getValueAt(i, 9).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 9);
				buff.append("INSERT INTO CATALOG VALUES(" + i + "," + qm.getValueAt(i, 0) + "," + userId + ",'"
						+ qm.getValueAt(i, 2) + "'," + active + "," + tax + "," + descript + "," + qm.getValueAt(i, 6)
						+ "," + lang + "," + parentId + "," + catalogImageId + ") \n");
			}
			fs.write(buff.toString().getBytes());

			query = "select image_id ,  imgname ,  img_url ,  user_id from images ";
			fs.write("-- images --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				buff.append("INSERT INTO IMAGES VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "','"
						+ qm.getValueAt(i, 2) + "'," + qm.getValueAt(i, 3) + ") \n");
			}
			fs.write(buff.toString().getBytes());

			query = "select big_images_id ,  imgname ,  img_url ,  user_id from BIG_IMAGES ";
			fs.write("-- big_images --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				buff.append("INSERT INTO BIG_IMAGES VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "','"
						+ qm.getValueAt(i, 2) + "'," + qm.getValueAt(i, 3) + ") \n");
			}
			fs.write(buff.toString().getBytes());

			//
			query = "select file_id,name,filedata,user_id,size,path from file ";
			fs.write("-- file --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				buff.append("INSERT INTO FILE VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ qm.getValueAt(i, 2) + "," + qm.getValueAt(i, 3) + "," + qm.getValueAt(i, 4) + ",'"
						+ qm.getValueAt(i, 5) + "') \n");
			}
			fs.write(buff.toString().getBytes());

//
			/**
			 * query = "select creteria1_id , name , active , lang_id , link_id , catalog_id
			 * , label from creteria1" ; fs.write("-- criteria1 --\n\n".getBytes()) ; buff =
			 * new StringBuilder(); qm.executeQuery(query); for (int i = 0; qm.rows().size()
			 * > i; i ++) { buff.append("INSERT INTO CRETERIA1 VALUES("+qm.getValueAt(i,
			 * 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i, 2)+","+qm.getValueAt(i,
			 * 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i, 5)+",'"+qm.getValueAt(i,
			 * 6)+"') \n"); } fs.write(buff.toString().getBytes()) ; // query = "select
			 * creteria2_id , name , active , lang_id , link_id, catalog_id , label from
			 * creteria2" ; fs.write("-- criteria1 --\n\n".getBytes()) ; buff = new
			 * StringBuilder(); qm.executeQuery(query); for (int i = 0; qm.rows().size() >
			 * i; i ++) { buff.append("INSERT INTO CRETERIA2 VALUES("+qm.getValueAt(i,
			 * 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i, 2)+","+qm.getValueAt(i,
			 * 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i, 5)+",'"+qm.getValueAt(i,
			 * 6)+"') \n"); } fs.write(buff.toString().getBytes()) ;
			 *
			 * // query = "select creteria3_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria3" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA3
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; // query = "select creteria4_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria4" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA4
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; // query = "select creteria5_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria5" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA5
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; // query = "select creteria6_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria6" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA6
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; // query = "select creteria7_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria7" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA7
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; // query = "select creteria8_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria8" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA8
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ;
			 *
			 * // query = "select creteria9_id , name , active , lang_id , link_id ,
			 * catalog_id , label from creteria9" ; fs.write("-- criteria1
			 * --\n\n".getBytes()) ; buff = new StringBuilder(); qm.executeQuery(query); for
			 * (int i = 0; qm.rows().size() > i; i ++) { buff.append("INSERT INTO CRETERIA9
			 * VALUES("+qm.getValueAt(i, 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i,
			 * 2)+","+qm.getValueAt(i, 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i,
			 * 5)+",'"+qm.getValueAt(i, 6)+"') \n"); } fs.write(buff.toString().getBytes())
			 * ; //INSERT INTO CRETERIA1 VALUES(0,'',TRUE,NULL,0,0,'') query = "select
			 * creteria10_id , name , active , lang_id , link_id , catalog_id , label from
			 * creteria10" ; fs.write("-- criteria1 --\n\n".getBytes()) ; buff = new
			 * StringBuilder(); qm.executeQuery(query); for (int i = 0; qm.rows().size() >
			 * i; i ++) { buff.append("INSERT INTO CRETERIA10 VALUES("+qm.getValueAt(i,
			 * 0)+",'"+qm.getValueAt(i, 1)+"',"+qm.getValueAt(i, 2)+","+qm.getValueAt(i,
			 * 3)+","+qm.getValueAt(i, 4)+","+qm.getValueAt(i, 5)+",'"+qm.getValueAt(i,
			 * 6)+"') \n"); } fs.write(buff.toString().getBytes()) ;
			 */

			query = "select creteria1_id , name , active , lang_id  , link_id  from  creteria1";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA1 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria2_id , name , active , lang_id , link_id  from  creteria2";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA2 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());

//
			query = "select creteria3_id , name , active , lang_id , link_id   from  creteria3";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA3 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");

			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria4_id , name , active , lang_id , link_id from  creteria4";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA4 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria5_id , name , active , lang_id , link_id  from  creteria5";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA5 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria6_id , name , active , lang_id , link_id  from  creteria6";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA6 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria7_id , name , active , lang_id , link_id   from  creteria7";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA7 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//
			query = "select creteria8_id , name , active , lang_id , link_id from  creteria8";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA8 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());

//
			query = "select creteria9_id , name , active , lang_id , link_id from  creteria9";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA9 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());
//INSERT INTO CRETERIA1 VALUES(0,'',TRUE,NULL,0,0,'')
			query = "select creteria10_id , name , active , lang_id , link_id  from  creteria10";
			fs.write("-- criteria1 --\n\n".getBytes());
			buff = new StringBuilder();
			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				String langId = qm.getValueAt(i, 3).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 3);
				String linkId = qm.getValueAt(i, 4).length() == 0 ? "NULL" : "" + qm.getValueAt(i, 4);
				String active = qm.getValueAt(i, 2).equals("t") ? "TRUE" : "FALSE";
				buff.append("INSERT INTO CRETERIA10 VALUES(" + qm.getValueAt(i, 0) + ",'" + qm.getValueAt(i, 1) + "',"
						+ active + "," + langId + "," + linkId + ",0,NULL) \n");
			}
			fs.write(buff.toString().getBytes());

			qm.commit();
		} catch (SQLException ex) {
			qm.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
			qm.rollback();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			qm.close();
		}

	}

}
