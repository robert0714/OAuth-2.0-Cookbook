-- create the database for OAuth 2.0 provider and admin user
CREATE DATABASE oauth2provider;
CREATE USER 'oauth2provider'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON oauth2provider.* TO 'oauth2provider'@'localhost';

-- access the recently created database oauth2provider
use oauth2provider;

-- create needed structure for oauth provider
drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);
drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);
 
drop table if exists oauth_access_token;
CREATE TABLE oauth_access_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BLOB,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication BLOB,
  refresh_token VARCHAR(256) DEFAULT NULL
);
 
drop table if exists oauth_refresh_token;
CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BLOB,
  authentication BLOB
);
 
drop table if exists oauth_code;
create table oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
);
drop table if exists oauth_approvals;
create table oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt DATETIME,
    lastModifiedAt DATETIME
);

INSERT INTO oauth_client_details
    (client_id, resource_ids, client_secret, scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
    additional_information, autoapprove)
VALUES
    ('clientapp', null, '123456',
    'read_profile,read_posts', 'authorization_code',
    'http://localhost:9000/callback',
    null, null, null, null, 'false');

-- defines resource server credentials for token introspection
INSERT INTO oauth_client_details (
  client_id, resource_ids, client_secret, scope, authorized_grant_types,
  web_server_redirect_uri, authorities, access_token_validity,
  refresh_token_validity, additional_information, autoapprove)
VALUES (
  'resource_server', '', 'abc123', 'read_profile,write_profile',
  'authorization_code', 'http://localhost:9000/callback', 'introspection',
  null, null, null, '');

-- creates a client details
INSERT INTO oauth_client_details (
  client_id, resource_ids, client_secret, scope, authorized_grant_types,
  web_server_redirect_uri, authorities, access_token_validity,
  refresh_token_validity, additional_information, autoapprove)
VALUES (
  'clientxpto', '', '123', 'read_profile,write_profile',
  'authorization_code,password', 'http://localhost:9000/callback', null,
  null, null, null, '');

-- creates user database (resource owner table)
create table resource_owner(
  id bigint auto_increment primary key,
  name varchar(200),
  username varchar(60),
  password varchar(100),
  email varchar(100)
);

insert into resource_owner (name, username, password, email)
values ('Adolfo Eloy', 'adolfo', '123', 'adolfo@mailinator.com');