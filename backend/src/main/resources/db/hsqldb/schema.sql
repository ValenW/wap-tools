CREATE SCHEMA `wap_tools` DEFAULT CHARACTER SET utf8 ;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS link;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS tag_link;
DROP TABLE IF EXISTS text_resource;

CREATE TABLE user (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(80),
  password      VARCHAR(80)
);

CREATE  TABLE  link(
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(80),
  href          VARCHAR(1000),
  status        INT,
  create_time   DATETIME,
  update_time   DATETIME
);

CREATE  TABLE  tag(
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(40),
  color         VARCHAR(40),
  sys           INT
);

CREATE  TABLE  tag_link(
  tag_id        INT,
  link_id       INT,
  PRIMARY KEY (tag_id, link_id)
);

CREATE TABLE text_resource (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  en            VARCHAR(200),
  ja            VARCHAR(200),
  create_time   DATETIME,
  update_time   DATETIME,
  ip            VARCHAR(45)
)
