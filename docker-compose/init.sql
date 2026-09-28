CREATE DATABASE  IF NOT EXISTS `cmsdb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cmsdb`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: cmsdb
-- ------------------------------------------------------
-- Server version	8.0.35OLD.SHIPPING_COMPANY_ID

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actionbidstatus`
--

DROP TABLE IF EXISTS `actionbidstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actionbidstatus` (
  `ACTION_BID_STATUS_ID` bigint(20) NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ACTION_BID_STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionbidstatus`
--

LOCK TABLES `actionbidstatus` WRITE;
/*!40000 ALTER TABLE `actionbidstatus` DISABLE KEYS */;
INSERT INTO `actionbidstatus` VALUES (-1,'Bid is not submited','Action bid is not submited',1,'en'),(0,'Bid is submited','Bid is submited',1,'en'),(1,'Bid was cancelled','Action bid was cancelled',1,'en'),(2,'Bid is won','Action bid is won',1,'en'),(3,'Bid did not win','Action bid did not win',1,'en');
/*!40000 ALTER TABLE `actionbidstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offerstatus`
--

DROP TABLE IF EXISTS `offerstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offerstatus` (
  `OFFER_STATUS_ID` bigint(20) NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`OFFER_STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offerstatus`
--

LOCK TABLES `offerstatus` WRITE;
/*!40000 ALTER TABLE `offerstatus` DISABLE KEYS */;
INSERT INTO `offerstatus` VALUES (-1,'Offer is not submited','Offer is not submited',1,'en'),(0,'Offer is submited','Offer is submited',1,'en'),(1,'Offer is accpted','Offer is accpted',1,'en'),(2,'Offer is declined','Offer is declined',1,'en'),(3,'Counter offer','Counter offer',1,'en');
/*!40000 ALTER TABLE `offerstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Remove it and User Role table Table structure for table `accesslevel`
--

DROP TABLE IF EXISTS `accesslevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accesslevel` (
  `ACCESSLEVEL_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`ACCESSLEVEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `ROLE_ID` bigint NOT NULL,
  `LEVELUP_CD` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `ACCOUNT_ID` bigint NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `AMOUNT` double NOT NULL DEFAULT '0',
  `CURR` int DEFAULT NULL,
  `DATE_INPUT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `COMPLETE` tinyint(1) NOT NULL DEFAULT '0',
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `CURRENCY_ID` bigint DEFAULT NULL,
  `TREE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_hist`
--

DROP TABLE IF EXISTS `account_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_hist` (
  `ID` bigint NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `ADD_AMOUNT` double DEFAULT '0',
  `OLD_AMOUNT` double DEFAULT NULL,
  `NUM_DAY` int DEFAULT NULL,
  `DATE_INPUT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `COMPLETE` tinyint(1) DEFAULT NULL,
  `DECSRIPTION` varchar(200) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `REZULT_CD` varchar(10) DEFAULT NULL,
  `DATE_END` timestamp NULL DEFAULT NULL,
  `SYSDATE` timestamp NULL DEFAULT NULL,
  `TAX` double DEFAULT '0',
  `SOFT_ID` bigint DEFAULT NULL,
  `RATE` double NOT NULL DEFAULT '0',
  `CURRENCY_ID_ADD` int DEFAULT NULL,
  `CURRENCY_ID_OLD` int DEFAULT NULL,
  `CURRENCY_ID_TOTAL` int DEFAULT NULL,
  `TOTAL_AMOUNT` double DEFAULT NULL,
  `WITHTAX_TOTAL_AMOUNT` double DEFAULT NULL,
  `USER_IP` varchar(15) DEFAULT NULL,
  `USER_HEADER` varchar(500) DEFAULT NULL,
  `USER_LOCALE` varchar(2) DEFAULT NULL,
  `USER_OS` varchar(10) DEFAULT NULL,
  `USER_OS_PACK` varchar(10) DEFAULT NULL,
  `ORDER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `basket`
--

DROP TABLE IF EXISTS `basket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `basket` (
  `BASKET_ID` bigint NOT NULL,
  `PRODUCT_ID` bigint DEFAULT NULL,
  `ORDER_ID` bigint DEFAULT NULL,
  `QUANTITY` int DEFAULT NULL,
  `SHIPPING_COST_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`BASKET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `big_images`
--

DROP TABLE IF EXISTS `big_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `big_images` (
  `BIG_IMAGES_ID` bigint NOT NULL,
  `IMGNAME` varchar(100) DEFAULT NULL,
  `IMG_URL` varchar(200) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`BIG_IMAGES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar` (
  `CALENDAR_ID` bigint NOT NULL,
  `SOFT_ID` bigint DEFAULT NULL,
  `HOLDDATE` int DEFAULT NULL,
  `FIST_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `FATHER_NAME` varchar(50) DEFAULT NULL,
  `DOCUMENT_NUMBER` varchar(50) DEFAULT NULL,
  `DOCUMENT_TYPE` varchar(50) DEFAULT NULL,
  `AGE` varchar(50) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `NOTE` varchar(500) DEFAULT NULL,
  `PARENT_ID` bigint DEFAULT NULL,
  `NODE_NAME` varchar(500) DEFAULT NULL,
  `SITE_ID` bigint NOT NULL,
  `BASKET_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`CALENDAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog`
--

DROP TABLE IF EXISTS `catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalog` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CATALOG_ID` bigint NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `TAX` double DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `PARENT_ID` bigint DEFAULT NULL,
  `CATALOG_IMAGE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=765 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_images`
--

DROP TABLE IF EXISTS `catalog_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalog_images` (
  `CATALOG_IMAGES_ID` bigint NOT NULL,
  `IMGNAME` varchar(100) DEFAULT NULL,
  `IMG_URL` varchar(200) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`CATALOG_IMAGES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


INSERT INTO cmsdb.catalog_images (CATALOG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101074423758454784,'Phone.jpeg','imgcatalog/101074423758454784.jpeg',100728482950021285),
	 (101074423758454785,'PC.jpeg','imgcatalog/101074423758454785.jpeg',100728482950021285),
	 (101074423758454786,'tvset.jpeg','imgcatalog/101074423758454786.jpeg',100728482950021285),
	 (101074423758454787,'SAMSUNG_27-Inch_Odyssey_G6_p2.jpg','imgcatalog/101074423758454787.jpg',100728482950021285),
	 (-1,'empty-folder.png','imgcatalog/-1.png',100728482950021285);


--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `CITY_ID` bigint DEFAULT NULL,
  `TELCODE` int DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `FULLNAME` varchar(100) DEFAULT NULL,
  `COUNTRY_ID` bigint DEFAULT NULL,
  `LANG_ID` int DEFAULT NULL,
  `LOCALE` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `COUNTRY_ID` bigint DEFAULT NULL,
  `TELCODE` int DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `FULLNAME` varchar(100) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LOCALE` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria1`
--

DROP TABLE IF EXISTS `creteria1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria1` (
  `CRETERIA1_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA1_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria10`
--

DROP TABLE IF EXISTS `creteria10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria10` (
  `CRETERIA10_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA10_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria2`
--

DROP TABLE IF EXISTS `creteria2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria2` (
  `CRETERIA2_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA2_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria3`
--

DROP TABLE IF EXISTS `creteria3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria3` (
  `CRETERIA3_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA3_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria4`
--

DROP TABLE IF EXISTS `creteria4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria4` (
  `CRETERIA4_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA4_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria5`
--

DROP TABLE IF EXISTS `creteria5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria5` (
  `CRETERIA5_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA5_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria6`
--

DROP TABLE IF EXISTS `creteria6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria6` (
  `CRETERIA6_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA6_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria7`
--

DROP TABLE IF EXISTS `creteria7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria7` (
  `CRETERIA7_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA7_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria8`
--

DROP TABLE IF EXISTS `creteria8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria8` (
  `CRETERIA8_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA8_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creteria9`
--

DROP TABLE IF EXISTS `creteria9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creteria9` (
  `CRETERIA9_ID` bigint NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `LINK_ID` bigint NOT NULL,
  `CATALOG_ID` bigint NOT NULL,
  `LABEL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CRETERIA9_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency` (
  `CURRENCY_ID` bigint NOT NULL,
  `RATE` double DEFAULT NULL,
  `CURRENCY_LABLE` varchar(10) DEFAULT NULL,
  `CURRENCY_DESC` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `CURRENCY_CD` varchar(100) DEFAULT NULL,
  `CURSDATE` varchar(100) DEFAULT NULL,
  `CHANGEDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CURRENCY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency_converter`
--

DROP TABLE IF EXISTS `currency_converter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency_converter` (
  `CURRENCY_ID` bigint NOT NULL,
  `1` double DEFAULT NULL,
  `2` double DEFAULT NULL,
  `3` double DEFAULT NULL,
  `4` double DEFAULT NULL,
  `5` double DEFAULT NULL,
  `6` double DEFAULT NULL,
  `7` double DEFAULT NULL,
  `8` double DEFAULT NULL,
  `9` double DEFAULT NULL,
  `10` double DEFAULT NULL,
  `11` double DEFAULT NULL,
  `12` double DEFAULT NULL,
  `13` double DEFAULT NULL,
  `14` double DEFAULT NULL,
  `15` double DEFAULT NULL,
  `16` double DEFAULT NULL,
  PRIMARY KEY (`CURRENCY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency_rate`
--

DROP TABLE IF EXISTS `currency_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency_rate` (
  `CURRENCY_RATE_ID` bigint NOT NULL,
  PRIMARY KEY (`CURRENCY_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverystatus`
--

DROP TABLE IF EXISTS `deliverystatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliverystatus` (
  `DELIVERYSTATUS_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`DELIVERYSTATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `deliverystatus` VALUES (0,'is not ready','is not ready',1,'en'),(1,'the receipt is sent','the receipt is sent',1,'en'),(2,'is paid','is paid',1,'en'),(3,'is sent to client','is sent to client',1,'en'),(4,'is delivered','is delivered',1,'en'),(5,'cancel','cancel',1,'en');

DROP TABLE IF EXISTS `itemdeliverystatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemdeliverystatus` (
  `ITEM_DELIVERYSTATUS_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ITEM_DELIVERYSTATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `itemdeliverystatus` VALUES (0,'is not ready','is not ready',1,'en'),(1,'the receipt is sent','the receipt is sent',1,'en'),(2,'is paid','is paid',1,'en'),(3,'is sent to client','is sent to client',1,'en'),(4,'is delivered','is delivered',1,'en'),(5,'cancel','cancel',1,'en'),(6,'lost','lost',1,'en'),(7,'start return','start return',1,'en'),(8,'returned','returned',1,'en');

--
-- Table structure for table `dinamic_fields`
--

DROP TABLE IF EXISTS `dinamic_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dinamic_fields` (
  `DINAMIC_FIELD_ID` bigint NOT NULL,
  `PRODUCT_ID` bigint DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`DINAMIC_FIELD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `domain_proc`
--

DROP TABLE IF EXISTS `domain_proc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domain_proc` (
  `DOMAIN_PROC_ID` bigint NOT NULL,
  `LAST_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `FILE_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `FILEDATA` binary(255) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `SIZE` varchar(15) DEFAULT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO cmsdb.file (FILE_ID,NAME,FILEDATA,USER_ID,`SIZE`,`PATH`) VALUES
	 (101087870529306629,'bordo_glina_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306629.jar'),
	 (101087870529306631,'bordo_baklajan_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306631.jar'),
	 (101087870529306634,'bordo_haki_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306634.jar'),
	 (101087870529306638,'bordo_noch_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306638.jar'),
	 (101087870529306643,'bordo_orange_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306643.jar'),
	 (101087870529306646,'bordo_pepel_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306646.jar'),
	 (101087870529306650,'bordo_red_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306650.jar'),
	 (101087870529306654,'bordo_shokolad_v325.jar',NULL,100728482950021285,NULL,'usr/local/tomcat/webapps/ROOT/files/101087870529306654.jar');

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `IMAGE_ID` bigint NOT NULL,
  `IMGNAME` varchar(50) DEFAULT NULL,
  `IMG_URL` varchar(500) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`IMAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lang`
--

DROP TABLE IF EXISTS `lang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lang` (
  `LANG_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`LANG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `licence`
--

DROP TABLE IF EXISTS `licence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `licence` (
  `LICENCE_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`LICENCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `one_sequences`
--

DROP TABLE IF EXISTS `one_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `one_sequences` (
  `ID` bigint NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ORDER_ID` bigint NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `DELIVERY_TIMEEND` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AMOUNT` double NOT NULL DEFAULT '0',
  `TAX` double DEFAULT NULL,
  `END_AMOUNT` double DEFAULT NULL,
  `DELIVERY_AMOUNT` double DEFAULT NULL,
  `DELIVERY_LONG` int DEFAULT NULL,
  `PAYSTATUS_ID` bigint DEFAULT NULL,
  `DELIVERY_START` timestamp NULL DEFAULT NULL,
  `CDATE` timestamp NULL DEFAULT NULL,
  `CURRENCY_ID` bigint DEFAULT NULL,
  `COUNTRY_ID` bigint DEFAULT NULL,
  `CITY_ID` bigint DEFAULT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `PHONE` varchar(50) DEFAULT NULL,
  `CONTACT_PERSON` varchar(200) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `FAX` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `ZIP` int DEFAULT NULL,
  `TREE_ID` int DEFAULT NULL,
  `IMEI` int DEFAULT NULL,
  `PHONEMODEL_ID` int DEFAULT NULL,
  `DELIVERYSTATUS_ID` bigint DEFAULT NULL,
  `SHIPPING_COMPANY_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders_hist`
--

DROP TABLE IF EXISTS `orders_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_hist` (
  `ORDER_HIST_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  `NOTICE` varchar(1000) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `SOFT_ID` bigint(20) DEFAULT NULL,
  `SITE_ID` bigint(20) DEFAULT NULL,
  `IMG_URL` varchar(255) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `FULLDESCRIPTION` varchar(5000) DEFAULT NULL,
  `BIG_IMG_URL` varchar(255) DEFAULT NULL,
  `CDATE` timestamp  NULL,
  `DELIVERY_TIMEEND` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `PRODUCT_COST` double DEFAULT NULL,
  `CURRENCY` int(11) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `ORDER_AMOUNT` double DEFAULT NULL,
  `TAX` double DEFAULT NULL,
  `ORDER_END_AMOUNT` double DEFAULT NULL,
  `DELIVERY_AMOUNT` double DEFAULT NULL,
  `DELIVERY_DAYS` int(11) DEFAULT NULL,
  `PAYSTATUS_ID` bigint(20) DEFAULT NULL,
  `ORDER_DELIVERYSTATUS_ID` bigint(20) DEFAULT NULL,
  `ITEM_DELIVERYSTATUS_ID` bigint(20) DEFAULT NULL,
  `SHIPPING_TRACKING_ID` bigint(20) DEFAULT NULL,
  `RETURN_SHIPPING_TRACKING_ID` bigint(20) DEFAULT NULL,
  `SHIPPING_COMPANY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ORDER_HIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `shipping_tracking`
--

DROP TABLE IF EXISTS `shipping_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_tracking` (
  `SHIPPING_TRACKING_ID` bigint NOT NULL AUTO_INCREMENT ,
  `ORDER_ID` bigint DEFAULT NULL,
  `NOTICE` varchar(1000) DEFAULT NULL,
  `SHIPPING_NUNBER` varchar(100) DEFAULT NULL,
  `EXT_SHIPPING_NUNBER` varchar(100) DEFAULT NULL,
  `ITEM_CURRENT_LOCATION` varchar(1000) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,  
  `SHIPPING_COMPANY_ID` bigint DEFAULT NULL, 
  `SOFT_ID` bigint DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`SHIPPING_TRACKING_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `shipping_tracking_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_tracking_hist` (
  `SHIPPING_TRACKING_HIST_ID` bigint NOT NULL,
  `SHIPPING_TRACKING_ID` bigint DEFAULT NULL,
  `ORDER_ID` bigint DEFAULT NULL,
  `NOTICE` varchar(1000) DEFAULT NULL,
  `SHIPPING_NUNBER` varchar(100) DEFAULT NULL,
  `EXT_SHIPPING_NUNBER` varchar(100) DEFAULT NULL,
  `ITEM_CURRENT_LOCATION` varchar(1000) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,  
  `SHIPPING_COMPANY_ID` bigint DEFAULT NULL, 
  `SOFT_ID` bigint DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`SHIPPING_TRACKING_HIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shipping_tracking`
--

DROP TABLE IF EXISTS `shipping_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_company` (
  `SHIPPING_COMPANY_ID` bigint NOT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `RATING_ID` bigint DEFAULT NULL,
  `VERIFIED` tinyint(1) NOT NULL,
  `USER_ID` bigint DEFAULT NULL, 
  `SITE_ID` bigint DEFAULT NULL, 
  PRIMARY KEY (`SHIPPING_COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `weight`
--

DROP TABLE IF EXISTS `weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weight` (
  `WEIGHT_ID` int NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`WEIGHT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO weight VALUES(0, 'LB', 'Pound') , (1, 'KG', 'Kilogram');

--
-- Table structure for table `volume`
--

DROP TABLE IF EXISTS `volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volume` (
  `VOLUME_ID` int NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`VOLUME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO volume VALUES(0, 'SQFT', 'Square foot') , (1, 'SQMR', 'Square meter'); 



--
-- Table structure for table `shipping_cost`
--

DROP TABLE IF EXISTS `shipping_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_cost` (
  `SHIPPING_COST_ID` bigint NOT NULL,
  `SHIPPING_COMPANY_ID` bigint NOT NULL,
  `DAYS_RANGE_ESTIMATE` varchar(200) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `PRICE` double NOT NULL DEFAULT '0',
  `CURRENCY_ID` int NOT NULL DEFAULT '1',
  `WEIGHT_ID` int NOT NULL DEFAULT '0',
  `VOLUME_ID` int NOT NULL DEFAULT '0',
  `ZIP_FROM` bigint NOT NULL DEFAULT '0',
  `ZIP_TO` bigint NOT NULL DEFAULT '0',
  `WEIGHT_MIN` bigint NOT NULL DEFAULT '0',
  `WEIGHT_MAX` bigint NOT NULL DEFAULT '0',
  `VOLUME_MIN` bigint NOT NULL DEFAULT '0',
  `VOLUME_MAX` bigint NOT NULL DEFAULT '0',
  `RATING_ID` bigint DEFAULT NULL,
  `DEAL` tinyint(1) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`SHIPPING_COST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


INSERT INTO shipping_cost VALUES
(-10, -10, '2-3 days','AI Estimate in USD for USPS ', 15 , 1, 0 , 0 , 11803 , 12845 , 0 , 2 , 0 , 1 , 3 , 1 , 1 ) ,
(-9, -10, '2-4 days','AI Estimate in USD for UPS ', 15 , 1, 0 , 0 , 11803 , 12845 , 0 , 2 , 0 , 1 , 3 , 1 , 1 ) , 
(-8, -10, '2-5 days','AI Estimate in USD for FedEx ', 15 , 1, 0 , 0 , 11803 , 12845 , 0 , 2 , 0 , 1 , 3 , 1 , 1 ) ,
(-7, -10, '2-5 days','AI Estimate in USD for DHL', 15 , 1, 0 , 0 , 11803 , 12845 , 0 , 2 , 0 , 1 , 3 , 1 , 1 ) ;

--
-- Table structure for table `resolution_center`
--

DROP TABLE IF EXISTS `resolution_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resolution_center` (
  `RESOLUTION_CENTER_ID` bigint NOT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `RATING_ID` bigint DEFAULT NULL,
  `VERIFIED` tinyint(1) NOT NULL,
  `USER_ID` bigint DEFAULT NULL, 
  `SITE_ID` bigint DEFAULT NULL, 
  PRIMARY KEY (`RESOLUTION_CENTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paystatus`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `RATING_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` longtext,
  PRIMARY KEY (`RATING_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `ratings` VALUES (0,'1','Very bad',1,'en'),(1,'2','Bad',1,'en'),(2,'3','Good',1,'en'),(3,'4','Excellent',1,'en');


--
-- Table structure for table `pay_gateway`
--

DROP TABLE IF EXISTS `pay_gateway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_gateway` (
  `PAY_GATEWAY_ID` bigint NOT NULL,
  `NAME_GATEWAY` varchar(50) DEFAULT NULL,
  `FULLNAME_GATEWAY` varchar(500) DEFAULT NULL,
  `PHONE` varchar(15) DEFAULT NULL,
  `FAX` varchar(15) DEFAULT NULL,
  `SITE_URL` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `pay_gateway` VALUES (1,'FiServ Test','FiServ Test','+1 888-477-3611','+1 888-477-3611','https://demo.globalgatewaye4.firstdata.com/payment',1),(2,'FiServ Live','FiServ Live','+1 888-477-3611','+1 888-477-3611','https://checkout.globalgatewaye4.firstdata.com/payment',1);


--
-- Table structure for table `paystatus`
--

DROP TABLE IF EXISTS `paystatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paystatus` (
  `PAYSTATUS_ID` bigint NOT NULL,
  `LABLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `LANG` longtext,
  PRIMARY KEY (`PAYSTATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


INSERT INTO `paystatus` VALUES (0,'is not paid','is not paid',1,'en'),(1,'is processed','is processed',1,'en'),(2,'is paid','is paid',1,'en'),(3,'is not accepted','is not accepted',1,'en');

--
-- Table structure for table `paysystem`
--

DROP TABLE IF EXISTS `paysystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paysystem` (
  `PAYSYSTEM_ID` bigint NOT NULL,
  `PAYSYSTEM_CD` varchar(50) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `IMG_URL` varchar(500) DEFAULT NULL,
  `PAY_GATEWAY_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`PAYSYSTEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;



INSERT INTO `paysystem` VALUES (1,'CardPaymentVisa','VISA','VISA',0,'',NULL),(2,'CardPaymentMaster','EC/MC','EC/MC',0,'',NULL),(3,'Discover','Discover card ','Discover card',0,'',NULL),(4,'Amex','American Experess card','American Experess card',0,'',NULL),(5,'UnionPay','Union Pay cards','Union Pay cards',0,'',NULL),(6,'Google pay','Google pay','Google pay',0,'',NULL),(7,'PayCashPayment','Yandex money','Yandex money',1,'',NULL),(9,'Paypal','Paypal','Paypal',0,'',NULL),(10,'Apple pay','Apple pay','Apple pay',0,'',NULL);


--
-- Table structure for table `portlettype`
--

DROP TABLE IF EXISTS `portlettype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portlettype` (
  `PORTLETTYPE_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`PORTLETTYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;



INSERT INTO `portlettype` VALUES (0,'Items list in product list',1),(1,'Recommended items in product list',1),(2,'Sponsored items in product list ',1),(3,'Items review panel',1),(4,'Tabs panel in product info with tree_id',1),(5,'File attachement panel',1),(6,'Payment info page',1),(18,'Rooter panel',1),(19,'Product offer',1),(20,'Product action',1);

--
-- Table structure for table `salelogic`
--

DROP TABLE IF EXISTS `salelogic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salelogic` (
  `SALELOGIC_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIBE` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`SALELOGIC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_status_events`
--

DROP TABLE IF EXISTS `service_status_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_status_events` (
  `SITE_ID` bigint DEFAULT NULL,
  `DATE_SEND` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CLASSBODY` varchar(250) DEFAULT NULL,
  `SERVICE_STATUS` int DEFAULT NULL,
  `ISNOTIFY` tinyint(1) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_status_events_history`
--

DROP TABLE IF EXISTS `service_status_events_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_status_events_history` (
  `SITE_ID` bigint DEFAULT NULL,
  `DATE_SEND` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SERVICE_STATUS` int DEFAULT NULL,
  `CLASSBODY` varchar(250) DEFAULT NULL,
  `ISNOTIFY` tinyint(1) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `SHOP_ID` bigint NOT NULL AUTO_INCREMENT,
  `SHOP_CD` int NOT NULL,
  `OWNER_ID` bigint NOT NULL,
  `LOGIN` varchar(50) DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  `PAY_GATEWAY_ID` bigint DEFAULT NULL,
  `PASSWD` varchar(255) DEFAULT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SHOP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `site` (
  `SITE_ID` bigint NOT NULL,
  `HOST` varchar(100) DEFAULT NULL,
  `OWNER` bigint DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `HOME_DIR` varchar(100) DEFAULT NULL,
  `SITE_DIR` varchar(100) DEFAULT NULL,
  `PERSON` varchar(200) DEFAULT NULL,
  `PHONE` varchar(50) DEFAULT NULL,
  `ADDRESS` varchar(300) DEFAULT NULL,
  `SUBJECT_SITE` varchar(300) DEFAULT NULL,
  `NICK_SITE` varchar(200) DEFAULT NULL,
  `COMPANY_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SITE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `soft`
--

DROP TABLE IF EXISTS `soft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soft` (
  `SOFT_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `VERSION` varchar(10) DEFAULT NULL,
  `COST` double NOT NULL DEFAULT '0',
  `CURRENCY` int NOT NULL DEFAULT '1',
  `SERIAL_NUBMER` varchar(50) DEFAULT NULL,
  `FILE_ID` bigint DEFAULT NULL,
  `TYPE_ID` bigint DEFAULT NULL,
  `LEVELUP` bigint DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `PHONETYPE_ID` int DEFAULT NULL,
  `PROGNAME_ID` bigint DEFAULT NULL,
  `IMAGE_ID` bigint DEFAULT NULL,
  `BIGIMAGE_ID` bigint DEFAULT NULL,
  `WEIGHT` double DEFAULT NULL,
  `COUNT` int DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `PHONEMODEL_ID` int DEFAULT NULL,
  `LICENCE_ID` bigint DEFAULT NULL,
  `CATALOG_ID` bigint DEFAULT NULL,
  `SALELOGIC_ID` bigint DEFAULT NULL,
  `TREE_ID` bigint DEFAULT NULL,
  `CARD_NUMBER` int DEFAULT NULL,
  `CARD_CODE` int DEFAULT NULL,
  `START_DIAL_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DIAL_TIME` timestamp NULL DEFAULT NULL,
  `START_ACTIVATION_CARD` tinyint(1) NOT NULL DEFAULT '0',
  `END_ACTIVATION_CARD` tinyint(1) NOT NULL DEFAULT '0',
  `TYPE_CARD_ID` int DEFAULT NULL,
  `PRODUCT_CODE` int DEFAULT NULL,
  `FULLDESCRIPTION` varchar(5000) DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  `SEARCH` varchar(1) DEFAULT NULL,
  `PORTLETTYPE_ID` bigint NOT NULL DEFAULT '0',
  `CRETERIA1_ID` bigint DEFAULT NULL,
  `CRETERIA2_ID` bigint DEFAULT NULL,
  `CRETERIA3_ID` bigint DEFAULT NULL,
  `CRETERIA4_ID` bigint DEFAULT NULL,
  `CRETERIA5_ID` bigint DEFAULT NULL,
  `CRETERIA6_ID` bigint DEFAULT NULL,
  `CRETERIA7_ID` bigint DEFAULT NULL,
  `CRETERIA8_ID` bigint DEFAULT NULL,
  `CRETERIA9_ID` bigint DEFAULT NULL,
  `CRETERIA10_ID` bigint DEFAULT NULL,
  `STATISTIC_ID` bigint NOT NULL DEFAULT '0',
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CRETERIA11_ID` bigint DEFAULT NULL,
  `CRETERIA12_ID` bigint DEFAULT NULL,
  `CRETERIA13_ID` bigint DEFAULT NULL,
  `CRETERIA14_ID` bigint DEFAULT NULL,
  `CRETERIA15_ID` bigint DEFAULT NULL,
  `SQUARE` int DEFAULT NULL,
  `RATING_SUMM1` int NOT NULL DEFAULT '0',
  `RATING_SUMM2` int NOT NULL DEFAULT '0',
  `RATING_SUMM3` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING1` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING2` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING3` int NOT NULL DEFAULT '0',
  `MIDLE_BAL1` int NOT NULL DEFAULT '0',
  `MIDLE_BAL2` int NOT NULL DEFAULT '0',
  `MIDLE_BAL3` int NOT NULL DEFAULT '0',
  `SHOW_RATING1` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_RATING2` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_RATING3` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_BLOG` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_OFFER` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_ACTION` tinyint(1) NOT NULL DEFAULT '0',
  `NAME2` varchar(50) DEFAULT NULL,
  `SEARCH2` varchar(1) DEFAULT NULL,
  `AMOUNT1` double NOT NULL DEFAULT '0',
  `AMOUNT2` double NOT NULL DEFAULT '0',
  `AMOUNT3` double NOT NULL DEFAULT '0',
  `JSP_URL` varchar(100) DEFAULT NULL,
  `COLOR` varchar(10) DEFAULT NULL,
  `ACTION_BID_STATUS_ID` bigint NOT NULL DEFAULT '-1',
  `OFFER_STATUS_ID` bigint NOT NULL DEFAULT '-1',
  PRIMARY KEY (`SOFT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `soft_hist`
--

DROP TABLE IF EXISTS `soft_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soft_hist` (
  `SOFT_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `VERSION` varchar(10) DEFAULT NULL,
  `COST` double NOT NULL DEFAULT '0',
  `CURRENCY` int NOT NULL DEFAULT '1',
  `SERIAL_NUBMER` varchar(50) DEFAULT NULL,
  `FILE_ID` bigint DEFAULT NULL,
  `TYPE_ID` bigint DEFAULT NULL,
  `LEVELUP` bigint DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `PHONETYPE_ID` int DEFAULT NULL,
  `PROGNAME_ID` bigint DEFAULT NULL,
  `IMAGE_ID` bigint DEFAULT NULL,
  `BIGIMAGE_ID` bigint DEFAULT NULL,
  `WEIGHT` double DEFAULT NULL,
  `COUNT` int DEFAULT NULL,
  `LANG_ID` bigint DEFAULT NULL,
  `PHONEMODEL_ID` int DEFAULT NULL,
  `LICENCE_ID` bigint DEFAULT NULL,
  `CATALOG_ID` bigint DEFAULT NULL,
  `SALELOGIC_ID` bigint DEFAULT NULL,
  `TREE_ID` bigint DEFAULT NULL,
  `CARD_NUMBER` int DEFAULT NULL,
  `CARD_CODE` int DEFAULT NULL,
  `START_DIAL_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DIAL_TIME` timestamp NULL DEFAULT NULL,
  `START_ACTIVATION_CARD` tinyint(1) NOT NULL DEFAULT '0',
  `END_ACTIVATION_CARD` tinyint(1) NOT NULL DEFAULT '0',
  `TYPE_CARD_ID` int DEFAULT NULL,
  `PRODUCT_CODE` int DEFAULT NULL,
  `FULLDESCRIPTION` varchar(5000) DEFAULT NULL,
  `SITE_ID` bigint DEFAULT NULL,
  `SEARCH` varchar(1) DEFAULT NULL,
  `PORTLETTYPE_ID` bigint NOT NULL DEFAULT '0',
  `CRETERIA1_ID` bigint DEFAULT NULL,
  `CRETERIA2_ID` bigint DEFAULT NULL,
  `CRETERIA3_ID` bigint DEFAULT NULL,
  `CRETERIA4_ID` bigint DEFAULT NULL,
  `CRETERIA5_ID` bigint DEFAULT NULL,
  `CRETERIA6_ID` bigint DEFAULT NULL,
  `CRETERIA7_ID` bigint DEFAULT NULL,
  `CRETERIA8_ID` bigint DEFAULT NULL,
  `CRETERIA9_ID` bigint DEFAULT NULL,
  `CRETERIA10_ID` bigint DEFAULT NULL,
  `STATISTIC_ID` bigint NOT NULL DEFAULT '0',
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CRETERIA11_ID` bigint DEFAULT NULL,
  `CRETERIA12_ID` bigint DEFAULT NULL,
  `CRETERIA13_ID` bigint DEFAULT NULL,
  `CRETERIA14_ID` bigint DEFAULT NULL,
  `CRETERIA15_ID` bigint DEFAULT NULL,
  `SQUARE` int DEFAULT NULL,
  `RATING_SUMM1` int NOT NULL DEFAULT '0',
  `RATING_SUMM2` int NOT NULL DEFAULT '0',
  `RATING_SUMM3` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING1` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING2` int NOT NULL DEFAULT '0',
  `COUNTPOST_RATING3` int NOT NULL DEFAULT '0',
  `MIDLE_BAL1` int NOT NULL DEFAULT '0',
  `MIDLE_BAL2` int NOT NULL DEFAULT '0',
  `MIDLE_BAL3` int NOT NULL DEFAULT '0',
  `SHOW_RATING1` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_RATING2` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_RATING3` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_BLOG` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_OFFER` tinyint(1) NOT NULL DEFAULT '0',
  `SHOW_ACTION` tinyint(1) NOT NULL DEFAULT '0',
  `NAME2` varchar(50) DEFAULT NULL,
  `SEARCH2` varchar(1) DEFAULT NULL,
  `AMOUNT1` double NOT NULL DEFAULT '0',
  `AMOUNT2` double NOT NULL DEFAULT '0',
  `AMOUNT3` double NOT NULL DEFAULT '0',
  `JSP_URL` varchar(100) DEFAULT NULL,
  `COLOR` varchar(10) DEFAULT NULL,
  `ACTION_BID_STATUS_ID` bigint NOT NULL DEFAULT '-1',
  `OFFER_STATUS_ID` bigint NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;





--
-- Table structure for table `notification_subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `SOFT_ID` bigint NOT NULL,
  `MESSAGE` varchar(50) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `store_session`
--

DROP TABLE IF EXISTS `store_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_session` (
  `IDSESSION_HASH1` bigint DEFAULT NULL,
  `IDSESSION_HASH2` bigint DEFAULT NULL,
  `IDSESSION_HASH3` bigint DEFAULT NULL,
  `IDSESSION_HASH4` bigint DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `TYPE` varchar(200) DEFAULT NULL,
  `LASTURL` varchar(500) DEFAULT NULL,
  `CLASSBODY` varchar(250) DEFAULT NULL,
  `BCLASSBODY` longblob,
  `ACTIVE` tinyint(1) NOT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tree`
--

DROP TABLE IF EXISTS `tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tree` (
  `ITEM_ID` bigint NOT NULL,
  `NODE_ID` bigint DEFAULT NULL,
  `FOLDER_NAME` varchar(50) DEFAULT NULL,
  `OWNER_ID` bigint DEFAULT NULL,
  `GROUP_ID` int DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `DOC_ID` int DEFAULT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_TOUCH_ID` int DEFAULT NULL,
  `LAST_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuser`
--

DROP TABLE IF EXISTS `tuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tuser` (
  `USER_ID` bigint NOT NULL,
  `LOGIN` varchar(50) DEFAULT NULL,
  `PASSWD` varchar(255) DEFAULT NULL,
  `FIRST_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `COMPANY` varchar(50) DEFAULT NULL,
  `E_MAIL` varchar(50) DEFAULT NULL,
  `PHONE` varchar(50) DEFAULT NULL,
  `MOBIL_PHONE` varchar(50) DEFAULT NULL,
  `FAX` varchar(50) DEFAULT NULL,
  `ICQ` varchar(50) DEFAULT NULL,
  `WEBSITE` varchar(100) DEFAULT NULL,
  `QUESTION` varchar(200) DEFAULT NULL,
  `ANSWER` varchar(200) DEFAULT NULL,
  `IDSESSION` varchar(50) DEFAULT NULL,
  `BIRTHDAY` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REGDATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LEVELUP_CD` bigint DEFAULT NULL,
  `BANK_CD` bigint DEFAULT NULL,
  `ACVIVE_SESSION` tinyint(1) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `COUNTRY` varchar(20) DEFAULT NULL,
  `CITY` varchar(20) DEFAULT NULL,
  `ZIP` varchar(10) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `SCOUNTRY` varchar(50) DEFAULT NULL,
  `MIDDLENAME` varchar(50) DEFAULT NULL,
  `CITY_ID` bigint DEFAULT NULL,
  `COUNTRY_ID` bigint DEFAULT NULL,
  `CURRENCY_ID` bigint DEFAULT NULL,
  `TREE_ID` bigint DEFAULT NULL,
  `SITE_ID` bigint NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `typesoft`
--

DROP TABLE IF EXISTS `typesoft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typesoft` (
  `TYPE_ID` bigint NOT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `TYPE_LABLE` varchar(50) DEFAULT NULL,
  `TYPE_DESC` varchar(100) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `TAX` double NOT NULL,
  PRIMARY KEY (`TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xsl_style`
--

DROP TABLE IF EXISTS `xsl_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xsl_style` (
  `XSL_STYLE_ID` int NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  `PRODUCER_ID` int NOT NULL,
  `OWNER_ID` bigint NOT NULL,
  `COST` double NOT NULL,
  `CURRENCY_ID` bigint NOT NULL,
  `SYS_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SITE_ID` bigint NOT NULL,
  `DIRNAME` varchar(200) DEFAULT NULL,
  `XSL_SUBJ_ID` int DEFAULT NULL,
  PRIMARY KEY (`XSL_STYLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xsl_subj`
--

DROP TABLE IF EXISTS `xsl_subj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xsl_subj` (
  `XSL_SUBJ_ID` bigint NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`XSL_SUBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16 21:06:33

INSERT INTO cmsdb.roles
(ROLE_ID, LEVELUP_CD, NAME, DESCRIPTION, ACTIVE) VALUES
(0, 0, 'Guest', 'Guest', 1),
(1, 1, 'Menmber', 'Menmber', 1),
(2, 2, 'Administrator', 'Administrator', 1),
(3, 3, 'Shipping', 'Shipping', 1),
(4, 4, 'Fullfilment', 'Fullfilment', 1);

INSERT INTO cmsdb.orders
(ORDER_ID, USER_ID, DELIVERY_TIMEEND, AMOUNT, TAX, END_AMOUNT, DELIVERY_AMOUNT, DELIVERY_LONG, PAYSTATUS_ID, DELIVERY_START, CDATE, CURRENCY_ID, COUNTRY_ID, CITY_ID, ADDRESS, PHONE, CONTACT_PERSON, EMAIL, FAX, DESCRIPTION, ZIP, TREE_ID, IMEI, PHONEMODEL_ID, DELIVERYSTATUS_ID , SHIPPING_COMPANY_ID ) VALUES
(-1, 100728482950021285, '2024-11-28 21:13:21', 219.99, 0.0, 219.99, 0.0, NULL, 0, '2024-11-28 00:00:00', '2024-11-28 00:00:00', 1, 2, 107, '777 Cmsmanhattan AVE', '+1(xxx)xxx-xxxx', 'Admins', 'admin@cmsmanhattan.com', '', '', NULL, NULL, NULL, NULL, 4 , -10),
(-2, -5, '2024-11-28 21:13:21', 219.99, 0.0, 219.99, 0.0, NULL, 0, '2024-11-28 00:00:00', '2024-11-28 00:00:00', 1, 2, 107, '777 Cmsmanhattan AVE', '+1(xxx)xxx-xxxx', 'Tester', 'tester@cmsmanhattan.com', '', '', NULL, NULL, NULL, NULL, 4 , -10);


INSERT INTO cmsdb.account_hist
(ID, USER_ID, ADD_AMOUNT, OLD_AMOUNT, NUM_DAY, DATE_INPUT, COMPLETE, DECSRIPTION, ACTIVE, REZULT_CD, DATE_END, SYSDATE, TAX, SOFT_ID, RATE, CURRENCY_ID_ADD, CURRENCY_ID_OLD, CURRENCY_ID_TOTAL, TOTAL_AMOUNT, WITHTAX_TOTAL_AMOUNT, USER_IP, USER_HEADER, USER_LOCALE, USER_OS, USER_OS_PACK, ORDER_ID) VALUES
(-1, 100728482950021285, 1000 , 0, NULL, current_timestamp(), 1 , 'added 1000 point reward for registration' , 1, 2, current_timestamp(), current_timestamp(), 0, 100728482950021316, 0, 1, 1, 1, 1000 , 0, NULL, NULL, NULL, NULL, NULL, -1),
(-2, -5, 1000 , 0, NULL, current_timestamp(), 1 , 'added 1000 point reward for registration' , 1, 2, current_timestamp(), current_timestamp(), 0, 100728482950021316, 0, 1, 1, 1, 1 , 0, NULL, NULL, NULL, NULL, NULL, -2);

INSERT INTO shipping_tracking ( ORDER_ID, NOTICE,  ITEM_CURRENT_LOCATION, USER_ID,  SOFT_ID, SITE_ID , SHIPPING_COMPANY_ID) VALUES 
(-1 , 'It is waiting a tracking number ',  'The shop warehouse ' , 100728482950021285 , 100728482950021305 , 2 , -10) ,
(-2 , 'It is waiting a tracking number ',  'The shop warehouse ' , -5 , 100728482950021305 , 2 , -10) ;

INSERT INTO cmsdb.shipping_tracking_hist (SHIPPING_TRACKING_HIST_ID, SHIPPING_TRACKING_ID, ORDER_ID, NOTICE, SHIPPING_NUNBER, EXT_SHIPPING_NUNBER, ITEM_CURRENT_LOCATION, USER_ID, SHIPPING_COMPANY_ID, SOFT_ID, SITE_ID) VALUES
(-1, -1, -1, 'It is waiting a tracking number ', '12345', '12345EXT', 'The shop warehouse ', 100728482950021285, -10, 100728482950021316, -10),
(-2, -2, -1, 'It is waiting a tracking number ', '12346', '12346EXT', 'The shop warehouse ', 100728482950021285, -10, 100728482950021316, -10) ;

INSERT INTO cmsdb.orders_hist
(ORDER_HIST_ID, ORDER_ID, NOTICE, USER_ID, SOFT_ID, SITE_ID, IMG_URL, NAME, DESCRIPTION, FULLDESCRIPTION, BIG_IMG_URL, CDATE, DELIVERY_TIMEEND, PRODUCT_COST, CURRENCY, QUANTITY, ORDER_AMOUNT, TAX, ORDER_END_AMOUNT, DELIVERY_AMOUNT, DELIVERY_DAYS, PAYSTATUS_ID, ORDER_DELIVERYSTATUS_ID, ITEM_DELIVERYSTATUS_ID, SHIPPING_TRACKING_ID, RETURN_SHIPPING_TRACKING_ID, SHIPPING_COMPANY_ID)
VALUES
(-1, -1, NULL, 100728482950021285, 100728482950021316, 2, 'imgpositions/101055362458714165.png', 'Dell G2725D Gaming Monitor', '
<r>Dell G2725D Gaming Monitor - 27-inch QHD (2560x1440) up to 180 Hz, 2ms Response time, 99% RGB Display, DisplayPort/HDMI Connectivity, AMD FreeSync, Comfortview, Tilt Adjustable - Black
</r>', '
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>', 'big_imgpositions/101055362458714166.png', '2024-10-08 00:00:00', '2025-07-15 18:32:51', 199.99, 1, 1, 199.99000549316406, 0.0, 199.99000549316406, 0.0, NULL, 0, 0, 0, -1, NULL, -10),
(-2, -2, NULL, -5, 100728482950021316, 2, 'imgpositions/101055362458714165.png', 'Dell G2725D Gaming Monitor', '
<r>Dell G2725D Gaming Monitor - 27-inch QHD (2560x1440) up to 180 Hz, 2ms Response time, 99% RGB Display, DisplayPort/HDMI Connectivity, AMD FreeSync, Comfortview, Tilt Adjustable - Black
</r>', '
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>', 'big_imgpositions/101055362458714166.png', '2024-10-08 00:00:00', '2025-07-15 18:32:51', 199.99, 1, 1, 199.99000549316406, 0.0, 199.99000549316406, 0.0, NULL, 0, 0, 0, -2, NULL , -10);





INSERT INTO cmsdb.account (ACCOUNT_ID,USER_ID,AMOUNT,CURR,DATE_INPUT,DESCRIPTION,COMPLETE,ACTIVE,CURRENCY_ID,TREE_ID) VALUES
	 (100728482950021286,100728482950021285,0.0,3,'2024-02-26 00:00:00',' new_admin_account ',0,0,1,NULL),
	 (100728482950021288,100728482950021287,0.0,3,'2024-02-26 00:00:00',' new_guest_account ',0,0,1,NULL),
	 (-110,-110,0.0,3,'2024-02-26 00:00:00',' new_usps_account ',0,0,1,NULL),
	 (-10,-10,0.0,3,'2024-02-26 00:00:00',' admin_usps_account ',0,0,1,NULL),
	 (-20,-20,0.0,3,'2024-02-26 00:00:00',' guest_usps_account ',0,0,1,NULL),	 
	 (-119,-119,0.0,3,'2024-02-26 00:00:00',' new_ups_account ',0,0,1,NULL),
     (-9,-9,0.0,3,'2024-02-26 00:00:00','admin_ups_account ',0,0,1,NULL),	 
	 (-19,-19,0.0,3,'2024-02-26 00:00:00',' guest_ups_account ',0,0,1,NULL),	 
	 (-118,-118,0.0,3,'2024-02-26 00:00:00',' new_fedex_account ',0,0,1,NULL),
	 (-8,-8,0.0,3,'2024-02-26 00:00:00',' new_fedex_account ',0,0,1,NULL),	 
	 (-18,-18,0.0,3,'2024-02-26 00:00:00',' guest_fedex_account ',0,0,1,NULL),	 
	 (-117,-117,0.0,3,'2024-02-26 00:00:00',' new_dhl_account ',0,0,1,NULL),
	 (-7,-7,0.0,3,'2024-02-26 00:00:00',' new_dhl_account ',0,0,1,NULL),	 
	 (-17,-17,0.0,3,'2024-02-26 00:00:00',' guest_fedex_account ',0,0,1,NULL),	 
	 (-116,-116,0.0,3,'2024-02-26 00:00:00','new_cmsmanhattan_fullfilment_account ',0,0,1,NULL),
	 (-6,-6,0.0,3,'2024-02-26 00:00:00','new_cmsmanhattan_fullfilment_account ',0,0,1,NULL),	 
	 (-16,-16,0.0,3,'2024-02-26 00:00:00','guest_cmsmanhattan_fullfilment_account ',0,0,1,NULL),	 
	 (-5,-5,0.0,3,'2024-02-26 00:00:00','tester',0,0,1,NULL),
	 (-14,-14,0.0,3,'2024-02-26 00:00:00','new_resolution_center_account',0,0,1,NULL),
	 (-4,-4,0.0,3,'2024-02-26 00:00:00','admin_resolution_center_account',0,0,1,NULL),	 
	 (-114,-114,0.0,3,'2024-02-26 00:00:00','guest_resolution_center_account',0,0,1,NULL);
	 
update cmsdb.account set amount = 1000 where  user_id = 100728482950021285 ;
update cmsdb.account set amount = 1000 where  user_id = -5 ;	 
	 

INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,2,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,2,2,0,NULL),
	 (100728482950021289,NULL,'Smart phones',1,NULL,NULL,2,2,-2,101074423758454784),
	 (100728482950021290,NULL,'Samsung',1,NULL,NULL,2,2,100728482950021289,-1),
	 (100728482950021291,NULL,'Apple',1,NULL,NULL,2,2,100728482950021289,-1),
	 (100728482950021292,NULL,'LG',1,NULL,NULL,2,2,100728482950021289,-1),
	 (100728482950021293,NULL,'PC',1,NULL,NULL,2,2,-2,101074423758454785),
	 (100728482950021294,NULL,'Samsung',1,NULL,NULL,2,2,100728482950021293,-1),
	 (100728482950021295,NULL,'DELL',1,NULL,NULL,2,2,100728482950021293,-1),
	 (100728482950021296,NULL,'Lenove',1,NULL,NULL,2,2,100728482950021293,-1);
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (100728482950021297,NULL,'Monitors',1,NULL,NULL,2,2,-2,101074423758454787),
	 (100728482950021298,NULL,'Samsung',1,NULL,NULL,2,2,100728482950021297,-1),
	 (100728482950021299,NULL,'Apple',1,NULL,NULL,2,2,100728482950021297,-1),
	 (100728482950021300,NULL,'LG',1,NULL,NULL,2,2,100728482950021297,-1),
	 (100728482950021301,NULL,'TV set',1,NULL,NULL,2,2,-2,101074423758454786),
	 (100728482950021302,NULL,'Samsung',1,NULL,NULL,2,2,100728482950021301,-1),
	 (100728482950021303,NULL,'Apple',1,NULL,NULL,2,2,100728482950021301,-1),
	 (100728482950021304,NULL,'LG',1,NULL,NULL,2,2,100728482950021301,-1);
	 
-- usps
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,-10,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,-10,2,0,NULL),
	 (-27,NULL,'Statuses',1,NULL,NULL,-10,2,-2,0),
	 (-20,NULL,'New',1,NULL,NULL,-10,2,-26,-1),
	 (-21,NULL,'Printed label',1,NULL,NULL,-10,2,-26,-1),
	 (-22,NULL,'Shipped',1,NULL,NULL,-10,2,-26,-1),
	 (-23,NULL,'In delivery',1,NULL,NULL,-10,2,-26,-1),
	 (-24,NULL,'Out of delivery',1,NULL,NULL,-10,2,-26,-1),
	 (-25,NULL,'Issue solved',1,NULL,NULL,-10,2,-26,-1),
	 (-26,NULL,'Delivered',1,NULL,NULL,-10,2,-26,-1);
	 
-- ups
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,-9,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,-9,2,0,NULL),
	 (-27,NULL,'Statuses',1,NULL,NULL,-9,2,-2,0),
	 (-20,NULL,'New',1,NULL,NULL,-9,2,-26,-1),
	 (-21,NULL,'Printed label',1,NULL,NULL,-9,2,-26,-1),
	 (-22,NULL,'Shipped',1,NULL,NULL,-9,2,-26,-1),
	 (-23,NULL,'In delivery',1,NULL,NULL,-9,2,-26,-1),
	 (-24,NULL,'Out of delivery',1,NULL,NULL,-9,2,-26,-1),
	 (-25,NULL,'Issue solved',1,NULL,NULL,-9,2,-26,-1),
	 (-26,NULL,'Delivered',1,NULL,NULL,-9,2,-26,-1);	 

-- 	fedex 
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,-8,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,-8,2,0,NULL),
	 (-27,NULL,'Statuses',1,NULL,NULL,-8,2,-2,0),
	 (-20,NULL,'New',1,NULL,NULL,-8,2,-26,-1),
	 (-21,NULL,'Printed label',1,NULL,NULL,-8,2,-26,-1),
	 (-22,NULL,'Shipped',1,NULL,NULL,-8,2,-26,-1),
	 (-23,NULL,'In delivery',1,NULL,NULL,-8,2,-26,-1),
	 (-24,NULL,'Out of delivery',1,NULL,NULL,-8,2,-26,-1),
	 (-25,NULL,'Issue solved',1,NULL,NULL,-8,2,-26,-1),
	 (-26,NULL,'Delivered',1,NULL,NULL,-8,2,-26,-1);
	 
-- dhl	 
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,-7,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,-7,2,0,NULL),
	 (-27,NULL,'Statuses',1,NULL,NULL,-7,2,-2,0),
	 (-20,NULL,'New',1,NULL,NULL,-7,2,-26,-1),
	 (-21,NULL,'Printed label',1,NULL,NULL,-7,2,-26,-1),
	 (-22,NULL,'Shipped',1,NULL,NULL,-7,2,-26,-1),
	 (-23,NULL,'In delivery',1,NULL,NULL,-7,2,-26,-1),
	 (-24,NULL,'Out of delivery',1,NULL,NULL,-7,2,-26,-1),
	 (-25,NULL,'Issue solved',1,NULL,NULL,-7,2,-26,-1),
	 (-26,NULL,'Delivered',1,NULL,NULL,-7,2,-26,-1);
	 
-- resolution center
INSERT INTO cmsdb.`catalog` (CATALOG_ID,USER_ID,LABLE,ACTIVE,TAX,DESCRIPTION,SITE_ID,LANG_ID,PARENT_ID,CATALOG_IMAGE_ID) VALUES
	 (-1,NULL,'News',1,NULL,NULL,-4,2,0,NULL),
	 (-2,NULL,'Main page',1,NULL,NULL,-4,2,0,NULL),
	 (-19,NULL,'Statuses',1,NULL,NULL,-4,2,-2,0),
	 (-20,NULL,'New',1,NULL,NULL,-4,2,-19,-1),
	 (-21,NULL,'In process',1,NULL,NULL,-4,2,-19,-1),
	 (-22,NULL,'Solved',1,NULL,NULL,-4,2,-19,-1);

	 
INSERT INTO cmsdb.city (CITY_ID,TELCODE,NAME,FULLNAME,COUNTRY_ID,LANG_ID,LOCALE) VALUES
	 (1,812,'St. Petersburg','St. Petersburg',1,2,'EN'),
	 (2,95,'Moscow','Moscow',1,2,'EN'),
	 (107,22,'York','York',2,2,'EN'),
	 (103,22,'London','London',3,2,'EN'),
	 (700,61,'Ottawa','Ottawa',13,2,'EN'),
	 (7001,2,'Canberra','Canberra',20,2,'EN');
INSERT INTO cmsdb.country (COUNTRY_ID,TELCODE,NAME,FULLNAME,LANG_ID,LOCALE) VALUES
	 (1,7,'Russia','Russia',2,'EN'),
	 (2,1,'USA','USA',2,'EN'),
	 (3,44,'United Kingdom','United Kingdom',2,'EN'),
	 (13,1,'Canada','Canada',2,'EN'),
	 (20,1,'Australia','Australia',2,'EN');
	 
INSERT INTO cmsdb.creteria1 (CRETERIA1_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Brand'),
	 (1,'SUMSUNG',1,2,0,2,'Brand'),
	 (2,'LENOVO',1,2,0,2,'Brand'),
	 (3,'LG',1,2,0,2,'Brand'),
	 (4,'APPLE',1,2,0,2,'Brand');
	 
INSERT INTO cmsdb.creteria10 (CRETERIA10_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Shipping company'),
	 (1,'USPS',1,2,0,2,'Shipping company'),
	 (2,'UPS',1,2,0,2,'Shipping company'),
	 (3,'FedEx',1,2,0,2,'Shipping company'),
	 (4,'Local pickup',1,2,0,2,'Shipping company');
	 
INSERT INTO cmsdb.creteria2 (CRETERIA2_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
     (0,'Not selected',1,2,0,2,'RAM'),
	 (1,'1GB',1,2,0,2,'RAM'),
	 (2,'2GB',1,2,0,2,'RAM'),
	 (3,'3GB',1,2,0,2,'RAM'),
	 (4,'4GB',1,2,0,2,'RAM'),
	 (5,'6GB',1,2,0,2,'RAM'),
	 (6,'8GB',1,2,0,2,'RAM'),
	 (7,'10GB',1,2,0,2,'RAM'),
	 (8,'12GB',1,2,0,2,'RAM'),
	 (9,'14GB',1,2,0,2,'RAM'),
	 (10,'16GB',1,2,0,2,'RAM'),
	 (11,'24GB',1,2,0,2,'RAM'),
	 (12,'32GB',1,2,0,2,'RAM'),
	 (13,'48GB',1,2,0,2,'RAM');
	 
INSERT INTO cmsdb.creteria3 (CRETERIA3_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Display resolution'),
	 (1,'800x600',1,2,0,2,'Display resolution'),
	 (2,'1024x768',1,2,0,2,'Display resolution'),
	 (3,'1290x1024',1,2,0,2,'Display resolution'),
	 (4,'1920x1080',1,2,0,2,'Display resolution');
	 
	 
INSERT INTO cmsdb.creteria4 (CRETERIA4_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Color'),
	 (1,'Red',1,2,0,2,'Color'),
	 (2,'Grey',1,2,0,2,'Color'),
	 (3,'White',1,2,0,2,'Color'),
	 (4,'Black',1,2,0,2,'Color'),
	 (5,'Blue',1,2,0,2,'Color');
	 
	 
INSERT INTO cmsdb.creteria5 (CRETERIA5_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Display frequency'),
	 (1,'60',1,2,0,2,'Display frequency'),
	 (2,'100',1,2,0,2,'Display frequency'),
	 (3,'120',1,2,0,2,'Display frequency'),
	 (4,'144',1,2,0,2,'Display frequency'),
	 (5,'244',1,2,0,2,'Display frequency');
	 
	 
INSERT INTO cmsdb.creteria6 (CRETERIA6_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Item condition'),
	 (1,'Any condition',1,2,0,2,'Item condition'),
	 (2,'New',1,2,0,2,'Item condition'),
	 (3,'Like new',1,2,0,2,'Item condition'),
	 (4,'Open box',1,2,0,2,'Item condition'),
	 (5,'Refurbished',1,2,0,2,'Item condition'),
	 (6,'Used',1,2,0,2,'Item condition');
	 
INSERT INTO cmsdb.creteria7 (CRETERIA7_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Return policy'),
	 (1,'Free return',1,2,0,2,'Return policy'),
	 (2,'Buyer pays retrun',1,2,0,2,'Return policy');
	 
INSERT INTO cmsdb.creteria8 (CRETERIA8_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Delievry policy'),
	 (1,'Free delievry',1,2,0,2,'Delievry policy'),
	 (2,'Buyer pays retrun',1,2,0,2,'Delievry policy');
	 
INSERT INTO cmsdb.creteria9 (CRETERIA9_ID,NAME,ACTIVE,LANG_ID,LINK_ID,CATALOG_ID,LABEL) VALUES
	 (0,'Not selected',1,2,0,2,'Delievry from'),
	 (1,'U.S.A',1,2,0,2,'Delievry from'),
	 (2,'From abroad',1,2,0,2,'Delievry from');

	 
INSERT INTO cmsdb.currency (CURRENCY_ID,RATE,CURRENCY_LABLE,CURRENCY_DESC,ACTIVE,CURRENCY_CD,CURSDATE,CHANGEDATE) VALUES
	 (0,0.0,'UNKNOWN','Not selected',1,'0',NULL,'2024-02-26 04:25:13'),
	 (1,36.0,'USD','U.S Dollar',1,'USD',NULL,'2024-02-26 04:25:13'),
	 (2,45.0,'EUR',' EURO',1,'EUR',NULL,'2024-02-26 04:25:13'),
	 (3,1.0,'RUB','RF Ruble',1,'RUB',NULL,'2024-02-26 04:25:13'),
	 (4,1.0,'CNY','China money',1,'CNY',NULL,'2024-02-26 04:25:13'),
	 (5,1.0,'JPY','Japan Yen',1,'JPY',NULL,'2024-02-26 04:25:13'),
	 (6,1.0,'TRY','Turkey Lira',1,'TRY',NULL,'2024-02-26 04:25:13'),
	 (7,1.0,'MXN','Mexico Peso',1,'MXN',NULL,'2024-02-26 04:25:13'),
	 (8,1.0,'CAD','United Kingdom Pound',1,'CAD',NULL,'2024-02-26 04:25:13'),
	 (9,1.0,'GBP','Brazil Real',1,'GBP',NULL,'2024-02-26 04:25:13');
INSERT INTO cmsdb.currency (CURRENCY_ID,RATE,CURRENCY_LABLE,CURRENCY_DESC,ACTIVE,CURRENCY_CD,CURSDATE,CHANGEDATE) VALUES
	 (10,1.0,'BRL','India Rupee',1,'BRL',NULL,'2024-02-26 04:25:13'),
	 (11,1.0,'INR','Bitcoin',1,'INR',NULL,'2024-02-26 04:25:13'),
	 (12,1.0,'BTC','Litecoin',1,'BTC',NULL,'2024-02-26 04:25:13'),
	 (13,1.0,'LTC','Ethereum',1,'LTC',NULL,'2024-02-26 04:25:13'),
	 (14,1.0,'ETH','PayPal Dollar',1,'ETH',NULL,'2024-02-26 04:25:13'),
	 (15,1.0,'PYUSD','U.S Digital Dollar',1,'PYUSD',NULL,'2024-02-26 04:25:13'),
	 (16,1.0,'USDT','USD Tether',1,'PYUSD',NULL,'2024-02-26 04:25:13'),
	 (17,1.0,'TUSD','TrueUSD',1,'PYUSD',NULL,'2024-02-26 04:25:13'),
	 (18,1.0,'CBDC','USD Coin',1,'CBDC',NULL,'2024-02-26 04:25:13');

INSERT INTO cmsdb.lang (LANG_ID,LABLE,DESCRIPTION,ACTIVE) VALUES
	 (1,'ru','Russian',1),
	 (2,'en','English',1);
INSERT INTO cmsdb.shop (SHOP_CD,OWNER_ID,LOGIN,SITE_ID,PAY_GATEWAY_ID,PASSWD,CDATE) VALUES
	 (84473,100728482950021285,'HCO-CENTE-406',2,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),
	 (84473,-110,'HCO-CENTE-406',-10,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),
	 (84473,-119,'HCO-CENTE-406',-9,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),
	 (84473,-118,'HCO-CENTE-406',-8,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),
	 (84473,-117,'HCO-CENTE-406',-7,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),
	 (84473,-116,'HCO-CENTE-406',-6,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00'),	 
	 (84473,-114,'HCO-CENTE-406',-4,1,'91KiBFRtE8fF7VHc8tvr','2024-02-26 00:00:00');
	 
INSERT INTO cmsdb.site (SITE_ID,HOST,OWNER,ACTIVE,HOME_DIR,SITE_DIR,PERSON,PHONE,ADDRESS,SUBJECT_SITE,NICK_SITE,COMPANY_NAME) VALUES
	 (2,'localhost',100728482950021285,1,'shops.online-spb.com','shops.online-spb.com','Administrator','+z(xxx)yyy-xxxx','',NULL,NULL,NULL),
	 (-10,'www.usps.irr.bz',-110,1,'shops.online-spb.com','usps.online-spb.com','usps administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL),
	 (-9,'www.ups.irr.bz',-119,1,'shops.online-spb.com','ups.online-spb.com','ups administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL),
	 (-8,'www.fedex.irr.bz',-118,1,'shops.online-spb.com','fedex.online-spb.com','fedex administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL),	
	 (-7,'www.dhl.irr.bz',-117,1,'shops.online-spb.com','dhl.online-spb.com','dhl administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL),
	 (-6,'www.fullfilment.irr.bz',-116,1,'shops.online-spb.com','fullfilment.online-spb.com','fullfilment administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL),
	 (-4,'www.resolutioncenter.irr.bz',-114,1,'shops.online-spb.com','resolutioncenter.online-spb.com','resolutioncenter administrator','+z(xxx)yyy-xxxx','no created',NULL,NULL,NULL);
	 
-- fixed
-- INSERT INTO cmsdb.shipping_company (SHIPPING_COMPANY_ID, NAME, DESCRIPTION, RATING_ID, VERIFIED, USER_ID, SITE_ID) VALUES
--	(-10, 'USPS', 'U.S Postal office' , 1, 0, -110, -10),
--	(-9, 'UPS', 'Unted Postal office' , 1, 0, -119, -9),
--	(-8, 'FedEx', 'FedEx Postal office' , 1, 0, -118, -8),
--	(-7, 'DHL', 'DHL Postal office' , 1, 0, -117, -7); 

-- The four rows above belong to the carrier sites themselves (-10..-7), so
-- Order.jsp on the shop site (SITE_ID = 2, localhost) rendered an empty
-- "Shipping company" dropdown: OrderAction.collectData calls
-- OrderFaced.getShippingCompanyList(orderBeanId), which selects from
-- shipping_company for the current site only and therefore found no rows.
-- The same carriers must also be registered as shipping companies OF the
-- shop site, otherwise no order can be assigned a shipper and the
-- shipping_cost rows (all pointing at company -10) can never be used.
 INSERT INTO cmsdb.shipping_company (SHIPPING_COMPANY_ID, NAME, DESCRIPTION, RATING_ID, VERIFIED, USER_ID, SITE_ID) VALUES
	(-10, 'USPS', 'U.S Postal office' , 3, 1, -110, 2),
	(-9, 'UPS', 'Unted Postal office' , 3, 1, -119, 2),
	(-8, 'FedEx', 'FedEx Postal office' , 3, 1, -118, 2),
	(-7, 'DHL', 'DHL Postal office' , 3, 1, -117, 2)
-- FIX: ids -10..-7 are the PRIMARY KEY of shipping_company and the rows already
-- exist (as the carrier sites' own entries, SITE_ID -10..-7). A plain INSERT
-- therefore aborted with "Duplicate entry '-10' for key 'shipping_company.PRIMARY'"
-- and NOTHING was inserted, so the shop site (SITE_ID = 2) still had no carrier
-- and the Order.jsp dropdown stayed empty. Upsert instead of insert: on a fresh
-- database it adds the four rows, on an existing one it re-points them at site 2.
 ON DUPLICATE KEY UPDATE SITE_ID = VALUES(SITE_ID), NAME = VALUES(NAME),
	DESCRIPTION = VALUES(DESCRIPTION), RATING_ID = VALUES(RATING_ID),
	VERIFIED = VALUES(VERIFIED), USER_ID = VALUES(USER_ID); 
	
 INSERT INTO cmsdb.resolution_center (RESOLUTION_CENTER_ID, NAME, DESCRIPTION, RATING_ID, VERIFIED, USER_ID, SITE_ID) VALUES
	(-4, 'Corp Resolutioncenter', 'Corp Resolutioncenter' , 1, 0, -114, -4);

INSERT INTO cmsdb.images (IMAGE_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714142,'SAMSUN_ 32-Inch_Odyssey_p11.png','imgpositions/101055362458714142.png',100728482950021285),
	 (101055362458714150,'ASUS_ROG_Swift 27_1440P_p11.png','imgpositions/101055362458714150.png',100728482950021285),
	 (101055362458714153,'SAMSUNG_27-Inch_Odyssey_G6_p11.png','imgpositions/101055362458714153.png',100728482950021285),
	 (101055362458714165,'Dell_G2725D_Gaming_Monitor-27-inch_p11.png','imgpositions/101055362458714165.png',100728482950021285),
	 (101055362458714179,'Dell_G2725D_Gaming_Monitor-27-inch_p11.png','imgpositions/101055362458714179.png',100728482950021285),
	 (101055362458714181,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p1.jpg','imgpositions/101055362458714181.jpg',100728482950021285),
	 (101055362458714196,'SANSUI_34_Inch_p11.png','imgpositions/101055362458714196.png',100728482950021285),
	 (101055362458714208,'LG_45GS95QE_Ultragear_OLED_7_P11.png','imgpositions/101055362458714208.png',100728482950021285),
	 (101055362458714221,'LG 32GQ750-B Ultragear_P11.png','imgpositions/101055362458714221.png',100728482950021285),
	 (101055362458714231,'ViewSonic_VP3256-4K_32_Inch_P11.png','imgpositions/101055362458714231.png',100728482950021285);
INSERT INTO cmsdb.images (IMAGE_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714245,'34 Inch Ultrawide_P11.png','imgpositions/101055362458714245.png',100728482950021285),
	 (101055362458714301,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p11.png','imgpositions/101055362458714301.png',100728482950021285),
	 (101055362458714302,'SAMSUNG_Galaxy_S24_p11.png','imgpositions/101055362458714302.png',100728482950021285),
	 (101055362458714303,'OnePlus_12_12GB_p11.png','imgpositions/101055362458714303.png',100728482950021285),
	 (101055362458714304,'Lenovo_V15_Gen_4_Business_Laptop_p11.png','imgpositions/101055362458714304.png',100728482950021285),
	 (101055362458714305,'Alienware_AW3423DW_p11.png','imgpositions/101055362458714305.png',100728482950021285),
	 (101087870529306624,'bordo_baklajan.jpg','imgpositions/101087870529306624.jpg',100728482950021285),
	 (101087870529306627,'bordo_glina.jpg','imgpositions/101087870529306627.jpg',100728482950021285),
	 (101087870529306632,'bordo_haki.jpg','imgpositions/101087870529306632.jpg',100728482950021285),
	 (101087870529306636,'bordo_noch.jpg','imgpositions/101087870529306636.jpg',100728482950021285);
INSERT INTO cmsdb.images (IMAGE_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101087870529306641,'bordo_orange.jpg','imgpositions/101087870529306641.jpg',100728482950021285),
	 (101087870529306644,'bordo_pepel.jpg','imgpositions/101087870529306644.jpg',100728482950021285),
	 (101087870529306648,'bordo_red.jpg','imgpositions/101087870529306648.jpg',100728482950021285),
	 (101087870529306652,'bordo_shokolad.jpg','imgpositions/101087870529306652.jpg',100728482950021285),
	 (201055362458714245,'34 Inch Ultrawide_P11.png','imgpositions/201055362458714245.png',201055362458714245),
	 (201055362458714301,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p11.png','imgpositions/201055362458714301.png',201055362458714301),
	 (201055362458714302,'SAMSUNG_Galaxy_S24_p11.png','imgpositions/201055362458714302.png',201055362458714302);

	 
	 
	 
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714143,'SAMSUN_ 32-Inch_Odyssey_p11.png','big_imgpositions/101055362458714143.png',100728482950021285),
	 (101055362458714144,'SAMSUN_ 32-Inch_Odyssey_p3.jpg','big_imgpositions/101055362458714144.jpg',100728482950021285),
	 (101055362458714145,'SAMSUN_ 32-Inch_Odyssey_p4.jpg','big_imgpositions/101055362458714145.jpg',100728482950021285),
	 (101055362458714147,'SAMSUN_ 32-Inch_Odyssey_p5.jpg','big_imgpositions/101055362458714147.jpg',100728482950021285),
	 (101055362458714148,'SAMSUN_ 32-Inch_Odyssey_p22.png','big_imgpositions/101055362458714148.png',100728482950021285),
	 (101055362458714149,'SAMSUN_ 32-Inch_Odyssey_p66.png','big_imgpositions/101055362458714149.png',100728482950021285),
	 (101055362458714151,'ASUS_ROG_Swift 27_1440P_p11.png','big_imgpositions/101055362458714151.png',100728482950021285),
	 (101055362458714152,'ASUS_ROG_Swift 27_1440P_p22.png','big_imgpositions/101055362458714152.png',100728482950021285),
	 (101055362458714154,'SAMSUNG_27-Inch_Odyssey_G6_p11.png','big_imgpositions/101055362458714154.png',100728482950021285),
	 (101055362458714155,'SAMSUNG_27-Inch_Odyssey_G6_p22.png','big_imgpositions/101055362458714155.png',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714157,'SAMSUNG_27-Inch_Odyssey_G6_p3.jpg','big_imgpositions/101055362458714157.jpg',100728482950021285),
	 (101055362458714163,'SAMSUNG_27-Inch_Odyssey_G6_p6.jpg','big_imgpositions/101055362458714163.jpg',100728482950021285),
	 (101055362458714166,'Dell_G2725D_Gaming_Monitor-27-inch_p11.png','big_imgpositions/101055362458714166.png',100728482950021285),
	 (101055362458714167,'Dell_G2725D_Gaming_Monitor-27-inch_p2.jpg','big_imgpositions/101055362458714167.jpg',100728482950021285),
	 (101055362458714169,'Dell_G2725D_Gaming_Monitor-27-inch_p3.jpg','big_imgpositions/101055362458714169.jpg',100728482950021285),
	 (101055362458714171,'Dell_G2725D_Gaming_Monitor-27-inch_p5.jpg','big_imgpositions/101055362458714171.jpg',100728482950021285),
	 (101055362458714173,'Dell_G2725D_Gaming_Monitor-27-inch_p4.jpg','big_imgpositions/101055362458714173.jpg',100728482950021285),
	 (101055362458714174,'Dell_G2725D_Gaming_Monitor-27-inch_p6.jpg','big_imgpositions/101055362458714174.jpg',100728482950021285),
	 (101055362458714175,'Dell_G2725D_Gaming_Monitor-27-inch_p7.jpg','big_imgpositions/101055362458714175.jpg',100728482950021285),
	 (101055362458714177,'Dell_G2725D_Gaming_Monitor-27-inch_p4.jpg','big_imgpositions/101055362458714177.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714180,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p1.jpg','big_imgpositions/101055362458714180.jpg',100728482950021285),
	 (101055362458714183,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p11.png','big_imgpositions/101055362458714183.png',100728482950021285),
	 (101055362458714186,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p2.jpg','big_imgpositions/101055362458714186.jpg',100728482950021285),
	 (101055362458714190,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p3.jpg','big_imgpositions/101055362458714190.jpg',100728482950021285),
	 (101055362458714192,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p4.jpg','big_imgpositions/101055362458714192.jpg',100728482950021285),
	 (101055362458714193,'Dell_S3422DWG Curved_Gaming Monitor_34_Inch_p5.jpg','big_imgpositions/101055362458714193.jpg',100728482950021285),
	 (101055362458714197,'SANSUI_34_Inch_p11.png','big_imgpositions/101055362458714197.png',100728482950021285),
	 (101055362458714198,'SANSUI_34_Inch_p2.jpg','big_imgpositions/101055362458714198.jpg',100728482950021285),
	 (101055362458714201,'SANSUI_34_Inch_p3.jpg','big_imgpositions/101055362458714201.jpg',100728482950021285),
	 (101055362458714202,'SANSUI_34_Inch_p4.jpg','big_imgpositions/101055362458714202.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714204,'SANSUI_34_Inch_p5.jpg','big_imgpositions/101055362458714204.jpg',100728482950021285),
	 (101055362458714206,'SANSUI_34_Inch_p11.png','big_imgpositions/101055362458714206.png',100728482950021285),
	 (101055362458714209,'LG_45GS95QE_Ultragear_OLED_7_P11.png','big_imgpositions/101055362458714209.png',100728482950021285),
	 (101055362458714210,'LG_45GS95QE_Ultragear_OLED_7_P11.png','big_imgpositions/101055362458714210.png',100728482950021285),
	 (101055362458714211,'LG_45GS95QE_Ultragear_OLED_7_P2.jpg','big_imgpositions/101055362458714211.jpg',100728482950021285),
	 (101055362458714213,'LG_45GS95QE_Ultragear_OLED_7_P3.jpg','big_imgpositions/101055362458714213.jpg',100728482950021285),
	 (101055362458714215,'LG_45GS95QE_Ultragear_OLED_7_P4.jpg','big_imgpositions/101055362458714215.jpg',100728482950021285),
	 (101055362458714217,'LG_45GS95QE_Ultragear_OLED_7_P5.jpg','big_imgpositions/101055362458714217.jpg',100728482950021285),
	 (101055362458714219,'LG_45GS95QE_Ultragear_OLED_7_P6.jpg','big_imgpositions/101055362458714219.jpg',100728482950021285),
	 (101055362458714222,'LG 32GQ750-B Ultragear_P11.png','big_imgpositions/101055362458714222.png',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714223,'LG 32GQ750-B Ultragear_P22.png','big_imgpositions/101055362458714223.png',100728482950021285),
	 (101055362458714225,'LG 32GQ750-B Ultragear_P33.png','big_imgpositions/101055362458714225.png',100728482950021285),
	 (101055362458714227,'LG 32GQ750-B Ultragear_P44.png','big_imgpositions/101055362458714227.png',100728482950021285),
	 (101055362458714229,'LG 32GQ750-B Ultragear_P5.jpg','big_imgpositions/101055362458714229.jpg',100728482950021285),
	 (101055362458714232,'ViewSonic_VP3256-4K_32_Inch_P11.png','big_imgpositions/101055362458714232.png',100728482950021285),
	 (101055362458714237,'ViewSonic_VP3256-4K_32_Inch_P2.jpg','big_imgpositions/101055362458714237.jpg',100728482950021285),
	 (101055362458714238,'ViewSonic_VP3256-4K_32_Inch_P3.jpg','big_imgpositions/101055362458714238.jpg',100728482950021285),
	 (101055362458714240,'ViewSonic_VP3256-4K_32_Inch_P5.jpg','big_imgpositions/101055362458714240.jpg',100728482950021285),
	 (101055362458714242,'ViewSonic_VP3256-4K_32_Inch_P5.jpg','big_imgpositions/101055362458714242.jpg',100728482950021285),
	 (101055362458714244,'ViewSonic_VP3256-4K_32_Inch_P4.jpg','big_imgpositions/101055362458714244.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714246,'34 Inch Ultrawide_P11.png','big_imgpositions/101055362458714246.png',100728482950021285),
	 (101055362458714247,'34 Inch Ultrawide_P22.png','big_imgpositions/101055362458714247.png',100728482950021285),
	 (101055362458714249,'34 Inch Ultrawide_P3.jpg','big_imgpositions/101055362458714249.jpg',100728482950021285),
	 (101055362458714251,'34 Inch Ultrawide_P4.jpg','big_imgpositions/101055362458714251.jpg',100728482950021285),
	 (101055362458714253,'34 Inch Ultrawide_P5.jpg','big_imgpositions/101055362458714253.jpg',100728482950021285),
	 (101055362458714255,'34 Inch Ultrawide_P6.jpg','big_imgpositions/101055362458714255.jpg',100728482950021285),
	 (101055362458714257,'SAMSUNG_Galaxy_S24_p11.png','big_imgpositions/101055362458714257.png',100728482950021285),
	 (101055362458714258,'SAMSUNG_Galaxy_S24_p2.jpg','big_imgpositions/101055362458714258.jpg',100728482950021285),
	 (101055362458714260,'SAMSUNG_Galaxy_S24_p3.jpg','big_imgpositions/101055362458714260.jpg',100728482950021285),
	 (101055362458714262,'SAMSUNG_Galaxy_S24_p4.jpg','big_imgpositions/101055362458714262.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714264,'OnePlus_12_12GB_p11.png','big_imgpositions/101055362458714264.png',100728482950021285),
	 (101055362458714265,'HP_14_Laptop_Intel_Celeron_N4020_p11.png','big_imgpositions/101055362458714265.png',100728482950021285),
	 (101055362458714266,'HP_14_Laptop_Intel_Celeron_N4020_p44.png','big_imgpositions/101055362458714266.png',100728482950021285),
	 (101055362458714268,'HP_14_Laptop_Intel_Celeron_N4020_p2.jpg','big_imgpositions/101055362458714268.jpg',100728482950021285),
	 (101055362458714270,'HP_14_Laptop_Intel_Celeron_N4020_p3.jpg','big_imgpositions/101055362458714270.jpg',100728482950021285),
	 (101055362458714272,'HP_14_Laptop_Intel_Celeron_N4020_p5.jpg','big_imgpositions/101055362458714272.jpg',100728482950021285),
	 (101055362458714274,'HP_14_Laptop_Intel_Celeron_N4020_p6.jpg','big_imgpositions/101055362458714274.jpg',100728482950021285),
	 (101055362458714277,'HP_ Newest_14_Ultral_Ligh_ Laptop_p11.png','big_imgpositions/101055362458714277.png',100728482950021285),
	 (101055362458714278,'HP_ Newest_14_Ultral_Ligh_ Laptop_p22.png','big_imgpositions/101055362458714278.png',100728482950021285),
	 (101055362458714280,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p11.png','big_imgpositions/101055362458714280.png',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714282,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p2.jpg','big_imgpositions/101055362458714282.jpg',100728482950021285),
	 (101055362458714283,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p3.jpg','big_imgpositions/101055362458714283.jpg',100728482950021285),
	 (101055362458714285,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p4.jpg','big_imgpositions/101055362458714285.jpg',100728482950021285),
	 (101055362458714287,'Acer_Aspire_3_A315-24P-R7VH_Slim_Laptop_p5.jpg','big_imgpositions/101055362458714287.jpg',100728482950021285),
	 (101055362458714289,'Lenovo_V15_Gen_4_Business_Laptop_p11.png','big_imgpositions/101055362458714289.png',100728482950021285),
	 (101055362458714290,'Lenovo_V15_Gen_4_Business_Laptop_p2.jpg','big_imgpositions/101055362458714290.jpg',100728482950021285),
	 (101055362458714294,'Lenovo_V15_Gen_4_Business_Laptop_p3.jpg','big_imgpositions/101055362458714294.jpg',100728482950021285),
	 (101055362458714295,'Lenovo_V15_Gen_4_Business_Laptop_p4.jpg','big_imgpositions/101055362458714295.jpg',100728482950021285),
	 (101055362458714297,'Lenovo_V15_Gen_4_Business_Laptop_p5.jpg','big_imgpositions/101055362458714297.jpg',100728482950021285),
	 (101055362458714298,'Lenovo_V15_Gen_4_Business_Laptop_p6.jpg','big_imgpositions/101055362458714298.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101055362458714299,'Lenovo_V15_Gen_4_Business_Laptop_p6.jpg','big_imgpositions/101055362458714299.jpg',100728482950021285),
	 (101055362458714307,'Alienware_AW3423DW_p2.jpg','big_imgpositions/101055362458714307.jpg',100728482950021285),
	 (101055362458714310,'Alienware_AW3423DW_p3.jpg','big_imgpositions/101055362458714310.jpg',100728482950021285),
	 (101055362458714311,'Alienware_AW3423DW_p4.jpg','big_imgpositions/101055362458714311.jpg',100728482950021285),
	 (101055362458714313,'Alienware_AW3423DW_p5.jpg','big_imgpositions/101055362458714313.jpg',100728482950021285),
	 (101087870529306625,'bordo_baklajan.jpg','big_imgpositions/101087870529306625.jpg',100728482950021285),
	 (101087870529306628,'bordo_glina.jpg','big_imgpositions/101087870529306628.jpg',100728482950021285),
	 (101087870529306633,'bordo_haki.jpg','big_imgpositions/101087870529306633.jpg',100728482950021285),
	 (101087870529306637,'bordo_noch.jpg','big_imgpositions/101087870529306637.jpg',100728482950021285),
	 (101087870529306642,'bordo_orange.jpg','big_imgpositions/101087870529306642.jpg',100728482950021285);
INSERT INTO cmsdb.big_images (BIG_IMAGES_ID,IMGNAME,IMG_URL,USER_ID) VALUES
	 (101087870529306645,'bordo_pepel.jpg','big_imgpositions/101087870529306645.jpg',100728482950021285),
	 (101087870529306649,'bordo_red.jpg','big_imgpositions/101087870529306649.jpg',100728482950021285),
	 (101087870529306653,'bordo_shokolad.jpg','big_imgpositions/101087870529306653.jpg',100728482950021285);

	 

INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (100728482950021305,'OnePlus 12,12GB RAM+256GB,Dual-SIM','
<r>OnePlus 12,12GB RAM+256GB,Dual-SIM</r>','',0.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714303,101055362458714264,NULL,NULL,2,NULL,NULL,-1,0,NULL,NULL,NULL,'2024-10-08 19:54:50',NULL,0,0,NULL,0,'
<r>Brand	OnePlus
</r><r>Operating System	Android 14
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	12 GB
</r><r>Screen Size	6.82 Inches
</r><r>Resolution	3168 x 1440
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	OnePlus 12
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>Comes with 6 months of Google One and 3 months of Youtube Premium with purchase of OnePlus 12. (New accounts only for each service to qualify)
</r><r>Pure Performance: The OnePlus 12 is powered by the latest Snapdragon 8 Gen 3, with up to 16GB of RAM. The improved processing power and graphics performance is supported by the latest Dual Cryo-velocity VC cooling chamber, which improves thermal efficiency and heat dissipation.
</r><r>Brilliant Display: The OnePlus 12 has a stunning 2k 120Hz Super Fluid Display, with advanced LTPO for a brighter, smoother, and more vibrant viewing experience. With 4500 nits peak brightness, enjoying your content is effortless anywhere.
</r><r>Powered by Trinity Engine: The OnePlus 12`s performance is optimized by the Trinity Engine, which accelerates various softwares to maximize the performance of your device. These include RAM-Vita, CPU-Vita, ROM-Vita, HyperTouch, HyperBoost, and HyperRendering (visit the official product page for more information).
</r><r>Powerful, Versatile Camera: Explore the new 4th Gen Hasselblad Camera System for Mobile, packed with computational photography software and improved sensors. The OnePlus 12 boasts a 50MP primary camera, a 64MP 3x Periscope Lens, and a 48MP Ultra-Wide Camera, all packed together with industry-leading Hasselblad color science.
</r>',2,'O',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021306,'SAMSUNG Galaxy S24 Ultra Cell Phone','
<r>SAMSUNG Galaxy S24 Ultra Cell Phone</r>','',299.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714302,101055362458714257,NULL,NULL,2,NULL,NULL,-1,0,NULL,NULL,NULL,'2024-10-08 19:52:56',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Operating System	Android 14, One UI 6.1
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	256 GB
</r><r>Screen Size	6.8 Inches
</r><r>Resolution	3120 x 1440 pixels
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	Galaxy S24 Ultra
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>CIRCLE and SEARCH¹ IN A SNAP: What’s your favorite influencer wearing? Where’d they go on vacation? What’s that word mean? Don’t try to describe it — use Circle to Search1 with Google to get the answer; With S24 Series, circle it on your screen and learn more
</r><r>REAL EASY, REAL-TIME TRANSLATIONS: Speak foreign languages on the spot with Live Translate²; Unlock the power of convenient communication with near real-time voice translations, right through your Samsung Phone app
</r><r>NOTE SMARTER, NOT HARDER: Focus on capturing your notes and spend less time perfecting them; Note Assist³ will summarize, format, and even translate them for you; All of your notes are organized neatly so that you can find what you need
</r><r>BRING DETAILS OUT OF THE DARKNESS: Brighten up your night with Nightography on S24 Ultra; Want a closer look? Zoom in from a distance, even in low light
</r><r>MORE WOW, LESS WORK: Turn every photo into a post-worthy masterpiece; Move or remove objects; Fill in empty space; Simply snap a pic and take it from great to jaw-dropping with Generative Edit⁴
</r><r>CAPTURE. SHARE. IMPRESS: Create crystal-clear content worth sharing; From bustling cityscape to a serene landscape, capture a masterpiece of detail with 200MP camera and let S24 Ultra adjust each hue and shade to reflect the world as you see it
</r><r>OUR MOST POWERFUL GALAXY SMARTPHONE YET: Jump seamlessly between apps without the wait and see content in high quality with our fastest processor yet, Snapdragon 8 Gen 3 for Galaxy⁵
</r><r>PUT YOUR BEST TEXT FORWARD: Say the right thing at the right time in no time with Chat Assist⁶; Get real-time tone suggestions to make your writing sound more professional or conversational; Plus, make typos a thing of the past
</r><r>GAME and STREAM, EDGE TO EDGE: Take your streaming and gaming to the edge with S24 Ultra; Whether you’re watching, winning or even writing, the brighter, flatter screen gives you more room to do what you love
</r><r>WRITE WHEN YOU NEED IT: With S Pen, you can easily make last-minute edits to work projects, jot down your latest idea or sign important docs on your S24 Ultra; Take doing to whole new heights with the built-in S Pen
</r>',2,'S',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021307,'Lenovo V15 Gen 4 Business Laptop, 15.6','
<r>Lenovo V15 Gen 4 Business Laptop, 15.6"</r>','',549.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714304,101055362458714289,NULL,NULL,2,NULL,NULL,-1,0,NULL,NULL,NULL,'2024-10-08 19:55:55',NULL,0,0,NULL,0,'
<r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r>High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r>Processor AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r>Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r>Tech Specs 1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r>Operating System Windows 11 Pro</r>',2,'N',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021308,'ViewSonic VP3256-4K 32','
<r>ViewSonic VP3256-4K 32 Inch 4K UHD IPS Ergonomic Monitor</r>','',499.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714231,101055362458714232,NULL,NULL,2,NULL,NULL,-1,0,NULL,NULL,NULL,'2024-10-08 19:11:49',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021309,'Gawfolk 34 Inch Ultrawide ','
<r>The best Deal today
</r><r>Gawfolk 34 Inch Ultrawide
</r>','',100.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714245,101055362458714247,NULL,NULL,2,NULL,NULL,-1,0,NULL,NULL,NULL,'2024-10-08 19:10:01',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor - The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming - The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen - Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service. We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'G',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021310,'34 Inch Ultrawide','
<r>34 Inch Ultrawide Curved 144Hz Gaming Computer Monitor 1500R PC Screen 21:9 UWQHD (3440x1440),Adaptive Sync,178° Viewing Angle,HDMI、Display Port,Compatible with Wall mounting - Black</r>','',219.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714245,101055362458714246,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:02:18',NULL,0,0,NULL,0,'
<r>
</r><r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'I',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021311,'ViewSonic VP3256-4K 32 Inch','
<r>ViewSonic VP3256-4K 32 Inch 4K UHD IPS Ergonomic Monitor with Ultra-Thin Bezels, Color Accuracy, Pantone Validated, 60W USB C</r>','',499.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714231,101055362458714232,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 18:56:01',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021312,'LG 32GQ750-B','
<r>LG 32GQ750-B Ultragear OLED Curved Gaming Monitor 45-Inch WQHD 800R 240Hz 0.03ms DisplayHDR True Black 400 AMD FreeSync Premium Pro NVIDIA G-Sync HDMI 2.1 DisplayPort Tilt/Height/Swivel Stand - Black</r>','',0.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714221,101055362458714222,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 18:48:14',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'L',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021313,'LG 45GS95QE Ultragear','
<r>LG 45GS95QE Ultragear OLED Curved Gaming Monitor 45-Inch WQHD 800R 240Hz 0.03ms DisplayHDR True Black 400 AMD FreeSync Premium Pro NVIDIA G-Sync HDMI 2.1 DisplayPort Tilt/Height/Swivel Stand - Black</r>','',1042.14,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714208,101055362458714210,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 18:35:29',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021314,'SANSUI 34-Inch Curved Gaming Monitor','
<r>SANSUI 34-Inch Curved Gaming Monitor UWQHD 3440 x 1440 165Hz Curved 1500R - PIP/PBP, 1ms(MPRT), HDR, 300nits, sRGB 125%, DCI-P3 95%,FreeSync,HDMI2.1(TMDS) x2,DP1.4 x2(ES-G34C5 DP Cable Included)</r>','',219.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714196,101055362458714197,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 18:16:22',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'S',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (100728482950021315,'Dell S3422DWG Curved Gaming Monitor','
<r>Dell S3422DWG Curved Gaming Monitor - 34 Inch 1800R Curved Screen with 144Hz Refresh Rate, WQHD (3440 x 1440) Display, HDMI, DP to DP 1.4 Cable, AMD FreeSync - Black</r>','',299.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714181,101055362458714183,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 18:05:28',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021316,'Dell G2725D Gaming Monitor','
<r>Dell G2725D Gaming Monitor - 27-inch QHD (2560x1440) up to 180 Hz, 2ms Response time, 99% RGB Display, DisplayPort/HDMI Connectivity, AMD FreeSync, Comfortview, Tilt Adjustable - Black
</r>','',199.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714165,101055362458714166,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 17:55:24',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021317,'SAMSUNG 27-Inch Odyssey G6 ','
<r>SAMSUNG 27-Inch Odyssey G6 (G60SD) Series OLED Gaming Monitor with QHD 360Hz 0.03ms, Anti-Glare, Sleek Metal Design, LS27DG602SNXZA, 2024, 3 Year Warranty</r>','',649.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714153,101055362458714154,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 17:48:26',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021318,'ASUS ROG Swift 27 inch ','
<r>ASUS ROG Swift 27 inch 1440P OLED DSC Gaming Monitor (PG27AQDM) - QHD (2560x1440), 240Hz, 0.03ms, G-SYNC Compatible, Anti-Glare Micro-Texture Coating, 99% DCI-P3, True 10-bit, DisplayPort,Black</r>','',754.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714150,101055362458714151,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 17:29:09',NULL,0,0,NULL,0,'
<r>Brand	ASUS
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>
</r><r>26.5-inch QHD (2560 x 1440) OLED gaming monitor with 240 Hz refresh rate for immersive gaming
</r><r>Highly efficient custom heatsink, plus intelligent voltage optimization for better heat management to reduce the risk of burn-in
</r><r>Anti-glare micro-texture coating reduces reflections for accurate colors and better viewing experiences
</r><r>
</r><r>G-Sync compatible technology delivers seamless, tear-free gaming; and optional uniform brightness setting ensures consistent luminance levels
</r><r>DisplayWidget Center enables easy OLED and monitor settings adjustments with a mouse
</r><r>What’s in the box: USB 3.0 cable, DisplayPort cable, HDMI cable, Power cord and adapter, ROG pouch, ROG sticker, VESA mount kit, Color pre-calibration report, Quick start guide, Warranty Card
</r><r>3-month Adobe Creative Cloud Subscription: Receive complimentary access with the purchase of this product</r>',2,'A',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021319,'SAMSUNG 32-Inch Odyssey ','
<r>SAMSUNG 32-Inch Odyssey OLED G8 (G80SD) Series 4K UHD Smart Gaming Monitor, 240Hz 0.03ms, G-Sync Compatible, Glare-Free Display, Gaming Hub, Sleek Metal Design, 3 Year Warranty, LS32DG802SNXZA, 2024</r>','',800.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,101055362458714142,101055362458714143,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 17:24:37',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021320,' Item review section 1 ','<r> Item review section - You can talk about  goods or news and a price of sheet on your forum.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,100728482950021319,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r>  The information module for main page 1.</r><r> You need to change the contents of this module to place here  your information. </r><r> For this you should enter on site under  your the password  </r><r> So you can observe buttons:  remove, edit, update (To add the new module). </r><r> In during  change of the informational unit you can put its other section .</r><r> You also can edit ,  add, delete this section name. </r><r> Also you can change parameters  criteria for search of  information  unit </r><r> For this purpose should change criteria on the form of change of the information with title (Installation of criteria for information search on a site ). </r>',2,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021321,'Item review section 2 ','<r>Item review section - Thanks from Center Business Solutions Ltd  that  you using  this CMS .</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,100728482950021319,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> The information module for main page 1.</r><r> You need to change the contents of this module to place here  your information. </r><r> For this you should enter on site under  your the password  </r><r> So you can observe buttons:  remove, edit, update (To add the new module). </r><r> In during  change of the informational unit you can put its other section .</r><r> You also can edit ,  add, delete this section name. </r><r> Also you can change parameters  criteria for search of  information  unit </r><r> For this purpose should change criteria on the form of change of the information with title (Installation of criteria for information search on a site ). </r>',2,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021322,'Information module','
<r>OnePlus 12,12GB RAM+256GB,Dual-SIM,Unlocked Android Smartphone,Supports Fastest 50W Wireless Charging,with The Latest Mobile Processor,Advanced Hasselblad Camera,5400 mAh Battery,2024,Silky Black</r>','',599.99,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714264,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:22:09',NULL,0,0,NULL,0,'
<r>Brand	OnePlus
</r><r>Operating System	Android 14
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	12 GB
</r><r>Screen Size	6.82 Inches
</r><r>Resolution	3168 x 1440
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	OnePlus 12
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>Comes with 6 months of Google One and 3 months of Youtube Premium with purchase of OnePlus 12. (New accounts only for each service to qualify)
</r><r>Pure Performance: The OnePlus 12 is powered by the latest Snapdragon 8 Gen 3, with up to 16GB of RAM. The improved processing power and graphics performance is supported by the latest Dual Cryo-velocity VC cooling chamber, which improves thermal efficiency and heat dissipation.
</r><r>Brilliant Display: The OnePlus 12 has a stunning 2k 120Hz Super Fluid Display, with advanced LTPO for a brighter, smoother, and more vibrant viewing experience. With 4500 nits peak brightness, enjoying your content is effortless anywhere.
</r><r>Powered by Trinity Engine: The OnePlus 12`s performance is optimized by the Trinity Engine, which accelerates various softwares to maximize the performance of your device. These include RAM-Vita, CPU-Vita, ROM-Vita, HyperTouch, HyperBoost, and HyperRendering (visit the official product page for more information).
</r><r>Powerful, Versatile Camera: Explore the new 4th Gen Hasselblad Camera System for Mobile, packed with computational photography software and improved sensors. The OnePlus 12 boasts a 50MP primary camera, a 64MP 3x Periscope Lens, and a 48MP Ultra-Wide Camera, all packed together with industry-leading Hasselblad color science.
</r>',2,'I',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021323,'Lenovo V15 Gen 4 Business Laptop','
<r>Lenovo V15 Gen 4 Business Laptop, 15.6" FHD Screen, AMD Ryzen 5 5500U, 24GB RAM, 1TB SSD, Webcam, HDMI, Wi-Fi, Windows 11 Pro, Black</r>','',549.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714289,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:43:41',NULL,0,0,NULL,0,'
<r>
</r><r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r> High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r> Processor】AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r> Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r> Tech Specs】1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r> Operating System】Windows 11 Pro
</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021324,'Acer Aspire 3 A315-24P-R7VH Slim Laptop ','
<r>Acer Aspire 3 A315-24P-R7VH Slim Laptop | 15.6" Full HD IPS Display | AMD Ryzen 3 7320U Quad-Core Processor | AMD Radeon Graphics | 8GB LPDDR5 | 128GB NVMe SSD | Wi-Fi 6 | Windows 11 Home in S Mode</r>','',300.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714280,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:35:00',NULL,0,0,NULL,0,'
<r>Brand	acer
</r><r>Model Name	Laptop
</r><r>Screen Size	15.6 Inches
</r><r>Color	Silver
</r><r>Hard Disk Size	128 GB
</r><r>CPU Model	Ryzen 3
</r><r>Ram Memory Installed Size	8 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Backlit Keyboard
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>Purposeful Design: Travel with ease and look great doing it with the Aspire`s 3 thin, light design.
</r><r>Ready-to-Go Performance: The Aspire 3 is ready-to-go with the latest AMD Ryzen 3 7320U Processor with Radeon Graphics—ideal for the entire family, with performance and productivity at the core.
</r><r>Visibly Stunning: Experience sharp details and crisp colors on the 15.6" Full HD IPS display with 16:9 aspect ratio and narrow bezels.
</r><r>Internal Specifications: 8GB LPDDR5 Onboard Memory; 128GB NVMe solid-state drive storage to store your files and media
</r><r>The HD front-facing camera uses Acer’s TNR (Temporal Noise Reduction) technology for high-quality imagery in low-light conditions. Acer PurifiedVoice technology with AI Noise Reduction filters out any extra sound for clear communication over online meetings.
</r><r>Wireless Wi-Fi 6 Convenience: Maintain a strong, consistent wireless signal with Wi-Fi 6 (aka 802.11ax) and 2x2 MU-MIMO technology.
</r><r>Improved Thermals: With a 78% increase in fan surface area, enjoy an improved thermal system and an additional 17% thermal capacity. Allowing for longer, more efficient work sessions while not plugged in.
</r>',2,'A',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (100728482950021325,'SAMSUNG Galaxy S24 Ultra Cell Phon','
<r>SAMSUNG Galaxy S24 Ultra Cell Phone, 256GB AI Smartphone, Unlocked Android, 50MP Zoom Camera, Long Battery Life, S Pen, US Version, 2024, Titanium Yellow</r>','',0.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714257,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:18:29',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Operating System	Android 14, One UI 6.1
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	256 GB
</r><r>Screen Size	6.8 Inches
</r><r>Resolution	3120 x 1440 pixels
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	Galaxy S24 Ultra
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>CIRCLE and SEARCH¹ IN A SNAP: What’s your favorite influencer wearing? Where’d they go on vacation? What’s that word mean? Don’t try to describe it — use Circle to Search1 with Google to get the answer; With S24 Series, circle it on your screen and learn more
</r><r>REAL EASY, REAL-TIME TRANSLATIONS: Speak foreign languages on the spot with Live Translate²; Unlock the power of convenient communication with near real-time voice translations, right through your Samsung Phone app
</r><r>NOTE SMARTER, NOT HARDER: Focus on capturing your notes and spend less time perfecting them; Note Assist³ will summarize, format, and even translate them for you; All of your notes are organized neatly so that you can find what you need
</r><r>BRING DETAILS OUT OF THE DARKNESS: Brighten up your night with Nightography on S24 Ultra; Want a closer look? Zoom in from a distance, even in low light
</r><r>MORE WOW, LESS WORK: Turn every photo into a post-worthy masterpiece; Move or remove objects; Fill in empty space; Simply snap a pic and take it from great to jaw-dropping with Generative Edit⁴
</r><r>CAPTURE. SHARE. IMPRESS: Create crystal-clear content worth sharing; From bustling cityscape to a serene landscape, capture a masterpiece of detail with 200MP camera and let S24 Ultra adjust each hue and shade to reflect the world as you see it
</r><r>OUR MOST POWERFUL GALAXY SMARTPHONE YET: Jump seamlessly between apps without the wait and see content in high quality with our fastest processor yet, Snapdragon 8 Gen 3 for Galaxy⁵
</r><r>PUT YOUR BEST TEXT FORWARD: Say the right thing at the right time in no time with Chat Assist⁶; Get real-time tone suggestions to make your writing sound more professional or conversational; Plus, make typos a thing of the past
</r><r>GAME and STREAM, EDGE TO EDGE: Take your streaming and gaming to the edge with S24 Ultra; Whether you’re watching, winning or even writing, the brighter, flatter screen gives you more room to do what you love
</r><r>WRITE WHEN YOU NEED IT: With S Pen, you can easily make last-minute edits to work projects, jot down your latest idea or sign important docs on your S24 Ultra; Take doing to whole new heights with the built-in S Pen</r>',2,'I',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021326,'Information module','
<r>HP Newest 14" Ultral Light Laptop for Students and Business, Intel Quad-Core N4120, 8GB RAM, 192GB Storage(64GB eMMC+128GB Micro SD), 1 Year Office 365, Webcam, HDMI, WiFi, USB-AandC, Win 11 S</r>','',212.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714277,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:31:37',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	HP Laptop
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N
</r><r>
</r><r>
</r><r>About this item
</r><r> 14" HD Display】14.0-inch diagonal, HD (1366 x 768), micro-edge, BrightView. With virtually no bezel encircling the display, an ultra-wide viewing experience provides for seamless multi-monitor set-ups
</r><r> Processor and Graphics】Intel Celeron N4120, 4 Cores and 4 Threads, 1.10 GHz Base Frequency, Up to 2.60 GHz Burst Frequency, 4 MB Cahce, Intel UHD Graphics 600, Handle multitasking reliably with the perfect combination of performance, power consumption, and value
</r><r> RAM and Storage】8GB high-bandwidth DDR4 Memory (2400 MHz), Adequate high-bandwidth RAM to smoothly run multiple applications and browser tabs all at once. 64GB high-speed eMMC Storage for your office and webinar needs
</r><r> Ports】1 x USB 3.1 Type-C ports, 2 x USB 3.1 Type-A ports, 1 x HDMI, 1 x Headphone/Microphone Combo Jack, and there`s a microSD slot
</r><r> Windows 11 Home in S mode】You may switch to regular windows 11: Press "Start button" bottom left of the screen; Select "Settings" icon above "power" icon;Select "Activation", then Go to Store; Select Get option under "Switch out of S mode"; Hit Install. (If you also see an "Upgrade your edition of Windows" section, be careful not to click the "Go to the Store" link that appears there.)</r>',2,'I',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021327,'HP 14 Laptop, Intel Celeron N4020','
<r>HP 14 Laptop, Intel Celeron N4020, 4 GB RAM, 64 GB Storage, 14-inch Micro-edge HD Display, Windows 11 Home, Thin and Portable, 4K Graphics, One Year of Microsoft 365 (14-dq0040nr, Snowflake White)</r>','',176.0,1,NULL,-1,3,NULL,1,NULL,NULL,NULL,-1,101055362458714265,NULL,NULL,2,NULL,NULL,-2,0,NULL,NULL,NULL,'2024-10-08 19:25:45',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)
</r>',2,'H',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (100728482950021328,'About us','<r> About us.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,NULL,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> Renowned for our flexible solutions and ability to automate critical business functions for the distribution, manufacturing, retail, and service sectors, CMS Manhattan’s software solutions are backed by a wealth of experience and a reputation for excellence that countless companies rely on. With specific E-commerce solutions for hard goods, food, and pharmaceutical companies, we have helped some of the most recognized companies address key industry requirements and deliver bottom-line results.</r><r>Company History</r>
<r>Established in 2010 and located in Plainview, New York, we began as a regional E-Commerce provider in the New York area. We have expanded over the years by marketing our enhanced products and services worldwide through our direct sales force and dealer re-seller channel.</r>
<r>We continue to increase our staff to help manage a global client base. A team of tenured, industry-leading engineers, research and development experts, and technology specialists work side by side with their customers to help them thrive and succeed in a demanding and technologically advanced environment. At CMS Manhattan, we believe this dedication to customer commitment has been the underlying factor for our success through the years and we continue to work in conjunction with our customers to ensure their success remains a top priority.</r>',2,NULL,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021329,'Delivery','<r> Delivery.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,NULL,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> CMS Manhattan</r><r> Check Seller delivery policy and rules. Please use USPS and UPS or FeDex with traking number to be safe and send us email if you cannot slve issue with shipping company to email: delievry@cmsmanhattan.com </r><r></r>',2,NULL,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021330,'Return policy','<r> How can I return item.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,NULL,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> CMS Manhattan </r><r> Returns and refunds policy base on seller terms . Please reread it to be safe .So sens us email if you feel fraud : buypolicy@cmsmanhattan.com  </r>',2,NULL,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021331,'Contacts us','<r> Contacts us.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,NULL,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> CMS Manhattan </r>  <r> Phone +1(516)777-09-45  </r><r> Fax:  +1(516)777-09-45 ext 1 </r><r> email: support@cmsmahattan.com  </r>',2,NULL,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (100728482950021332,'Careers','<r> Careers.</r>',NULL,0.0,1,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,-2,NULL,NULL,NULL,NULL,'2024-02-26 04:25:13',NULL,0,0,NULL,NULL,'<r> Careers with CMS Manhattan </r><r> There are many types of careers, and they can be grouped into different categories. Here are some examples of career categories and the types of careers that fall under them123:</r>
<r>Agriculture, Food, and Natural Resources: careers in this cluster are centered around one important topic: life.</r>
<r>Architecture and Construction</r>
<r>Arts, Audio/Video Technology, and Communication</r>
<r>Business and Finance</r>
<r>Education and Training</r>
<r>Government and Public Administration</r>',2,NULL,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-02-26 04:25:13',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0.0,0.0,0.0,NULL,NULL),
	 (101055362458714117,'SAMSUNG Monitor 32 Inches','
<r>zoom it ...</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714149,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 17:31:41',NULL,0,0,NULL,0,'
<r>Brand SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'I',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714118,'SAMSUNG 32-Inch ','
<r>SAMSUNG 32-Inch Odyssey OLED G8 (G80SD) Series 4K UHD Smart Gaming Monitor, 240Hz 0.03ms, G-Sync Compatible, Glare-Free Display, Gaming Hub, Sleek Metal Design, 3 Year Warranty, LS32DG802SNXZA, 2024</r>','',0.0,1,NULL,-1,3,NULL,0,100728482950021285,NULL,NULL,-1,101055362458714116,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 16:30:29',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 16:30:29',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714120,'SAMSUNG 32-Inch ','
<r>zoom it ...</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714145,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 17:31:21',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714122,'SAMSUNG 32-Inch ','
<r>zoom it ...</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714148,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 17:31:33',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714124,'SAMSUNG 32-Inch ','
<r>zoom it ...</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714144,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 17:31:15',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714126,'SAMSUNG 32-Inch ','
<r>zoom it ...</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714147,NULL,NULL,2,NULL,-1,-2,0,100728482950021319,NULL,NULL,'2024-10-08 17:31:27',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	32 Inches
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>EXPERIENCE A BRIGHTER WORLD: Content springs to life in 4K OLED; Brilliant imagery shines with 250 nits (typ) of brightness and a wider spectrum of colors, shades and contrasts; The NQ8 AI Gen3 Processor also upscales lower resolutions to nearly 4K
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 240Hz refresh rate in UHD resolution¹
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method²
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and automatically control brightness to reduce heat accordingly³
</r><r>LOGO and TASKBAR DETECTION: The brightness on static images, like logos and taskbars, is automatically reduced to prevent burn-in⁴
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity, and comes back to regular brightness with any input²
</r><r>IMPROVED FOCUS, NO DISTRACTIONS: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions⁵
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: G-Sync compatibility keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears⁶
</r><r>DIVE INTO SHADOWS w/ PURER BLACK LEVELS: Uncover every secret with infinite expression; See true black and dark colors on screen without pixel light bleed for supreme color and depth expression in every game
</r><r>FIND MORE SECRETS HIDING IN THE DARKNESS: Traverse into the shadows fearlessly; The Dynamic Black Equalizer analyzes the brightness of game scenes and adjusts sharpness, saturation and black details automatically⁷</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714130,'ASUS ROG Swift 27 inch ','
<r>zoom it ...</r>','',754.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714152,NULL,NULL,2,NULL,-1,-2,0,100728482950021318,NULL,NULL,'2024-10-08 17:30:45',NULL,0,0,NULL,0,'
<r>Brand	ASUS
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>
</r><r>26.5-inch QHD (2560 x 1440) OLED gaming monitor with 240 Hz refresh rate for immersive gaming
</r><r>Highly efficient custom heatsink, plus intelligent voltage optimization for better heat management to reduce the risk of burn-in
</r><r>Anti-glare micro-texture coating reduces reflections for accurate colors and better viewing experiences
</r><r>
</r><r>G-Sync compatible technology delivers seamless, tear-free gaming; and optional uniform brightness setting ensures consistent luminance levels
</r><r>DisplayWidget Center enables easy OLED and monitor settings adjustments with a mouse
</r><r>What’s in the box: USB 3.0 cable, DisplayPort cable, HDMI cable, Power cord and adapter, ROG pouch, ROG sticker, VESA mount kit, Color pre-calibration report, Quick start guide, Warranty Card
</r><r>3-month Adobe Creative Cloud Subscription: Receive complimentary access with the purchase of this product</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714156,'SAMSUNG 27-Inch Odyssey G6 ','
<r>zoom it</r>','',649.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714155,NULL,NULL,2,NULL,-1,-2,0,100728482950021317,NULL,NULL,'2024-10-08 17:48:39',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714158,'SAMSUNG 27-Inch Odyssey G6 ','
<r>zoom it</r>','',649.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714157,NULL,NULL,2,NULL,-1,-2,0,100728482950021317,NULL,NULL,'2024-10-08 17:50:04',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:50:04',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714160,'SAMSUNG 27-Inch Odyssey G6 ','
<r>zoom it</r>','',649.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714157,NULL,NULL,2,NULL,-1,-2,0,100728482950021317,NULL,NULL,'2024-10-08 17:50:21',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:50:21',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714162,'SAMSUNG 27-Inch Odyssey G6 ','
<r>zoom it</r>','',649.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714157,NULL,NULL,2,NULL,-1,-2,0,100728482950021317,NULL,NULL,'2024-10-08 17:50:38',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:50:38',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714164,'SAMSUNG 27-Inch Odyssey G6 ','
<r>zoom it</r>','',649.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714163,NULL,NULL,2,NULL,-1,-2,0,100728482950021317,NULL,NULL,'2024-10-08 17:51:02',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>STUNNING VISUALS and BRIGHTER HIGHLIGHTS: Samsung OLED Monitor delivers superior brightness and dynamic contrast, with an expansive spectrum of colors and nearly limitless shades for consistently brilliant imagery
</r><r>OUTMANEUVER OPPONENTS w/ SUPREME SPEED: Dodge, counter and engage faster with OLED technology, offering a near-instant 0.03ms response time (GtG); Stay ahead with HDMI 2.1 and DisplayPort connections and a 360Hz refresh rate in QHD resolution¹
</r><r>IMMERSE IN SMOOTH ACTION WITHOUT DISRUPTION: Support of Variable refresh rate (VRR) with AMD FreeSync Premium Pro keeps the GPU and panel synced up to eliminate choppiness, screen lag, and image tears
</r><r>DYNAMIC COOLING SYSTEM: For the 1st time ever, a Pulsating Heat Pipe was introduced into the monitor to prevent burn-in; The Dynamic Cooling System evaporates and condenses a coolant to diffuse heat 5x better than the older graphite sheet method
</r><r>THERMAL MODULATION SYSTEM: Algorithms predict surface temperature and then control the brightness accordingly, to automatically reduce heat²
</r><r>LOGO and TASKBAR DETECTION: Brightness is automatically reduced on static images such as logos and taskbars, to prevent burn-in³
</r><r>SCREEN SAVER: The screen dims itself after 10 minutes of inactivity and comes back to regular brightness with any input
</r><r>FOCUS WITHOUT DISTRACTION: Glare Free technology significantly reduces glare from external light sources, so the OLED screen`s perfect black and color experience are presented without distractions
</r><r>DIVE INTO SHADOWS W/ PURER BLACK LEVELS: See the darkest colors and the truest blacks on the screen without pixel light bleed, for supreme color and depth expression in every game
</r><r>STUNNING IN ANY SPACE: Upgraded modern design, just 3.9mm at its thinnest point, transforms any room into a high-tech paradise; CoreLighting+ matches your mood with ambient lighting for any vibe, so you can create a distinctive space of your own⁴</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:51:02',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714168,'Dell G2725D Gaming Monitor','
<r>zoom it</r>','',199.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714167,NULL,NULL,2,NULL,-1,-2,0,100728482950021316,NULL,NULL,'2024-10-08 17:56:33',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:56:33',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714170,'Dell G2725D Gaming Monitor','
<r>zoom it</r>','',199.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714169,NULL,NULL,2,NULL,-1,-2,0,100728482950021316,NULL,NULL,'2024-10-08 17:56:52',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:56:52',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714172,'Dell G2725D Gaming Monitor','
<r>zoom it</r>','',199.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714171,NULL,NULL,2,NULL,-1,-2,0,100728482950021316,NULL,NULL,'2024-10-08 17:57:07',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:57:07',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714176,'Dell G2725D Gaming Monitor','
<r>zoom it</r>','',199.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714175,NULL,NULL,2,NULL,-1,-2,0,100728482950021316,NULL,NULL,'2024-10-08 17:57:34',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:57:34',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714178,'Dell G2725D Gaming Monitor','
<r>zoom it</r>','',199.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714177,NULL,NULL,2,NULL,-1,-2,0,100728482950021316,NULL,NULL,'2024-10-08 17:58:14',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	27 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>High-Detail Immersion: Elevate your gaming experience with superior image quality and detail in QHD (2560x1440).
</r><r>Great Color: Experience virtual worlds in rich color with 99% sRGB color coverage.
</r><r>Fluid Motion: Experience ultra-smooth gameplay with minimal input lag for your fast-paced gaming sessions with a 180Hz refresh rate.
</r><r>AMD FreeSync Technology: AMD FreeSync provides a smoother gaming experience by eliminating screen tearing and stuttering, ensuring that gameplay is fluid and visually stunning.
</r><r>Ready for Action: Say goodbye to excessive motion blur and enjoy crisp, clear visuals during intense action sequences with a 1ms response time.
</r><r>Game Longer: Maximize eye comfort with TUV-certified software low blue light solution that reduces harmful blue light emission.
</r><r>Versatile Connectivity: Enjoy connectivity options with PC or game consoles with 1x DisplayPort, 2x HDMI, and 1x 3.5mm audio jack.</r>',2,'D',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 17:58:14',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714187,'Dell S3422DWG Curved Gaming Monitor','
<r>zoom it</r>','',299.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714186,NULL,NULL,2,NULL,-1,-2,0,100728482950021315,NULL,NULL,'2024-10-08 18:06:02',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:06:02',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714189,'Dell S3422DWG Curved Gaming Monitor','
<r>zoom it</r>','',299.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714192,NULL,NULL,2,NULL,-1,-2,0,100728482950021315,NULL,NULL,'2024-10-08 18:07:20',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714191,'Dell S3422DWG Curved Gaming Monitor','
<r>zoom it</r>','',299.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714190,NULL,NULL,2,NULL,-1,-2,0,100728482950021315,NULL,NULL,'2024-10-08 18:06:38',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:06:38',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714194,'Dell S3422DWG Curved Gaming Monitor','
<r>zoom it</r>','',299.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714193,NULL,NULL,2,NULL,-1,-2,0,100728482950021315,NULL,NULL,'2024-10-08 18:07:37',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:07:37',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714195,'Dell S3422DWG Curved Gaming Monitor','
<r>zoom it</r>','',299.99,1,NULL,-1,3,NULL,0,100728482950021285,NULL,NULL,-1,101055362458714186,NULL,NULL,2,NULL,-1,-2,0,100728482950021315,NULL,NULL,'2024-10-08 18:08:34',NULL,0,0,NULL,0,'
<r>Brand	Dell
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>A NEW VIEW: WQHD resolution (3440x1440) offers 34% more screen pixels than QHD giving you sharp, crystal-clear details and a panoramic view.
</r><r>SEE EVERY DETAIL: 34” 1800R curved screen with 21:9 aspect ratio and 3-sided ultra-thin bezels that envelop you with minimal distractions.
</r><r>A SMOOTHER EXPERIENCE: A 144Hz refresh rate allows fast-moving visuals to be seen with incredible clarity for faster reaction times.
</r><r>REMARKABLE CLARITY: AMD FreeSync premium technology allows gamers to stay engaged during battle with swift, responsive, stutter-free gameplay
</r><r>DELL SERVICES: 3 Year Advanced Exchange Service and Premium Panel Exchange. Find one “bright pixel” and Dell will provide you a free monitor exchange during the limited hardware warranty period</r>',2,'D',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:08:34',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714199,'Information module','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714198,NULL,NULL,2,NULL,-1,-2,0,100728482950021314,NULL,NULL,'2024-10-08 18:15:40',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'I',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:15:40',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714200,'SANSUI 34-Inch Curved Gaming Monitor','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714201,NULL,NULL,2,NULL,-1,-2,0,100728482950021314,NULL,NULL,'2024-10-08 18:16:42',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714203,'SANSUI 34-Inch Curved Gaming Monitor','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714202,NULL,NULL,2,NULL,-1,-2,0,100728482950021314,NULL,NULL,'2024-10-08 18:16:59',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:16:59',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714205,'SANSUI 34-Inch Curved Gaming Monitor','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714204,NULL,NULL,2,NULL,-1,-2,0,100728482950021314,NULL,NULL,'2024-10-08 18:17:19',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:17:19',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714207,'SANSUI 34-Inch Curved Gaming Monitor','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714206,NULL,NULL,2,NULL,-1,-2,0,100728482950021314,NULL,NULL,'2024-10-08 18:17:36',NULL,0,0,NULL,0,'
<r>Brand	SANSUI
</r><r>Screen Size	34
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>34 inch Curved 1500R UWQHD(3440 x 1440) @ 165Hz Fast VA Ultrawide Gaming Monitor.
</r><r>Performance: 165Hz Refresh Rate | MPRT 1ms Response Time丨 FreeSync | Blue light reduction | Flicker-free
</r><r>Screen Colors: sRGB 125% | 300nits | 8 bits丨DCI-P3 95%丨HDR
</r><r>Ergonomic Stand: Tilt / Kensington Lock: -5°~15°(+/-2°) / Yes丨VESA Compatible (75 x 75mm) | 178° Wide Viewing Angle | PIP/PBP
</r><r>Input andOutput: HDMI 2.1(TMDS) X 2 (Up to 100Hz)|DP 1.4 X 2 (Up to 165Hz) | Earphone |No speakers
</r><r>Main Accessories: DP cable(1.5m), Adapter*1, Power Cord*1(1m), User manual.
</r><r>Warranty: SANSUI 34-inch Curved gaming computer monitor support money-back and free replacement warranty from order date within 30 days and lifetime technical support.</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:17:36',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714212,'LG 45GS95QE Ultragear','
<r>zoom it</r>','',1042.14,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714211,NULL,NULL,2,NULL,-1,-2,0,100728482950021313,NULL,NULL,'2024-10-08 18:36:23',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:36:23',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714214,'LG 45GS95QE Ultragear','
<r>zoom it</r>','',1042.14,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714213,NULL,NULL,2,NULL,-1,-2,0,100728482950021313,NULL,NULL,'2024-10-08 18:36:39',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:36:39',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714216,'LG 45GS95QE Ultragear','
<r>zoom it</r>','',1042.14,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714215,NULL,NULL,2,NULL,-1,-2,0,100728482950021313,NULL,NULL,'2024-10-08 18:36:55',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:36:55',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714218,'LG 45GS95QE Ultragear','
<r>zoom it</r>','',1042.14,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714217,NULL,NULL,2,NULL,-1,-2,0,100728482950021313,NULL,NULL,'2024-10-08 18:37:15',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:37:15',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714220,'LG 45GS95QE Ultragear','
<r>zoom it</r>','',1042.14,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714219,NULL,NULL,2,NULL,-1,-2,0,100728482950021313,NULL,NULL,'2024-10-08 18:37:34',NULL,0,0,NULL,0,'
<r>Brand	LG
</r><r>Screen Size	44.5 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>45” Display, 21:9 Aspect Ratio - With a huge 45” display, 21:9 aspect ratio and a dramatic curved OLED screen, you’ll feel like you’re actually in the game. Experience next-level gaming performance with LG UltraGear OLED.
</r><r>12% Larger Screen - Size matters in OLED gaming. A 45" 21:9 screen is 12% larger than a 49" 32:9 display.
</r><r>MLA+ 275 nits - Micro Lens Array produces bright OLED gaming at 275 nits (typical) brightness.
</r><r>800R Curved Screen - This UltraGear OLED monitor`s dramatic and steep 800R curve draws you in with game play by extending the periphery to feel like you`re surround by the action.
</r><r>240Hz Refresh Rate/0.03 Response Time - Gear up for smooth gameplay with an ultra-fast 240Hz OLED display. The faster speed lets you respond quickly to opponents and stay a step ahead of the competition. Plus, enjoy smoother scenes with less lag or ghosting thanks to LG UltraGear OLED`s nearly instantaneous 0.03ms (GtG) response time.
</r><r>Display HDR True Black 400 and 1.5M:1 Contrast Ratio - DisplayHDR True Black 400 displays rich blacks and vivid color. The benefit of the 1.5M:1 Contrast Ratio for gaming and streaming content, is impeccable image detail in dark scenes for a stunning visual experience.
</r><r>NVIDIA G-SYNC Compatible - Officially validated by NVIDIA as G-SYNC Compatible. That translates to faster, smoother gaming that`s been tested to reduce screen tearing and stutter. Never miss a frame of the action as you clinch your victories.
</r><r>AMD FreeSync Premium Pro - AMD FreeSync Premium Pro equips serious gamers with a fluid, virtually tear-free gaming experience. With at least 120Hz refresh rate at minimum FHD resolution and low latency gameplay, you`ll never miss a frame of the action as you play at peak performance.
</r><r>HDMI 2.1 and DisplayPort 1.4 - Enjoy smoother, vivid colors and high-resolution graphics thanks to HDMI 2.1 and DisplayPort 1.4. Both connections are supported on this display and let you experience up to 240Hz refresh rate.
</r><r>Anti-Glare with Low Reflection Display - Play with less distractions thanks to an anti-glare screen that minimizes light reflections and lets you concentrate on winning.</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:37:34',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714224,'LG 32GQ750-B','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714223,NULL,NULL,2,NULL,-1,-2,0,100728482950021312,NULL,NULL,'2024-10-08 18:48:51',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714226,'LG 32GQ750-B','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714225,NULL,NULL,2,NULL,-1,-2,0,100728482950021312,NULL,NULL,'2024-10-08 18:49:07',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:49:07',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714228,'LG 32GQ750-B','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714227,NULL,NULL,2,NULL,-1,-2,0,100728482950021312,NULL,NULL,'2024-10-08 18:49:46',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:49:46',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714230,'LG 32GQ750-B','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714229,NULL,NULL,2,NULL,-1,-2,0,100728482950021312,NULL,NULL,'2024-10-08 18:50:08',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:50:08',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714236,'ViewSonic VP3256-4K 32 Inch','
<r>zoo it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714237,NULL,NULL,2,NULL,-1,-2,0,100728482950021311,NULL,NULL,'2024-10-08 18:56:57',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714239,'ViewSonic VP3256-4K 32 Inch','
<r>zoo it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714238,NULL,NULL,2,NULL,-1,-2,0,100728482950021311,NULL,NULL,'2024-10-08 18:57:15',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:57:15',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714241,'ViewSonic VP3256-4K 32 Inch','
<r>zoo it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714240,NULL,NULL,2,NULL,-1,-2,0,100728482950021311,NULL,NULL,'2024-10-08 18:57:33',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 18:57:33',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714243,'ViewSonic VP3256-4K 32 Inch','
<r>zoo it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714244,NULL,NULL,2,NULL,-1,-2,0,100728482950021311,NULL,NULL,'2024-10-08 18:58:21',NULL,0,0,NULL,0,'
<r>Brand	ViewSonic
</r><r>Screen Size	32
</r><r>Resolution	4K UHD 2160p
</r><r>Aspect Ratio	16:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>PREMIUM MONITOR: 32 Inch 4K UHD (3840x2160p) IPS monitor with thin bezels, HDR10 and 60Hz refresh rate
</r><r>LESS CABLE CLUTTER: In addition to fast data, audio and video transfer, USB-C also provides quick 60W charging over a single cable
</r><r>ERGONOMIC STAND: Advanced ergonomics (tilt, swivel, pivot and height) for all-day comfort
</r><r>ENHANCED VIEWING COMFORT: Flicker-Free technology and Blue Light Filter for reduced eye fatigue
</r><r>
</r><r>EXCEPTIONAL COLOR ACCURACY: 100% sRGB, EBU, SMPTE-C, Rec. 709, and DICOM-SIM color spaces and Delta E less 2 accuracy
</r><r>PANTONE VALIDATED: This monitor has been certified as capable of reproducing Pantone Matching System colors
</r><r>NO IMAGE TEARING: Adaptive Sync technology enables smooth frame rates to aid you in battle
</r><r>FLEXIBLE CONNECTIVITY: The VP3256-4K supports laptops, PCs, Macs and more with USB-C, HDMI, USB and DisplayPort inputs</r>',2,'V',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714248,'34 Inch Ultrawide','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714247,NULL,NULL,2,NULL,-1,-2,0,100728482950021310,NULL,NULL,'2024-10-08 19:03:24',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'3',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714250,'34 Inch Ultrawide','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714249,NULL,NULL,2,NULL,-1,-2,0,100728482950021310,NULL,NULL,'2024-10-08 19:05:41',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'3',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:05:41',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714252,'34 Inch Ultrawide','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714251,NULL,NULL,2,NULL,-1,-2,0,100728482950021310,NULL,NULL,'2024-10-08 19:06:04',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'3',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:06:04',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714254,'34 Inch Ultrawide','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714253,NULL,NULL,2,NULL,-1,-2,0,100728482950021310,NULL,NULL,'2024-10-08 19:06:24',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'3',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:06:24',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714256,'34 Inch Ultrawide','
<r>zoom it</r>','',219.99,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714255,NULL,NULL,2,NULL,-1,-2,0,100728482950021310,NULL,NULL,'2024-10-08 19:06:39',NULL,0,0,NULL,0,'
<r>Brand	Gawfolk
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r> 34-inch 21:9 Ultra-Widescreen Curved Monitor】- The 1500R curved gaming monitor features a new generation of 350 nits of brightness, 8-bit, 16.7 million colors, with a dynamic contrast ratio of 4000:1 and 99% sRGB, providing a wider color gamut than most traditional monitors, resulting in deeper colors and crisp features, giving you sharper, brighter, more vivid colors and more image details.
</r><r> Smooth Gaming】- The 144Hz high refresh rate shows motion more clearly and minimizes screen stuttering, enjoying a smooth and responsive gaming experience. *Default refresh rate is 60Hz (HDMI cable supports up to 100Hz, DP cable supports up to 144Hz). If your computer supports 144 Hz, connect the monitor via the supplied DP cable and go to the display settings to change the refresh rate.
</r><r> Adaptive FreeSync】Prevent tearing and image errors by synchronizing the monitor`s refresh rate with the graphics card`s frame rate.
</r><r> Low blue light, no flickering screen】- Using DC global dimming technology, it can achieve high brush without flicker, and after activating the low blue light mode, it can effectively filter short-wave blue light, which is still comfortable for eyes after long-term use.
</r><r>After-sales service】We provide 12 months after-sales service, if you encounter any problems when using the monitor, please feel to communicate with us.</r>',2,'3',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:06:39',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714259,'SAMSUNG Galaxy S24 Ultra Cell Phone','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714258,NULL,NULL,2,NULL,-1,-2,0,100728482950021325,NULL,NULL,'2024-10-08 19:19:12',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Operating System	Android 14, One UI 6.1
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	256 GB
</r><r>Screen Size	6.8 Inches
</r><r>Resolution	3120 x 1440 pixels
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	Galaxy S24 Ultra
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>CIRCLE and SEARCH¹ IN A SNAP: What’s your favorite influencer wearing? Where’d they go on vacation? What’s that word mean? Don’t try to describe it — use Circle to Search1 with Google to get the answer; With S24 Series, circle it on your screen and learn more
</r><r>REAL EASY, REAL-TIME TRANSLATIONS: Speak foreign languages on the spot with Live Translate²; Unlock the power of convenient communication with near real-time voice translations, right through your Samsung Phone app
</r><r>NOTE SMARTER, NOT HARDER: Focus on capturing your notes and spend less time perfecting them; Note Assist³ will summarize, format, and even translate them for you; All of your notes are organized neatly so that you can find what you need
</r><r>BRING DETAILS OUT OF THE DARKNESS: Brighten up your night with Nightography on S24 Ultra; Want a closer look? Zoom in from a distance, even in low light
</r><r>MORE WOW, LESS WORK: Turn every photo into a post-worthy masterpiece; Move or remove objects; Fill in empty space; Simply snap a pic and take it from great to jaw-dropping with Generative Edit⁴
</r><r>CAPTURE. SHARE. IMPRESS: Create crystal-clear content worth sharing; From bustling cityscape to a serene landscape, capture a masterpiece of detail with 200MP camera and let S24 Ultra adjust each hue and shade to reflect the world as you see it
</r><r>OUR MOST POWERFUL GALAXY SMARTPHONE YET: Jump seamlessly between apps without the wait and see content in high quality with our fastest processor yet, Snapdragon 8 Gen 3 for Galaxy⁵
</r><r>PUT YOUR BEST TEXT FORWARD: Say the right thing at the right time in no time with Chat Assist⁶; Get real-time tone suggestions to make your writing sound more professional or conversational; Plus, make typos a thing of the past
</r><r>GAME and STREAM, EDGE TO EDGE: Take your streaming and gaming to the edge with S24 Ultra; Whether you’re watching, winning or even writing, the brighter, flatter screen gives you more room to do what you love
</r><r>WRITE WHEN YOU NEED IT: With S Pen, you can easily make last-minute edits to work projects, jot down your latest idea or sign important docs on your S24 Ultra; Take doing to whole new heights with the built-in S Pen</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:19:12',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714261,'SAMSUNG Galaxy S24 Ultra Cell Phone','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714260,NULL,NULL,2,NULL,-1,-2,0,100728482950021325,NULL,NULL,'2024-10-08 19:19:28',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Operating System	Android 14, One UI 6.1
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	256 GB
</r><r>Screen Size	6.8 Inches
</r><r>Resolution	3120 x 1440 pixels
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	Galaxy S24 Ultra
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>CIRCLE and SEARCH¹ IN A SNAP: What’s your favorite influencer wearing? Where’d they go on vacation? What’s that word mean? Don’t try to describe it — use Circle to Search1 with Google to get the answer; With S24 Series, circle it on your screen and learn more
</r><r>REAL EASY, REAL-TIME TRANSLATIONS: Speak foreign languages on the spot with Live Translate²; Unlock the power of convenient communication with near real-time voice translations, right through your Samsung Phone app
</r><r>NOTE SMARTER, NOT HARDER: Focus on capturing your notes and spend less time perfecting them; Note Assist³ will summarize, format, and even translate them for you; All of your notes are organized neatly so that you can find what you need
</r><r>BRING DETAILS OUT OF THE DARKNESS: Brighten up your night with Nightography on S24 Ultra; Want a closer look? Zoom in from a distance, even in low light
</r><r>MORE WOW, LESS WORK: Turn every photo into a post-worthy masterpiece; Move or remove objects; Fill in empty space; Simply snap a pic and take it from great to jaw-dropping with Generative Edit⁴
</r><r>CAPTURE. SHARE. IMPRESS: Create crystal-clear content worth sharing; From bustling cityscape to a serene landscape, capture a masterpiece of detail with 200MP camera and let S24 Ultra adjust each hue and shade to reflect the world as you see it
</r><r>OUR MOST POWERFUL GALAXY SMARTPHONE YET: Jump seamlessly between apps without the wait and see content in high quality with our fastest processor yet, Snapdragon 8 Gen 3 for Galaxy⁵
</r><r>PUT YOUR BEST TEXT FORWARD: Say the right thing at the right time in no time with Chat Assist⁶; Get real-time tone suggestions to make your writing sound more professional or conversational; Plus, make typos a thing of the past
</r><r>GAME and STREAM, EDGE TO EDGE: Take your streaming and gaming to the edge with S24 Ultra; Whether you’re watching, winning or even writing, the brighter, flatter screen gives you more room to do what you love
</r><r>WRITE WHEN YOU NEED IT: With S Pen, you can easily make last-minute edits to work projects, jot down your latest idea or sign important docs on your S24 Ultra; Take doing to whole new heights with the built-in S Pen</r>',2,'S',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:19:28',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714263,'SAMSUNG Galaxy S24 Ultra Cell Phone','
<r>zoom it</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714262,NULL,NULL,2,NULL,-1,-2,0,100728482950021325,NULL,NULL,'2024-10-08 19:20:10',NULL,0,0,NULL,0,'
<r>Brand	SAMSUNG
</r><r>Operating System	Android 14, One UI 6.1
</r><r>Ram Memory Installed Size	12 GB
</r><r>Memory Storage Capacity	256 GB
</r><r>Screen Size	6.8 Inches
</r><r>Resolution	3120 x 1440 pixels
</r><r>Refresh Rate	120 Hz
</r><r>Model Name	Galaxy S24 Ultra
</r><r>Wireless Carrier	Unlocked for All Carriers
</r><r>Cellular Technology	5G
</r><r>See more
</r><r>About this item
</r><r>CIRCLE and SEARCH¹ IN A SNAP: What’s your favorite influencer wearing? Where’d they go on vacation? What’s that word mean? Don’t try to describe it — use Circle to Search1 with Google to get the answer; With S24 Series, circle it on your screen and learn more
</r><r>REAL EASY, REAL-TIME TRANSLATIONS: Speak foreign languages on the spot with Live Translate²; Unlock the power of convenient communication with near real-time voice translations, right through your Samsung Phone app
</r><r>NOTE SMARTER, NOT HARDER: Focus on capturing your notes and spend less time perfecting them; Note Assist³ will summarize, format, and even translate them for you; All of your notes are organized neatly so that you can find what you need
</r><r>BRING DETAILS OUT OF THE DARKNESS: Brighten up your night with Nightography on S24 Ultra; Want a closer look? Zoom in from a distance, even in low light
</r><r>MORE WOW, LESS WORK: Turn every photo into a post-worthy masterpiece; Move or remove objects; Fill in empty space; Simply snap a pic and take it from great to jaw-dropping with Generative Edit⁴
</r><r>CAPTURE. SHARE. IMPRESS: Create crystal-clear content worth sharing; From bustling cityscape to a serene landscape, capture a masterpiece of detail with 200MP camera and let S24 Ultra adjust each hue and shade to reflect the world as you see it
</r><r>OUR MOST POWERFUL GALAXY SMARTPHONE YET: Jump seamlessly between apps without the wait and see content in high quality with our fastest processor yet, Snapdragon 8 Gen 3 for Galaxy⁵
</r><r>PUT YOUR BEST TEXT FORWARD: Say the right thing at the right time in no time with Chat Assist⁶; Get real-time tone suggestions to make your writing sound more professional or conversational; Plus, make typos a thing of the past
</r><r>GAME and STREAM, EDGE TO EDGE: Take your streaming and gaming to the edge with S24 Ultra; Whether you’re watching, winning or even writing, the brighter, flatter screen gives you more room to do what you love
</r><r>WRITE WHEN YOU NEED IT: With S Pen, you can easily make last-minute edits to work projects, jot down your latest idea or sign important docs on your S24 Ultra; Take doing to whole new heights with the built-in S Pen</r>',2,'S',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:20:10',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714267,'HP 14 Laptop, Intel Celeron N4020','
<r>zoom it</r>','',176.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714266,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:26:39',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)</r>',2,'H',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:26:39',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714269,'HP 14 Laptop, Intel Celeron N4020','
<r>zoom it</r>','',176.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714268,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:26:56',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)</r>',2,'H',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:26:56',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714271,'HP 14 Laptop, Intel Celeron N4020','
<r>zoom it</r>','',176.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714270,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:27:12',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)</r>',2,'H',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:27:12',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714273,'HP 14 Laptop, Intel Celeron N4020','
<r>zoom it</r>','',176.0,1,NULL,-1,3,NULL,0,100728482950021285,NULL,NULL,-1,101055362458714272,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:28:07',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)</r>',2,'H',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:28:07',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714275,'HP 14 Laptop, Intel Celeron N4020','
<r>zoom it</r>','',176.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714274,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:27:56',NULL,0,0,NULL,0,'
<r>Brand	HP
</r><r>Model Name	14-dq0040nr
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model	Celeron N4020
</r><r>Ram Memory Installed Size	4 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Micro-edge Bezel
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>READY FOR ANYWHERE – With its thin and light design, 6.5 mm micro-edge bezel display, and 79% screen-to-body ratio, you’ll take this PC anywhere while you see and do more of what you love (1)
</r><r>MORE SCREEN, MORE FUN – With virtually no bezel encircling the screen, you’ll enjoy every bit of detail on this 14-inch HD (1366 x 768) display (2)
</r><r>ALL-DAY PERFORMANCE – Tackle your busiest days with the dual-core, Intel Celeron N4020—the perfect processor for performance, power consumption, and value (3)
</r><r>4K READY – Smoothly stream 4K content and play your favorite next-gen games with Intel UHD Graphics 600 (4) (5)
</r><r>STORAGE AND MEMORY – An embedded multimedia card provides reliable flash-based, 64 GB of storage while 4 GB of RAM expands your bandwidth and boosts your performance (6)</r>',2,'H',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:27:56',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714276,'Information module','
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.</r>','',0.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714274,NULL,NULL,2,NULL,-1,-2,0,100728482950021327,NULL,NULL,'2024-10-08 19:28:43',NULL,0,0,NULL,0,'
<r>In the information module  you can  place  the text, to place  images on forms with short and detailed description  and attach files, to set a price, and also to include voting or a forum for each concrete information module.
</r><r> the Instruction on addition of the information module:
</r><r> 1. To come into MANAGEMENT of the SITE having pressed the button to UPDATE.
</r><r> 2. To press (To add the information, the goods, news in the central module with search in criteria)
</r><r> 3. We find subsection ( Form for an institution or information change on a site ).
</r><r> 4. In the field HEADING we will write heading.
</r><r> 5. In the field CHOSEN SECTION you can chose a section  where is place of  information module.If section is not exist then add new section in (Form for catalogue construction ) for detail see creation catalog.
</r><r> 6. In the field IN OTHER LANGUAGE FOR SEARCH of anything it is not necessary to enter HEADING. Is in working out.
</r><r> 7. In the field PICTURE 1 it is given the chance to add the image on page with the short description. The image can be added in two methods: UPLOAD PICTURE 1 FOR SHORT - upload from the computer of the user, PICTURE 1 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 8. If you mark the price in the field the PRICE it is displayed. If the price is not necessary to you, keep a zero.
</r><r> 9. In the field the BRIEF INFORMATION is written goods or service brief information.
</r><r> 10. In the field PICTURE 2 it is given the chance to add the image on page with detailed description. The image can be added in two methods: UPLOAD PICTURE 2 FOR DETAILED - upload from the computer of the user, PICTURE 2 FROM BASE - to choose from present base. The image uploading in any formats which are supported by your browser. The maximum size of the image 1,5 Mb.
</r><r> 11. In the field the DETAILED INFORMATION is written the detailed information of the goods or service.
</r><r> 12. In the field ATTACH the FILE you can to attach a file in two methods: UPLOAD the FILE - upload from the computer of the user to CHOOSE the FILE - to choose from present base. A file uploading in any format which supports your browser. The maximum size of a file. 1,5 Mb.
</r><r> 13. At an option choice to INCLUDE VOTING, your information module has a voting. By default this option is switched off.
</r><r> 14. At an option choice to INCLUDE DISCUSSION, your information module has a forum. By default this option is switched off.
</r><r> 15. In an option I APPROVE, if choose it is published, the information module will be displayed only in the catalogue. If will choose to SHOW ON MAIN PAGE this module will be displayed both in the catalogue and on main page.
</r><r> 16. Enter from a picture a code.
</r><r> 17. Press the button to SAVE.</r>',2,'I',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:28:43',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714279,'Brand HP Celeron N','
<r>zoom it</r>','',212.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714278,NULL,NULL,2,NULL,-1,-2,0,100728482950021326,NULL,NULL,'2024-10-08 19:32:14',NULL,0,0,NULL,0,'
<r>Brand HP Celeron N
</r><r>Model Name HP Laptop
</r><r>Screen Size	14 Inches
</r><r>Color	Snowflake White
</r><r>Hard Disk Size	64 GB
</r><r>CPU Model Celeron N
</r><r>
</r><r>
</r><r>About this item
</r><r> 14" HD Display】14.0-inch diagonal, HD (1366 x 768), micro-edge, BrightView. With virtually no bezel encircling the display, an ultra-wide viewing experience provides for seamless multi-monitor set-ups
</r><r> Processor and Graphics】Intel Celeron N4120, 4 Cores and 4 Threads, 1.10 GHz Base Frequency, Up to 2.60 GHz Burst Frequency, 4 MB Cahce, Intel UHD Graphics 600, Handle multitasking reliably with the perfect combination of performance, power consumption, and value
</r><r> RAM and Storage】8GB high-bandwidth DDR4 Memory (2400 MHz), Adequate high-bandwidth RAM to smoothly run multiple applications and browser tabs all at once. 64GB high-speed eMMC Storage for your office and webinar needs
</r><r> Ports】1 x USB 3.1 Type-C ports, 2 x USB 3.1 Type-A ports, 1 x HDMI, 1 x Headphone/Microphone Combo Jack, and there`s a microSD slot
</r><r> Windows 11 Home in S mode】You may switch to regular windows 11: Press "Start button" bottom left of the screen; Select "Settings" icon above "power" icon;Select "Activation", then Go to Store; Select Get option under "Switch out of S mode"; Hit Install. (If you also see an "Upgrade your edition of Windows" section, be careful not to click the "Go to the Store" link that appears there.)</r>',2,'I',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:32:14',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714281,'Acer Aspire 3 A315-24P-R7VH Slim Laptop ','
<r>zoom it</r>','',300.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714282,NULL,NULL,2,NULL,-1,-2,0,100728482950021324,NULL,NULL,'2024-10-08 19:40:56',NULL,0,0,NULL,0,'
<r>Brand	acer
</r><r>Model Name	Laptop
</r><r>Screen Size	15.6 Inches
</r><r>Color	Silver
</r><r>Hard Disk Size	128 GB
</r><r>CPU Model	Ryzen 3
</r><r>Ram Memory Installed Size	8 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Backlit Keyboard
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>Purposeful Design: Travel with ease and look great doing it with the Aspire`s 3 thin, light design.
</r><r>Ready-to-Go Performance: The Aspire 3 is ready-to-go with the latest AMD Ryzen 3 7320U Processor with Radeon Graphics—ideal for the entire family, with performance and productivity at the core.
</r><r>Visibly Stunning: Experience sharp details and crisp colors on the 15.6" Full HD IPS display with 16:9 aspect ratio and narrow bezels.
</r><r>Internal Specifications: 8GB LPDDR5 Onboard Memory; 128GB NVMe solid-state drive storage to store your files and media
</r><r>The HD front-facing camera uses Acer’s TNR (Temporal Noise Reduction) technology for high-quality imagery in low-light conditions. Acer PurifiedVoice technology with AI Noise Reduction filters out any extra sound for clear communication over online meetings.
</r><r>Wireless Wi-Fi 6 Convenience: Maintain a strong, consistent wireless signal with Wi-Fi 6 (aka 802.11ax) and 2x2 MU-MIMO technology.
</r><r>Improved Thermals: With a 78% increase in fan surface area, enjoy an improved thermal system and an additional 17% thermal capacity. Allowing for longer, more efficient work sessions while not plugged in.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714284,'Acer Aspire 3 A315-24P-R7VH Slim Laptop ','
<r>zoom it</r>','',300.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714283,NULL,NULL,2,NULL,-1,-2,0,100728482950021324,NULL,NULL,'2024-10-08 19:41:11',NULL,0,0,NULL,0,'
<r>Brand	acer
</r><r>Model Name	Laptop
</r><r>Screen Size	15.6 Inches
</r><r>Color	Silver
</r><r>Hard Disk Size	128 GB
</r><r>CPU Model	Ryzen 3
</r><r>Ram Memory Installed Size	8 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Backlit Keyboard
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>Purposeful Design: Travel with ease and look great doing it with the Aspire`s 3 thin, light design.
</r><r>Ready-to-Go Performance: The Aspire 3 is ready-to-go with the latest AMD Ryzen 3 7320U Processor with Radeon Graphics—ideal for the entire family, with performance and productivity at the core.
</r><r>Visibly Stunning: Experience sharp details and crisp colors on the 15.6" Full HD IPS display with 16:9 aspect ratio and narrow bezels.
</r><r>Internal Specifications: 8GB LPDDR5 Onboard Memory; 128GB NVMe solid-state drive storage to store your files and media
</r><r>The HD front-facing camera uses Acer’s TNR (Temporal Noise Reduction) technology for high-quality imagery in low-light conditions. Acer PurifiedVoice technology with AI Noise Reduction filters out any extra sound for clear communication over online meetings.
</r><r>Wireless Wi-Fi 6 Convenience: Maintain a strong, consistent wireless signal with Wi-Fi 6 (aka 802.11ax) and 2x2 MU-MIMO technology.
</r><r>Improved Thermals: With a 78% increase in fan surface area, enjoy an improved thermal system and an additional 17% thermal capacity. Allowing for longer, more efficient work sessions while not plugged in.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:41:11',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714286,'Acer Aspire 3 A315-24P-R7VH Slim Laptop ','
<r>zoom it</r>','',300.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714285,NULL,NULL,2,NULL,-1,-2,0,100728482950021324,NULL,NULL,'2024-10-08 19:41:27',NULL,0,0,NULL,0,'
<r>Brand	acer
</r><r>Model Name	Laptop
</r><r>Screen Size	15.6 Inches
</r><r>Color	Silver
</r><r>Hard Disk Size	128 GB
</r><r>CPU Model	Ryzen 3
</r><r>Ram Memory Installed Size	8 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Backlit Keyboard
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>Purposeful Design: Travel with ease and look great doing it with the Aspire`s 3 thin, light design.
</r><r>Ready-to-Go Performance: The Aspire 3 is ready-to-go with the latest AMD Ryzen 3 7320U Processor with Radeon Graphics—ideal for the entire family, with performance and productivity at the core.
</r><r>Visibly Stunning: Experience sharp details and crisp colors on the 15.6" Full HD IPS display with 16:9 aspect ratio and narrow bezels.
</r><r>Internal Specifications: 8GB LPDDR5 Onboard Memory; 128GB NVMe solid-state drive storage to store your files and media
</r><r>The HD front-facing camera uses Acer’s TNR (Temporal Noise Reduction) technology for high-quality imagery in low-light conditions. Acer PurifiedVoice technology with AI Noise Reduction filters out any extra sound for clear communication over online meetings.
</r><r>Wireless Wi-Fi 6 Convenience: Maintain a strong, consistent wireless signal with Wi-Fi 6 (aka 802.11ax) and 2x2 MU-MIMO technology.
</r><r>Improved Thermals: With a 78% increase in fan surface area, enjoy an improved thermal system and an additional 17% thermal capacity. Allowing for longer, more efficient work sessions while not plugged in.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:41:27',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714288,'Acer Aspire 3 A315-24P-R7VH Slim Laptop ','
<r>zoom it</r>','',300.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714287,NULL,NULL,2,NULL,-1,-2,0,100728482950021324,NULL,NULL,'2024-10-08 19:41:44',NULL,0,0,NULL,0,'
<r>Brand	acer
</r><r>Model Name	Laptop
</r><r>Screen Size	15.6 Inches
</r><r>Color	Silver
</r><r>Hard Disk Size	128 GB
</r><r>CPU Model	Ryzen 3
</r><r>Ram Memory Installed Size	8 GB
</r><r>Operating System	Windows 11 S
</r><r>Special Feature	Backlit Keyboard
</r><r>Graphics Card Description	Integrated
</r><r>See more
</r><r>About this item
</r><r>Purposeful Design: Travel with ease and look great doing it with the Aspire`s 3 thin, light design.
</r><r>Ready-to-Go Performance: The Aspire 3 is ready-to-go with the latest AMD Ryzen 3 7320U Processor with Radeon Graphics—ideal for the entire family, with performance and productivity at the core.
</r><r>Visibly Stunning: Experience sharp details and crisp colors on the 15.6" Full HD IPS display with 16:9 aspect ratio and narrow bezels.
</r><r>Internal Specifications: 8GB LPDDR5 Onboard Memory; 128GB NVMe solid-state drive storage to store your files and media
</r><r>The HD front-facing camera uses Acer’s TNR (Temporal Noise Reduction) technology for high-quality imagery in low-light conditions. Acer PurifiedVoice technology with AI Noise Reduction filters out any extra sound for clear communication over online meetings.
</r><r>Wireless Wi-Fi 6 Convenience: Maintain a strong, consistent wireless signal with Wi-Fi 6 (aka 802.11ax) and 2x2 MU-MIMO technology.
</r><r>Improved Thermals: With a 78% increase in fan surface area, enjoy an improved thermal system and an additional 17% thermal capacity. Allowing for longer, more efficient work sessions while not plugged in.</r>',2,'A',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:41:44',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714291,'Lenovo V15 Gen 4 Business Laptop','
<r>zoom it</r>','',549.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714294,NULL,NULL,2,NULL,-1,-2,0,100728482950021323,NULL,NULL,'2024-10-08 19:45:21',NULL,0,0,NULL,0,'
<r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r> High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r> Processor】AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r> Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r> Tech Specs】1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r> Operating System】Windows 11 Pro</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714293,'Lenovo V15 Gen 4 Business Laptop','
<r>zoom it</r>','',549.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714290,NULL,NULL,2,NULL,-1,-2,0,100728482950021323,NULL,NULL,'2024-10-08 19:45:06',NULL,0,0,NULL,0,'
<r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r> High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r> Processor】AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r> Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r> Tech Specs】1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r> Operating System】Windows 11 Pro</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:45:06',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714296,'Lenovo V15 Gen 4 Business Laptop','
<r>zoom it</r>','',549.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714295,NULL,NULL,2,NULL,-1,-2,0,100728482950021323,NULL,NULL,'2024-10-08 19:45:47',NULL,0,0,NULL,0,'
<r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r> High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r> Processor】AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r> Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r> Tech Specs】1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r> Operating System】Windows 11 Pro</r>',2,'L',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:45:47',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101055362458714300,'Lenovo V15 Gen 4 Business Laptop','
<r>zoom it</r>','',549.0,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714299,NULL,NULL,2,NULL,-1,-2,0,100728482950021323,NULL,NULL,'2024-10-08 19:46:27',NULL,0,0,NULL,0,'
<r>Brand	Lenovo
</r><r>Model Name	Lenovo V15-IKB
</r><r>Screen Size	15.6 Inches
</r><r>Color	Black
</r><r>Hard Disk Size	1 TB
</r><r>CPU Model	Ryzen 5
</r><r>Ram Memory Installed Size	24 GB
</r><r>
</r><r>About this item
</r><r> High Speed RAM And Enormous Space】24GB RAM to smoothly run multiple applications and browser tabs all at once; 1TB PCIe M.2 Solid State Drive allows to fast bootup and data transfer
</r><r> Processor】AMD Ryzen 5 5500U Processor (6 Cores, 12 Threads, 8MB Cache, Base Frequency at 2.1 GHz, Up to 4.0 GHz at Max Turbo Frequency)
</r><r> Display】15.6" FHD (1920 x 1080), IPS, anti-glare, 300 nits
</r><r> Tech Specs】1 x USB-A 3.2 Gen 1, 1 x USB-A 2.0 Gen 1, 1 x USB-C 3.2 Gen 1, 1 x HDMI, 1 x RJ45, 1 x Headphone/Microphone Combo; Webcam; Wi-Fi
</r><r> Operating System】Windows 11 Pro</r>',2,'L',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 19:46:27',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714306,'Alienware AW3423DW 34.18','
<r>Alienware AW3423DW Curved Gaming Monitor 34.18 inch Quantom Dot-OLED 1800R Display, 3440x1440 Pixels at 175Hz, True 0.1ms Gray-to-Gray, 1M:1 Contrast Ratio, 1.07 Billions Colors - Lunar Light</r>','',759.89,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,101055362458714305,-1,NULL,NULL,2,NULL,-1,-2,0,NULL,NULL,NULL,'2024-10-08 20:01:49',NULL,0,0,NULL,0,'
<r>rand	Alienware
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>IMPECCABLE DESIGN: The Legend 2.0 ID boasts a sleek QD-OLED curved 1800R panel, customizable stadium loop OLED AlienFX lighting, 360° ventilation, improved cable management, and a centralized OSD joystick for easy settings adjustment..Environmental Parameters - Min Operating Temperature : 0 °C, Max Operating Temperature : 40 °C, Humidity Range Operating : 10 - 90% (non-condensing)..ixel Pitch : 0.23 mm. Maximum Operating Temperature : 104 °F
</r><r>STUTTER-FREE SPEED: Experience ultra-low latency gameplay, HDR, cinematic color, and smooth gaming with NVIDIA G-SYNC ULTIMATE certification, industry-fast 0.1 ms GtG response time, and 175 Hz high refresh rate.
</r><r>IMMERSE IN INGINITE COLOR: Quantum Dot Display Technology enables a slim panel design and delivers a superior color performance with a higher peak luminance and greater color gamut range vs WOLED (White OLED).
</r><r>CRITICIAL CREATIVITY: Creator Mode offers precise color-critical work for content creators and gamers, allowing choice between native DCI-P3 and sRGB color spaces, and adjusting gamma settings.
</r><r>Comfort Viewing: This gaming monitor offers height adjustment, anti-glare screen, swivel and tilt adjustments, and a curved display for personalized viewing and engaging pictures, ensuring immersive experiences.</r>',2,'A',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714308,'Alienware AW3423DW 34.18','
<r>zoom iy</r>','',759.89,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714310,NULL,NULL,2,NULL,-1,-2,0,101055362458714306,NULL,NULL,'2024-10-08 20:02:50',NULL,0,0,NULL,0,'
<r>rand	Alienware
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>IMPECCABLE DESIGN: The Legend 2.0 ID boasts a sleek QD-OLED curved 1800R panel, customizable stadium loop OLED AlienFX lighting, 360° ventilation, improved cable management, and a centralized OSD joystick for easy settings adjustment..Environmental Parameters - Min Operating Temperature : 0 °C, Max Operating Temperature : 40 °C, Humidity Range Operating : 10 - 90% (non-condensing)..ixel Pitch : 0.23 mm. Maximum Operating Temperature : 104 °F
</r><r>STUTTER-FREE SPEED: Experience ultra-low latency gameplay, HDR, cinematic color, and smooth gaming with NVIDIA G-SYNC ULTIMATE certification, industry-fast 0.1 ms GtG response time, and 175 Hz high refresh rate.
</r><r>IMMERSE IN INGINITE COLOR: Quantum Dot Display Technology enables a slim panel design and delivers a superior color performance with a higher peak luminance and greater color gamut range vs WOLED (White OLED).
</r><r>CRITICIAL CREATIVITY: Creator Mode offers precise color-critical work for content creators and gamers, allowing choice between native DCI-P3 and sRGB color spaces, and adjusting gamma settings.
</r><r>Comfort Viewing: This gaming monitor offers height adjustment, anti-glare screen, swivel and tilt adjustments, and a curved display for personalized viewing and engaging pictures, ensuring immersive experiences.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714309,'Alienware AW3423DW 34.18','
<r>zoom iy</r>','',759.89,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714307,NULL,NULL,2,NULL,-1,-2,0,101055362458714306,NULL,NULL,'2024-10-08 20:02:36',NULL,0,0,NULL,0,'
<r>rand	Alienware
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>IMPECCABLE DESIGN: The Legend 2.0 ID boasts a sleek QD-OLED curved 1800R panel, customizable stadium loop OLED AlienFX lighting, 360° ventilation, improved cable management, and a centralized OSD joystick for easy settings adjustment..Environmental Parameters - Min Operating Temperature : 0 °C, Max Operating Temperature : 40 °C, Humidity Range Operating : 10 - 90% (non-condensing)..ixel Pitch : 0.23 mm. Maximum Operating Temperature : 104 °F
</r><r>STUTTER-FREE SPEED: Experience ultra-low latency gameplay, HDR, cinematic color, and smooth gaming with NVIDIA G-SYNC ULTIMATE certification, industry-fast 0.1 ms GtG response time, and 175 Hz high refresh rate.
</r><r>IMMERSE IN INGINITE COLOR: Quantum Dot Display Technology enables a slim panel design and delivers a superior color performance with a higher peak luminance and greater color gamut range vs WOLED (White OLED).
</r><r>CRITICIAL CREATIVITY: Creator Mode offers precise color-critical work for content creators and gamers, allowing choice between native DCI-P3 and sRGB color spaces, and adjusting gamma settings.
</r><r>Comfort Viewing: This gaming monitor offers height adjustment, anti-glare screen, swivel and tilt adjustments, and a curved display for personalized viewing and engaging pictures, ensuring immersive experiences.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 20:02:36',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714312,'Alienware AW3423DW 34.18','
<r>zoom iy</r>','',759.89,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714311,NULL,NULL,2,NULL,-1,-2,0,101055362458714306,NULL,NULL,'2024-10-08 20:03:02',NULL,0,0,NULL,0,'
<r>rand	Alienware
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>IMPECCABLE DESIGN: The Legend 2.0 ID boasts a sleek QD-OLED curved 1800R panel, customizable stadium loop OLED AlienFX lighting, 360° ventilation, improved cable management, and a centralized OSD joystick for easy settings adjustment..Environmental Parameters - Min Operating Temperature : 0 °C, Max Operating Temperature : 40 °C, Humidity Range Operating : 10 - 90% (non-condensing)..ixel Pitch : 0.23 mm. Maximum Operating Temperature : 104 °F
</r><r>STUTTER-FREE SPEED: Experience ultra-low latency gameplay, HDR, cinematic color, and smooth gaming with NVIDIA G-SYNC ULTIMATE certification, industry-fast 0.1 ms GtG response time, and 175 Hz high refresh rate.
</r><r>IMMERSE IN INGINITE COLOR: Quantum Dot Display Technology enables a slim panel design and delivers a superior color performance with a higher peak luminance and greater color gamut range vs WOLED (White OLED).
</r><r>CRITICIAL CREATIVITY: Creator Mode offers precise color-critical work for content creators and gamers, allowing choice between native DCI-P3 and sRGB color spaces, and adjusting gamma settings.
</r><r>Comfort Viewing: This gaming monitor offers height adjustment, anti-glare screen, swivel and tilt adjustments, and a curved display for personalized viewing and engaging pictures, ensuring immersive experiences.</r>',2,'A',1,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 20:03:02',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101055362458714314,'Alienware AW3423DW 34.18','
<r>zoom iy</r>','',759.89,1,NULL,-1,3,NULL,1,100728482950021285,NULL,NULL,-1,101055362458714313,NULL,NULL,2,NULL,-1,-2,0,101055362458714306,NULL,NULL,'2024-10-08 20:03:37',NULL,0,0,NULL,0,'
<r>rand	Alienware
</r><r>Screen Size	34 Inches
</r><r>Resolution	QHD Ultra Wide 1440p
</r><r>Aspect Ratio	21:9
</r><r>Screen Surface Description	Matte
</r><r>About this item
</r><r>IMPECCABLE DESIGN: The Legend 2.0 ID boasts a sleek QD-OLED curved 1800R panel, customizable stadium loop OLED AlienFX lighting, 360° ventilation, improved cable management, and a centralized OSD joystick for easy settings adjustment..Environmental Parameters - Min Operating Temperature : 0 °C, Max Operating Temperature : 40 °C, Humidity Range Operating : 10 - 90% (non-condensing)..ixel Pitch : 0.23 mm. Maximum Operating Temperature : 104 °F
</r><r>STUTTER-FREE SPEED: Experience ultra-low latency gameplay, HDR, cinematic color, and smooth gaming with NVIDIA G-SYNC ULTIMATE certification, industry-fast 0.1 ms GtG response time, and 175 Hz high refresh rate.
</r><r>IMMERSE IN INGINITE COLOR: Quantum Dot Display Technology enables a slim panel design and delivers a superior color performance with a higher peak luminance and greater color gamut range vs WOLED (White OLED).
</r><r>CRITICIAL CREATIVITY: Creator Mode offers precise color-critical work for content creators and gamers, allowing choice between native DCI-P3 and sRGB color spaces, and adjusting gamma settings.
</r><r>Comfort Viewing: This gaming monitor offers height adjustment, anti-glare screen, swivel and tilt adjustments, and a curved display for personalized viewing and engaging pictures, ensuring immersive experiences.</r>',2,'A',2,0,0,0,0,0,0,0,0,0,0,0,'2024-10-08 20:03:37',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',0.0,0.0,0.0,'',NULL),
	 (101087870529306626,'bordo_baklajan_v325','
<r></r>','',0.0,1,NULL,101087870529306631,3,NULL,1,100728482950021285,NULL,NULL,101087870529306624,101087870529306625,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:37:00',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_baklajan_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306630,'bordo_glina_v325','
<r></r>','',0.0,1,NULL,101087870529306629,3,NULL,1,100728482950021285,NULL,NULL,101087870529306627,101087870529306628,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:36:53',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_glina_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306635,'bordo_haki_v325','
<r></r>','',0.0,1,NULL,101087870529306634,3,NULL,1,100728482950021285,NULL,NULL,101087870529306632,101087870529306633,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 01:58:14',NULL,0,0,NULL,0,'
',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 01:58:14',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_haki_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306639,'bordo_noch_v325','
<r></r>','',0.0,1,NULL,101087870529306638,3,NULL,1,100728482950021285,NULL,NULL,101087870529306636,101087870529306637,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:36:45',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_noch_v325','b',0.0,0.0,0.0,'',NULL);
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
	 (101087870529306640,'bordo_orange_v325','
<r></r>','',0.0,1,NULL,101087870529306643,3,NULL,1,100728482950021285,NULL,NULL,101087870529306641,101087870529306642,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:36:35',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_orange_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306647,'bordo_pepel_v325','
<r></r>','',0.0,1,NULL,101087870529306646,3,NULL,1,100728482950021285,NULL,NULL,101087870529306644,101087870529306645,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:36:27',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_pepel_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306651,'bordo_red_v325','
<r></r>','',0.0,1,NULL,101087870529306650,3,NULL,1,100728482950021285,NULL,NULL,101087870529306648,101087870529306649,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:36:21',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_red_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306655,'bordo_shokolad_v325','
<r></r>','',0.0,1,NULL,101087870529306654,3,NULL,1,100728482950021285,NULL,NULL,101087870529306652,101087870529306653,NULL,NULL,2,NULL,-1,-3,0,NULL,NULL,NULL,'2024-10-31 02:37:19',NULL,0,0,NULL,0,'
<r>The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_shokolad_v325','b',0.0,0.0,0.0,'',NULL);
	 
INSERT INTO cmsdb.soft (SOFT_ID,NAME,DESCRIPTION,VERSION,COST,CURRENCY,SERIAL_NUBMER,FILE_ID,TYPE_ID,LEVELUP,ACTIVE,USER_ID,PHONETYPE_ID,PROGNAME_ID,IMAGE_ID,BIGIMAGE_ID,WEIGHT,COUNT,LANG_ID,PHONEMODEL_ID,LICENCE_ID,CATALOG_ID,SALELOGIC_ID,TREE_ID,CARD_NUMBER,CARD_CODE,START_DIAL_TIME,END_DIAL_TIME,START_ACTIVATION_CARD,END_ACTIVATION_CARD,TYPE_CARD_ID,PRODUCT_CODE,FULLDESCRIPTION,SITE_ID,`SEARCH`,PORTLETTYPE_ID,CRETERIA1_ID,CRETERIA2_ID,CRETERIA3_ID,CRETERIA4_ID,CRETERIA5_ID,CRETERIA6_ID,CRETERIA7_ID,CRETERIA8_ID,CRETERIA9_ID,CRETERIA10_ID,STATISTIC_ID,CDATE,CRETERIA11_ID,CRETERIA12_ID,CRETERIA13_ID,CRETERIA14_ID,CRETERIA15_ID,SQUARE,RATING_SUMM1,RATING_SUMM2,RATING_SUMM3,COUNTPOST_RATING1,COUNTPOST_RATING2,COUNTPOST_RATING3,MIDLE_BAL1,MIDLE_BAL2,MIDLE_BAL3,SHOW_RATING1,SHOW_RATING2,SHOW_RATING3,SHOW_BLOG,NAME2,SEARCH2,AMOUNT1,AMOUNT2,AMOUNT3,JSP_URL,COLOR) VALUES
(101087870529306670,'bordo_baklajan_v325','
<r></r>','',0.0,1,NULL,101087870529306631,3,NULL,1,100728482950021285,NULL,NULL,101087870529306624,101087870529306625,NULL,NULL,2,NULL,-1,-14,0,NULL,NULL,NULL,'2024-10-31 02:37:00',NULL,0,0,NULL,0,'
<r>So this is shipping company design . Please just click to create shipping company for marketplace. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_baklajan_v325','b',0.0,0.0,0.0,'',NULL),
(101087870529306671,'bordo_baklajan_v325','
<r></r>','',0.0,1,NULL,101087870529306631,3,NULL,1,100728482950021285,NULL,NULL,101087870529306624,101087870529306625,NULL,NULL,2,NULL,-1,-15,0,NULL,NULL,NULL,'2024-10-31 02:37:00',NULL,0,0,NULL,0,'
<r>So this is resolurion incidents company design . Please just click to create resolurion incidents company for marketplace. Please check with adminintation if marketplace wants to work with you . The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_baklajan_v325','b',0.0,0.0,0.0,'',NULL),
(101087870529306672,'bordo_glina_v325','
<r></r>','',0.0,1,NULL,101087870529306629,3,NULL,1,100728482950021285,NULL,NULL,101087870529306627,101087870529306628,NULL,NULL,2,NULL,-1,-14,0,NULL,NULL,NULL,'2024-10-31 02:36:53',NULL,0,0,NULL,0,'
<r>So this is shipping company design . Please just click to create shipping company for marketplace. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_glina_v325','b',0.0,0.0,0.0,'',NULL),
(101087870529306673,'bordo_glina_v325','
<r></r>','',0.0,1,NULL,101087870529306629,3,NULL,1,100728482950021285,NULL,NULL,101087870529306627,101087870529306628,NULL,NULL,2,NULL,-1,-15,0,NULL,NULL,NULL,'2024-10-31 02:36:53',NULL,0,0,NULL,0,'
<r>So this is resolurion incidents company design . Please just click to create resolurion incidents company for marketplace. Please check with adminintation if marketplace wants to work with you . The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_glina_v325','b',0.0,0.0,0.0,'',NULL),
(101087870529306674,'bordo_noch_v325','
<r></r>','',0.0,1,NULL,101087870529306638,3,NULL,1,100728482950021285,NULL,NULL,101087870529306636,101087870529306637,NULL,NULL,2,NULL,-1,-14,0,NULL,NULL,NULL,'2024-10-31 02:36:45',NULL,0,0,NULL,0,'
<r>So this is shipping company design . Please just click to create shipping company for marketplace. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_noch_v325','b',0.0,0.0,0.0,'',NULL),
(101087870529306675,'bordo_noch_v325','
<r></r>','',0.0,1,NULL,101087870529306638,3,NULL,1,100728482950021285,NULL,NULL,101087870529306636,101087870529306637,NULL,NULL,2,NULL,-1,-15,0,NULL,NULL,NULL,'2024-10-31 02:36:45',NULL,0,0,NULL,0,'
<r>So this is shipping company design . Please just click to create shipping company for marketplace. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_noch_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306676,'bordo_pepel_v325','
<r></r>','',0.0,1,NULL,101087870529306646,3,NULL,1,100728482950021285,NULL,NULL,101087870529306644,101087870529306645,NULL,NULL,2,NULL,-1,-14,0,NULL,NULL,NULL,'2024-10-31 02:36:27',NULL,0,0,NULL,0,'
<r>So this is resolurion incidents company design . Please just click to create resolurion incidents company for marketplace. Please check with adminintation if marketplace wants to work with you. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_pepel_v325','b',0.0,0.0,0.0,'',NULL),
	 (101087870529306677,'bordo_pepel_v325','
<r></r>','',0.0,1,NULL,101087870529306646,3,NULL,1,100728482950021285,NULL,NULL,101087870529306644,101087870529306645,NULL,NULL,2,NULL,-1,-15,0,NULL,NULL,NULL,'2024-10-31 02:36:27',NULL,0,0,NULL,0,'
<r>So this is shipping company design . Please just click to create shipping company for marketplace. The front-end container represents only front-end files. It includes HTML, JavaScript, CSS, and XSLT templates that generate HTML content and components in the web browser from XML REST web services.
</r><r>
</r><r>The CMS has a REST API via Spring Boot, along with Open API (Swagger) using JSON input and output. This new generation Enterprise e-commerce Portal solution allows deployment of front-end containers and back-end Docker containers with REST APIs.
</r><r>
</r><r>By using CMS Manhattan API Gateway, you can switch between new and old versions of web services for the UI without changing the endpoints for the web services.</r>',2,'b',0,0,0,0,0,0,0,0,0,0,0,0,'2024-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,'bordo_pepel_v325','b',0.0,0.0,0.0,'',NULL);





INSERT INTO cmsdb.tuser (USER_ID,LOGIN,PASSWD,FIRST_NAME,LAST_NAME,COMPANY,E_MAIL,PHONE,MOBIL_PHONE,FAX,ICQ,WEBSITE,QUESTION,ANSWER,IDSESSION,BIRTHDAY,REGDATE,LEVELUP_CD,BANK_CD,ACVIVE_SESSION,ACTIVE,COUNTRY,CITY,ZIP,STATE,SCOUNTRY,MIDDLENAME,CITY_ID,COUNTRY_ID,CURRENCY_ID,TREE_ID,SITE_ID) VALUES
	 (100728482950021285,'admin','admin',NULL,NULL,' your company name ',' your@mail',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (100728482950021287,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-10,'usps','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-10),
	 (-110,'usps','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-20,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-10),
	 (-9,'ups','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-9),
	 (-119,'ups','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-19,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-9),
	 (-8,'fedex','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-8),
	 (-118,'fedex','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-18,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-8),
	 (-7,'dhl','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-7),
	 (-117,'dhl','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-17,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-7),
	 (-6,'fullfilment','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-6),
	 (-116,'fullfilment','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-16,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-6),
	 (-5,'tester','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-4,'resolutioncenter','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',2,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-4),
	 (-114,'resolutioncenter','password',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',1,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,2),
	 (-14,'user','user',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-02-26 00:00:00','2024-02-26 00:00:00',0,0,1,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,NULL,-4);
 

	 
	 INSERT INTO cmsdb.typesoft (TYPE_ID,USER_ID,TYPE_LABLE,TYPE_DESC,ACTIVE,TAX) VALUES
	 (2,1,'Show in Marketplace','Show in Marketplace',1,0.0),
	 (3,1,'Show in my catalog','Show in my catalog',1,0.0);
	 
-- 
-- Order change staus event .
-- 	 
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `change_status` AFTER UPDATE ON orders FOR EACH ROW
BEGIN

INSERT INTO shipping_tracking 
 ( ORDER_ID, NOTICE,  ITEM_CURRENT_LOCATION, USER_ID,  SOFT_ID, SITE_ID , SHIPPING_COMPANY_ID)
 SELECT OLD.ORDER_ID , 'It is waiting a tracking number ',  'The shop warehouse ' , OLD.USER_ID , soft.soft_id , soft.site_id , NEW.SHIPPING_COMPANY_ID FROM soft  LEFT JOIN  basket  ON soft.soft_id = basket.product_id WHERE ORDER_ID = OLD.ORDER_ID  AND NOT EXISTS ( SELECT SHIPPING_TRACKING_ID FROM shipping_tracking WHERE ORDER_ID = OLD.ORDER_ID  )  ;
	
INSERT INTO orders_hist
(ORDER_ID, NOTICE, USER_ID, SOFT_ID, SITE_ID,  IMG_URL , NAME,  DESCRIPTION , FULLDESCRIPTION , BIG_IMG_URL , CDATE ,  DELIVERY_TIMEEND, PRODUCT_COST, CURRENCY , QUANTITY , ORDER_AMOUNT , TAX, ORDER_END_AMOUNT, DELIVERY_AMOUNT, DELIVERY_DAYS, PAYSTATUS_ID, ORDER_DELIVERYSTATUS_ID, ITEM_DELIVERYSTATUS_ID , SHIPPING_TRACKING_ID, SHIPPING_COMPANY_ID)
SELECT OLD.ORDER_ID AS ORDER_ID , OLD.DESCRIPTION , OLD.USER_ID AS USER_ID , soft.soft_id , soft.site_id , images.img_url , soft.name  , soft.description , soft.fulldescription , big_images.img_url, soft.cdate , current_timestamp() AS DELIVERY_TIMEEND, soft.COST  , soft.CURRENCY , basket.QUANTITY , OLD.AMOUNT AS ORDER_AMOUNT , 0 AS TAX, OLD.END_AMOUNT AS ORDER_END_AMOUNT , OLD.DELIVERY_AMOUNT AS DELIVERY_AMOUNT , OLD.DELIVERY_LONG AS DELIVERY_DAYS , OLD.PAYSTATUS_ID AS PAYSTATUS_ID , OLD.DELIVERYSTATUS_ID AS DELIVERYSTATUS_ID , OLD.DELIVERYSTATUS_ID AS ITEM_DELIVERYSTATUS_ID , shipping_tracking.shipping_tracking_id , OLD.SHIPPING_COMPANY_ID AS SHIPPING_COMPANY_ID FROM soft  LEFT JOIN shipping_tracking ON soft.soft_id = shipping_tracking.soft_id  LEFT JOIN images ON soft.image_id = images.image_id  LEFT  JOIN big_images ON soft.bigimage_id = big_images.big_images_id  LEFT  JOIN file  ON soft.file_id = file.file_id  LEFT JOIN  basket  ON soft.soft_id = basket.product_id  WHERE basket.ORDER_ID = OLD.ORDER_ID;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

/*!40000 ALTER TABLE `soft` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER changes
BEFORE UPDATE 
ON soft FOR EACH ROW
BEGIN
INSERT INTO cmsdb.soft_hist
(SOFT_ID, NAME, DESCRIPTION, VERSION, COST, CURRENCY, SERIAL_NUBMER, FILE_ID, TYPE_ID, LEVELUP, ACTIVE, USER_ID, PHONETYPE_ID, PROGNAME_ID, IMAGE_ID, BIGIMAGE_ID, WEIGHT, COUNT, LANG_ID, PHONEMODEL_ID, LICENCE_ID, CATALOG_ID, SALELOGIC_ID, TREE_ID, CARD_NUMBER, CARD_CODE, START_DIAL_TIME, END_DIAL_TIME, START_ACTIVATION_CARD, END_ACTIVATION_CARD, TYPE_CARD_ID, PRODUCT_CODE, FULLDESCRIPTION, SITE_ID, `SEARCH`, PORTLETTYPE_ID, CRETERIA1_ID, CRETERIA2_ID, CRETERIA3_ID, CRETERIA4_ID, CRETERIA5_ID, CRETERIA6_ID, CRETERIA7_ID, CRETERIA8_ID, CRETERIA9_ID, CRETERIA10_ID, STATISTIC_ID, CDATE, CRETERIA11_ID, CRETERIA12_ID, CRETERIA13_ID, CRETERIA14_ID, CRETERIA15_ID, SQUARE, RATING_SUMM1, RATING_SUMM2, RATING_SUMM3, COUNTPOST_RATING1, COUNTPOST_RATING2, COUNTPOST_RATING3, MIDLE_BAL1, MIDLE_BAL2, MIDLE_BAL3, SHOW_RATING1, SHOW_RATING2, SHOW_RATING3, SHOW_BLOG, SHOW_OFFER, SHOW_ACTION, NAME2, SEARCH2, AMOUNT1, AMOUNT2, AMOUNT3, JSP_URL, COLOR, ACTION_BID_STATUS_ID, OFFER_STATUS_ID)
VALUES(OLD.SOFT_ID, OLD.NAME, OLD.DESCRIPTION, OLD.VERSION, OLD.COST, OLD.CURRENCY, OLD.SERIAL_NUBMER, OLD.FILE_ID, OLD.TYPE_ID, OLD.LEVELUP, OLD.ACTIVE, OLD.USER_ID, OLD.PHONETYPE_ID, OLD.PROGNAME_ID, OLD.IMAGE_ID, OLD.BIGIMAGE_ID, OLD.WEIGHT, OLD.COUNT, OLD.LANG_ID, OLD.PHONEMODEL_ID, OLD.LICENCE_ID, OLD.CATALOG_ID, OLD.SALELOGIC_ID, OLD.TREE_ID, OLD.CARD_NUMBER, OLD.CARD_CODE, OLD.START_DIAL_TIME, OLD.END_DIAL_TIME, OLD.START_ACTIVATION_CARD, OLD.END_ACTIVATION_CARD, OLD.TYPE_CARD_ID, OLD.PRODUCT_CODE, OLD.FULLDESCRIPTION, OLD.SITE_ID, OLD.`SEARCH`, OLD.PORTLETTYPE_ID, OLD.CRETERIA1_ID, OLD.CRETERIA2_ID, OLD.CRETERIA3_ID, OLD.CRETERIA4_ID, OLD.CRETERIA5_ID, OLD.CRETERIA6_ID, OLD.CRETERIA7_ID, OLD.CRETERIA8_ID, OLD.CRETERIA9_ID, OLD.CRETERIA10_ID, OLD.STATISTIC_ID, OLD.CDATE, OLD.CRETERIA11_ID, OLD.CRETERIA12_ID, OLD.CRETERIA13_ID, OLD.CRETERIA14_ID, OLD.CRETERIA15_ID, OLD.SQUARE, OLD.RATING_SUMM1, OLD.RATING_SUMM2, OLD.RATING_SUMM3, OLD.COUNTPOST_RATING1, OLD.COUNTPOST_RATING2, OLD.COUNTPOST_RATING3, OLD.MIDLE_BAL1, OLD.MIDLE_BAL2, OLD.MIDLE_BAL3, OLD.SHOW_RATING1, OLD.SHOW_RATING2, OLD.SHOW_RATING3, OLD.SHOW_BLOG, OLD.SHOW_OFFER, OLD.SHOW_ACTION, OLD.NAME2, OLD.SEARCH2, OLD.AMOUNT1, OLD.AMOUNT2, OLD.AMOUNT3, OLD.JSP_URL, OLD.COLOR, OLD.ACTION_BID_STATUS_ID, OLD.OFFER_STATUS_ID);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
