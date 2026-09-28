-- Event notifications (bids, offers, orders, disputes) shown on Notifications.jsp.
-- Optional: if the table is absent the application logs one warning and skips
-- writing events; the old subscription/soft_hist list keeps working.
CREATE TABLE IF NOT EXISTS `notification` (
  `NOTIFICATION_ID` bigint NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint NOT NULL,
  `SITE_ID` bigint NOT NULL,
  `KIND` varchar(40) NOT NULL,
  `MESSAGE` varchar(500) NOT NULL,
  `PRODUCT_ID` bigint DEFAULT NULL,
  `ORDER_ID` bigint DEFAULT NULL,
  `AMOUNT` double DEFAULT NULL,
  `CDATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IS_READ` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`NOTIFICATION_ID`),
  KEY `ix_notification_user` (`USER_ID`, `CDATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
