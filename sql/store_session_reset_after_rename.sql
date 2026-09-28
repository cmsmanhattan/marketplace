-- Run ONCE when deploying the camelCase rename (pass 40+).
--
-- store_session.CLASSBODY holds Java-serialized session beans (AuthorizationPageBean,
-- PublisherBean, ...). Java serialization matches fields BY NAME; the beans keep an
-- explicit serialVersionUID, so rows written by the old build still deserialize, but
-- every renamed field (site_id -> siteId, catalog_id -> catalogId, ...) silently comes
-- back as null/0. Clearing the table forces users to re-login and rebuild state from
-- scratch instead of resuming a half-empty session.
TRUNCATE TABLE store_session;
