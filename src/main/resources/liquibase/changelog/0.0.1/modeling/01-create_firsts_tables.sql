-- Table: groups

CREATE TABLE groups
(
  group_id serial NOT NULL,
  group_name character varying(200),
  group_index integer,
  CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

-- Table: sections

CREATE TABLE sections
(
  section_id serial NOT NULL,
  section_name character varying(200) NOT NULL,
  section_index integer,
  CONSTRAINT sections_pkey PRIMARY KEY (section_id)
);

-- Table: users

CREATE TABLE users
(
  user_id serial NOT NULL,
  user_login character varying(50) NOT NULL,
  user_last_ip_login character varying(40),
  user_password character varying(32) NOT NULL,
  user_last_date_login timestamp without time zone,
  user_mail character varying(255),
  user_login_state smallint NOT NULL DEFAULT 0,
  user_token character varying(32),
  user_salt character varying(40),
  user_verification_token character varying(32),
  user_date_verification_token timestamp without time zone,
  CONSTRAINT users_pkey PRIMARY KEY (user_id),
  CONSTRAINT users_user_login_key UNIQUE (user_login)
);

-- Table: sheets

CREATE TABLE sheets
(
  sheet_id serial NOT NULL,
  sheet_title character varying(200) NOT NULL,
  sheet_description text,
  sheet_token character varying(20),
  sheet_password character varying(40),
  user_id integer,
  sheet_headers text,
  CONSTRAINT sheets_pkey PRIMARY KEY (sheet_id),
  CONSTRAINT sheets_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sheets_sheet_token_key UNIQUE (sheet_token)
);

-- Table: lines

CREATE TABLE lines
(
  line_id serial NOT NULL,
  line_index integer,
  line_is_selected character(1) NOT NULL DEFAULT 'N'::bpchar,
  line_data text,
  section_id integer,
  group_id integer,
  sheet_id integer,
  CONSTRAINT lines_pkey PRIMARY KEY (line_id),
  CONSTRAINT lines_group_id_fkey FOREIGN KEY (group_id)
      REFERENCES groups (group_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT lines_section_id_fkey FOREIGN KEY (section_id)
      REFERENCES sections (section_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT lines_sheet_id_fkey FOREIGN KEY (sheet_id)
      REFERENCES sheets (sheet_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
