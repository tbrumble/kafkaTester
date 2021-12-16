CREATE SCHEMA IF NOT EXISTS APPDATA;

CREATE TABLE APPDATA.Setting
(
    ID SERIAL NOT NULL UNIQUE PRIMARY KEY,
    NAME VARCHAR(32) NOT NULL,
    VALUE VARCHAR(512)
);

COMMENT ON TABLE APPDATA.Setting IS 'Application settings. Key-value map structure.';
COMMENT ON COLUMN APPDATA.Setting.NAME IS 'Setting name.';
COMMENT ON COLUMN APPDATA.Setting.VALUE IS 'Setting value. Can be null, empty.';


