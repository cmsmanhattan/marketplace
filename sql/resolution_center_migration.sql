-- Resolution Center: link an order to a resolution center and keep the case status.
-- Optional. The application detects these columns at runtime (DatabaseMetaData) and
-- only writes them when present; without the migration the order page simply does
-- not offer the "Resolution center" choice.

ALTER TABLE orders
  ADD COLUMN RESOLUTION_CENTER_ID BIGINT NULL,
  ADD COLUMN RESOLUTION_STATUS_ID BIGINT NULL;

-- The status folders of the resolution-center site (-4) were created with
-- PARENT_ID = -26, a catalog that exists only on the carrier sites, so
-- "New / In process / Solved" never appeared in the site menu. Their parent is
-- the "Statuses" folder (-19).
UPDATE catalog SET PARENT_ID = -19
 WHERE SITE_ID = -4 AND CATALOG_ID IN (-20, -21, -22) AND PARENT_ID = -26;
