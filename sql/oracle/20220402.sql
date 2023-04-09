-- xyf
alter table approve add  sprcodeone varchar2(16);
alter table approve add  sprnameone varchar2(64);
alter table approve add  sprcodetwo varchar2(16);
alter table approve add  sprnametwo varchar2(64);
comment on column approve.sprcodeone is '一级审批人工号';
comment on column approve.sprnameone is '一级审批人名称';
comment on column approve.sprcodetwo is '二级审批人工号';
comment on column approve.sprnametwo is '二级审批人名称';

alter table tbmqq430 add  agentno varchar2(16);
alter table tbmqq430 add  agentphone varchar2(11);
comment on column tbmqq430.agentno is '物资采购人工号';
comment on column tbmqq430.agentphone is '物资采购人联系电话';

alter table tbmqq433 add  remark varchar2(500 char);
alter table tbmqq433 add  agentno varchar2(16);
alter table tbmqq433 add  agentphone varchar2(11);
comment on column tbmqq433.remark is '普通备注';
comment on column tbmqq433.agentno is '物资采购人工号';
comment on column tbmqq433.agentphone is '物资采购人联系电话';

alter table tbmqq434 add  remark varchar2(500 char);
comment on column tbmqq434.remark is '普通备注';

alter table tbmqq436 add  agentno varchar2(16);
alter table tbmqq436 add  agentphone varchar2(11);
comment on column tbmqq436.agentno is '物资采购人工号';
comment on column tbmqq436.agentphone is '物资采购人联系电话';

alter table tbmqq437 add  agentno varchar2(16);
alter table tbmqq437 add  agentphone varchar2(11);
comment on column tbmqq437.agentno is '物资采购人工号';
comment on column tbmqq437.agentphone is '物资采购人联系电话';

alter table tbmqq441 add  remark2 varchar2(500 char);
alter table tbmqq441 add  agentno varchar2(16);
alter table tbmqq441 add  agentphone varchar2(11);
comment on column tbmqq441.remark2 is '普通备注';
comment on column tbmqq441.agentno is '物资采购人工号';
comment on column tbmqq441.agentphone is '物资采购人联系电话';

-- qy
alter table tbmqq440 add  invalidreason varchar2(500 char);
alter table tbmqq440 add  invaliduser varchar2(30);
alter table tbmqq440 add  invalidusername varchar2(128);
alter table tbmqq440 add  invaliddate varchar2(8);
alter table tbmqq440 add  invalidtime varchar2(6);
comment on column tbmqq440.invalidreason is '作废理由';
comment on column tbmqq440.invaliduser is '作废人';
comment on column tbmqq440.invalidusername is '作废人姓名';
comment on column tbmqq440.invaliddate is '作废日期';
comment on column tbmqq440.invalidtime is '作废时间';




