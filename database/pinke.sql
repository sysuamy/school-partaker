--------------------------------------------
-- Export file for user PINKE             --
-- Created by troy on 2015/5/22, 10:20:12 --
--------------------------------------------

set define off
spool pinke.log

prompt
prompt Creating table T_IV_CENTER
prompt ==========================
prompt
create table PINKE.T_IV_CENTER
(
  iv_key  NUMBER not null,
  name    VARCHAR2(20),
  content VARCHAR2(200),
  iv_date VARCHAR2(20),
  ic_url  VARCHAR2(50),
  suser   VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PINKE.T_IV_CENTER
  add constraint PK_T_IV_CENTER primary key (IV_KEY)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_REVIEW
prompt =======================
prompt
create table PINKE.T_REVIEW
(
  re_fkey     NUMBER,
  re_key      NUMBER not null,
  re_date     VARCHAR2(20),
  re_sender   VARCHAR2(20),
  re_accepter VARCHAR2(20),
  re_content  VARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PINKE.T_REVIEW
  add constraint PK_T_REVIEW primary key (RE_KEY)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_SHARE
prompt ======================
prompt
create table PINKE.T_SHARE
(
  m_id             NUMBER not null,
  m_title          VARCHAR2(20),
  m_category       VARCHAR2(20),
  m_place          VARCHAR2(20),
  m_start_date     DATE,
  m_end_date       DATE,
  m_join_num       NUMBER,
  m_need_num       NUMBER,
  m_icon_url       VARCHAR2(50),
  m_content        VARCHAR2(500),
  m_contacts       VARCHAR2(50),
  m_state          VARCHAR2(10),
  m_launch         VARCHAR2(20),
  m_start_date_str VARCHAR2(20),
  m_end_date_str   VARCHAR2(20),
  m_place_str      VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PINKE.T_SHARE
  add constraint PK_T_SHARE primary key (M_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_USER
prompt =====================
prompt
create table PINKE.T_USER
(
  suser     VARCHAR2(50) not null,
  sname     VARCHAR2(50),
  spwd      VARCHAR2(50),
  sphone    VARCHAR2(50),
  saddress  VARCHAR2(50),
  simg_path VARCHAR2(50),
  iv_times  NUMBER default 0 not null,
  iv_title  NUMBER default 0 not null,
  iv_score  NUMBER default 0 not null,
  sarea     VARCHAR2(20),
  scard     VARCHAR2(20),
  ssex      NUMBER,
  sschool   VARCHAR2(50),
  semail    VARCHAR2(100)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column PINKE.T_USER.iv_times
  is '服务时长';
comment on column PINKE.T_USER.iv_title
  is '服务等级';
comment on column PINKE.T_USER.iv_score
  is '服务志良';
comment on column PINKE.T_USER.sarea
  is '服务地区';
comment on column PINKE.T_USER.scard
  is '身份证ID';
alter table PINKE.T_USER
  add constraint PK_T_USER primary key (SUSER)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_USER_SHARE
prompt ===========================
prompt
create table PINKE.T_USER_SHARE
(
  suser VARCHAR2(20),
  m_id  NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence SEQ_T_IV_CENTER
prompt =================================
prompt
create sequence PINKE.SEQ_T_IV_CENTER
minvalue 1
maxvalue 9999999999999999999999999999
start with 103
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_T_REVIEW
prompt ==============================
prompt
create sequence PINKE.SEQ_T_REVIEW
minvalue 1
maxvalue 9999999999999999999999999999
start with 91
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_T_SHARE
prompt =============================
prompt
create sequence PINKE.SEQ_T_SHARE
minvalue 1
maxvalue 9999999999999999999999999999
start with 111
increment by 1
cache 20;


spool off
