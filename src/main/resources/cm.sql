create table cm.cm_manager_user(
  id int(8) primary key auto_increment,
  account_num varchar(64) not null default '' comment '帐号',
  cpassword varchar(64) not null default '' comment '密码',
  department_id tinyint(4) not null default 0 comment '部门编号',
  userType tinyint(4) default 0 comment '用户类型',
  name varchar(64) not null default '' comment '真实姓名',
  phone varchar(64) not null default '' comment '手机',
  purchase_commission tinyint(4) default 0 comment '采购提成比例',
  active tinyint(4) default 0 comment '是否启用，0：否，1：是',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into cm_manager_user (account_num,cpassword,department_id,userType,name,phone,active)values('lxw','e10adc3949ba59abbe56e057f20f883e',1,1,'林型伟','15067179268',1);


create table cm.cm_department_authority(
  id int(8) primary key auto_increment,
  department_name varchar(64) not null default '' comment '部门名称',
  account_page tinyint(4) not null default 0 comment '用户页面操作权限',
  car_page tinyint(4) not null default 0 comment '车辆页面操作权限',
  insurance_page tinyint(4) not null default 0 comment '保险页面操作权限',
  mortgage_page tinyint(4) not null default 0 comment '按揭页面操作权限',
  new_car_page tinyint(4) not null default 0 comment '新车页面操作权限',
  money_page tinyint(4) not null default 0 comment '资金页面操作权限',
  stat_page tinyint(4) not null default 0 comment '统计页面操作权限',
  dossier_page tinyint(4) not null default 0 comment '档案页面操作权限')ENGINE=MyISAM DEFAULT CHARSET=utf8;

insert into cm_department_authority(department_name,account_page,car_page,insurance_page,mortgage_page,new_car_page,stat_page)values('总裁',1,1,1,1,1,1,1);


create table cm.cm_car_record(
  id int(8) primary key auto_increment,
  purchase_date bigint(20) default 0 not null comment '采购日期',
  purchase_money double not null default 0 comment '采购价格',
  deposit double default 0 comment '订金',
  paid_money double not null default 0 comment '已付金额',
  purchase_person varchar(64) not null default '' comment '采购人',
  car_brand varchar(64) not null default '' comment '车辆品牌',
  car_model varchar(64) not null default '' comment '车型',
  key_num varchar(64) not null default '' comment '钥匙编号',
  frame_num varchar(64) default '' comment '车架号',
  car_line tinyint(4) default 0 comment '车系，对应车系表',
  car_level tinyint(4) default 0 comment '车辆级别，对应车辆级别表',
  car_displacement varchar(64) default '' comment '排量',
  car_resource varchar(64) default '' comment '车源所在地',
  car_num_resource varchar(64) default '' comment '车牌所在地',
  car_config varchar(256) default '' comment '车配置',
  car_create_time varchar(64) default '' comment '出厂日期',
  car_purchase_time varchar(64) default '' comment '入户日期',
  car_run_num varchar(64) default '' comment '公里数（万）',
  car_out_colour varchar(64) default '' comment '外观颜色',
  car_inside_colour varchar(64) default '' comment '内饰颜色',
  car_new_sale double default 0 comment '新车指导价',
  car_status tinyint(4) default 0 comment '车况，对应车况表',
  car_status_desc varchar(256) default '' comment '车况描述',
  hall_money double default 0 comment '展厅标价',
  q_authority_money double default 0 comment '全款权限底价',
  a_authority_money double default 0 comment '按揭权限底价',
  inside_person varchar(64) default '' comment '内部合伙人',
  inside_proportion varchar(64) default '' comment '内部合伙比例',
  outside_person varchar(64) default '' comment '外部合伙人',
  outside_proportion varchar(64) default '' comment '外部合伙比例',
  car_channel tinyint(4) default 0 comment '车源，对应车源表',
  car_take_type tinyint(4) default 0 comment '提车方式，对应提车方式表',
  agency_fee double default 0 comment '中介费',
  purchase_type tinyint(4) not null default 0 comment '车辆来源，1：采购，2：寄售',
  sold_date bigint(20) default 0 comment '转已售日期',
  record_status tinyint(4) not null default 0 comment '记录状态，1：采购中，2：车辆入库',
  is_bath tinyint(4) not null default 0 comment '是否批量采购,0：否，1：是',
  bath_id int(8) default 0 comment '批量采购id',
  is_cost tinyint(4) not null default 0 comment '是否录入成本,0：否，1：是',
  cost_id int(8) default 0 comment '对应成本录入表id',
  is_sale tinyint(4) not null default 0 comment '是否销售,0：否，1：是',
  sale_id int(8) default 0 comment '对应销售表id',
  is_sf tinyint(4) not null default 0 comment '是否填写销售费用,0：否，1：是',
  sf_id int(8) default 0 comment '对应销售费用表id',
  gross_profit varchar(64) default 0 comment '毛利润',
  vehicle_premium double default 0 comment '车溢价',
  stock_duration double default 0 comment '库存周期',
  expense_id int(8) default 0 comment '工资辅助表id',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间',
  index index_frame_num(frame_num))ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_remark(
  id int(8) primary key auto_increment,
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  record_type int(8) not null default 0 comment '记录类别',
  remark_date varchar(64) default '' comment '备注日期',
  remark varchar(64) default '' comment '备注',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_car_deposit(
  id int(8) primary key auto_increment,
  sale_person varchar(64) not null default '' comment '销售员',
  deposit_date bigint(20) default 0 not null comment '收订金日期',
  car_model varchar(64) default '' comment '车型',
  car_color varchar(64) default '' comment '颜色',
  car_year varchar(64) default '' comment '年份',
  car_config varchar(256) default '' comment '车配置',
  budget varchar(64) default '' comment '预算',
  give_car_date bigint(20) default 0 comment '预约交车日期',
  deposit double default 0 comment '订金',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_car_property(
  id int(8) primary key auto_increment,
  property_key varchar(64) not null default '' comment '配置关键字',
  property_value varchar(64) not null default '' comment '配置属性值',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

insert into cm_car_property(property_key,property_value)values('CAR_LINE','德系');
insert into cm_car_property(property_key,property_value)values('CAR_LINE','日系');

insert into cm_car_property(property_key,property_value)values('CAR_LEVEL','A级车');
insert into cm_car_property(property_key,property_value)values('CAR_LEVEL','B级车');

insert into cm_car_property(property_key,property_value)values('CAR_CHANNEL','车商');
insert into cm_car_property(property_key,property_value)values('CAR_CHANNEL','个人');

insert into cm_car_property(property_key,property_value)values('CAR_TAKE_TYPE','自提');
insert into cm_car_property(property_key,property_value)values('CAR_TAKE_TYPE','物流');

insert into cm_car_property(property_key,property_value)values('CAR_STATUS','精品');
insert into cm_car_property(property_key,property_value)values('CAR_STATUS','涉水');

create table cm.cm_car_bath(
  id int(8) primary key auto_increment,
  bath_name varchar(64) not null default '' comment '批量采购名称',
  bath_desc varchar(64) not null default '' comment '描述',
  account_num varchar(64) not null default '' comment '添加人',
  car_record_id int(8) default 0 comment '采购记录表ID',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_cost(
  id int(8) primary key auto_increment,
  car_record_id  int(8) not null default 0 comment '采购记录表ID',
  car_pick_person varchar(64) not null default '' comment '提车经办人',
  mention_fee double not null default 0 comment '提档费',
  mention_subsidy double not null default 0 comment '提档补贴',
  travel_fee double not null default 0 comment '差旅费',
  put_fee double not null default 0 comment '入档费',
  put_subsidy double not null default 0 comment '入档补贴',
  crossing_fee double not null default 0 comment '提车过路费',
  mail_fee double not null default 0 comment '邮递费',
  freight_fee double not null default 0 comment '运费',
  billing_fee double not null default 0 comment '提车开票费',
  oil_fee double not null default 0 comment '提车加油费',
  cattle_fee double not null default 0 comment '黄牛费',
  expense_fee double not null default 0 comment '保险费',
  other_fee double not null default 0 comment '其他费用',
  pre_sale_fee double not null default 0 comment '车辆售前整备费用',
  after_sale_fee double not null default 0 comment '车辆售后整备费用',
  other_income_fee double not null default 0 comment '车辆其他收入',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_sf(
  id int(8) primary key auto_increment,
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  transfer_fee double not null default 0 comment '过户费',
  transfer_subsidy double not null default 0 comment '过户补贴',
  transfer_crossing_fee double not null default 0 comment '过户过路费',
  transfer_billing_fee double not null default 0 comment '过户开票费',
  transfer_oil_fee double not null default 0 comment '过户加油费',
  rubbing double not null default 0 comment '其他费用',
  remove_card double not null default 0 comment '拆牌拓印',
  cattle_fee double not null default 0 comment '黄牛费',
  sale_fee double not null default 0 comment '车辆销售整备费用',
  is_produce tinyint(4) not null default 0 comment '是否生售后服务基金，0.否，1.是',
  service_fee double not null default 0 comment '售后服务基金',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_car_sale_setup(
  id int(8) primary key auto_increment,
  car_cost_id  int(8) not null default 0 comment '成本录入表ID',
  setup_type tinyint(4) not null default 0 comment '整备类型：1.售前；2.售后',
  setup_name varchar(64) not null default '' comment '整备项目',
  setup_fee double not null default 0 comment '金额',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_sale_info(
  id int(8) primary key auto_increment,
  sale_person varchar(64) not null default '' comment '销售员',
  sale_date bigint(20) default 0 not null comment '销售日期',
  sale_money double not null default 0 comment '销售价格',
  agency_fee double default 0 comment '中介费',
  unearned_insurance double default 0 comment '预收保险金额',
  pay_money double default 0 comment '应付金额',
  paid_money double default 0 comment '已付金额',
  expense_company varchar(64) default '' comment '保险公司',
  business_expense_fee double default 0 comment '商业保险费',
  force_expense_fee double default 0 comment '强制保险费',
  expense_rebate double default '' comment '保险返点',
  all_unearned_insurance double default 0 comment '总保险金额',
  pay_company_fee double default 0 comment '支付保险公司金额',
  consumer_property tinyint(4) not null default 0 comment '客户属性：关联车辆属性表',
  consumer_resource tinyint(4) not null default 0 comment '获客渠道：关联车辆属性表',
  purchase_use tinyint(4) not null default 0 comment '购车用途：关联车辆属性表',
  consumer_name varchar(64) not null default '' comment '客户姓名',
  consumer_sex tinyint(4) not null default 0 comment '客户性别：1.男，2.女',
  consumer_age tinyint(4) not null default 0 comment '客户年龄段：关联车辆属性表',
  consumer_address varchar(64) not null default '' comment '客户居住地',
  consumer_phone varchar(64) not null default '' comment '电话号码',
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  sale_type tinyint(4) not null default 0 comment '付款方式：1.全款，2.按揭',
  mortgage_id int(8) default 0 comment '按揭表ID',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_paid_record(
  id int(8) primary key auto_increment,
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  record_status tinyint(4) not null default 0 comment '记录状态，1：采购中，2：车辆入库',
  paid_money double default 0 comment '付款金额',
  paid_reason varchar(64) not null default '' comment '付款原因',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_pay_money_assist(
  id int(8) primary key auto_increment,
  car_sale_info_id  int(8) not null default 0 comment '销售信息表ID',
  old_pay_money double default 0 comment '旧的应付金额',
  new_pay_money double default 0 comment '新的应付金额',
  difference double default 0 comment '差值',
  mod_reason varchar(64) not null default '' comment '修改原因',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_mortgage_record(
  id int(8) primary key auto_increment,
  delegate_person varchar(64) default '' comment '委托对象',
  car_brand varchar(64) default '' comment '车辆品牌',
  car_model varchar(64) default '' comment '车型',
  consumer_name varchar(64) default '' comment '客户姓名',
  mortgage_commissioner varchar(64) not null default '' comment '对接按揭专员',
  mortgage_company varchar(64) not null default '' comment '按揭公司',
  loan_fee double not null default 0 comment '贷款金额',
  interest_rate varchar(64) not null default '' comment '利率',
  mortgage_rebate double not null default '' comment '按揭返点',
  back_fee double not null default 0 comment '返还金额',
  assessment_fee double not null default 0 comment '评估费',
  risk_fee double not null default 0 comment '风险金',
  renewal_fee double not null default 0 comment '续保押金',
  pad_fee double not null default 0 comment '垫资费',
  door_fee double not null default 0 comment '上门费',
  stamp_duty double not null default 0 comment '印花税',
  other_fee double not null default 0 comment '其它费用',
  expense_is_company tinyint(4) default 0 comment '保费是否公司支出：0.否，1.是',
  expense_fee double default 0 comment '保费金额',
  expense_rebate varchar(64) default '' comment '保险返点',
  money_back_consumer double default 0 comment '返还客户金额',
  is_mortgage tinyint(4) not null default 0 comment '按揭是否已放款：0.否，1.是',
  mortgage_date bigint(20) default 0 comment '放款日期',
  mortgage_money double default 0 comment '放款金额',
  a_mortgage_money double default 0 comment '已放款金额',
  is_sale tinyint(4) default 0 comment '是否已售按揭：0.否，1.是',
  sale_id int(8) default 0 comment '销售信息Id',
  is_new_car tinyint(4) default 0 comment '是否新车按揭：0.否，1.是',
  new_car_id int(8) default 0 comment '新车表Id',
  display_status tinyint(4) default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_mortgage_log(
  id int(8) primary key auto_increment,
  action_person varchar(64) default '' comment '委托人或业务员',
  consumer_name varchar(64) default '' comment '客户姓名',
  consumer_phone varchar(64) default '' comment '客户联系方式',
  car_brand varchar(64) default '' comment '车辆品牌',
  car_model varchar(64) default '' comment '车型',
  mortgage_commissioner varchar(64) not null default '' comment '按揭专员',
  mortgage_company varchar(64) not null default '' comment '按揭公司',
  loan_fee double not null default 0 comment '贷款金额',
  interest_rate varchar(64) not null default '' comment '利率',
  mortgage_rebate double not null default 0 comment '按揭返点',
  back_fee double not null default 0 comment '返还金额',
  pay_back_fee double default 0 comment '支付委托人返点金额',
  assessment_fee double not null default 0 comment '评估费',
  renewal_fee double not null default 0 comment '续保押金',
  sign_bill_fee double default 0 comment '签单费',
  over_year_fee double default 0 comment '超年限费',
  risk_fee double default 0 comment '风险金',
  pad_fee double default 0 comment '垫资费',
  door_fee double default 0 comment '上门费',
  stamp_duty double default 0 comment '印花税',
  other_fee double default 0 comment '其它费用',
  expense_company varchar(64) default '' comment '保险公司',
  business_expense_fee double default 0 comment '商业保险费',
  force_expense_fee double default 0 comment '强制保险费',
  business_expense_rebate double default 0 comment '商业保险返点',
  business_expense_back double default 0 comment '商业险返点金额',
  mortgage_date bigint(20) default 0 comment '放款日期',
  mortgage_money double default 0 comment '放款金额',
  gross_profit double default 0 comment '毛利润',
  need_pay_consumer double default 0 comment '应支付客户金额',
  agent_pay_fee double default 0 comment '代付金额',
  mortgage_type tinyint(4) default 0 comment '按揭类型：1.代办，2.外扩',
  display_status tinyint(4) default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_insurance(
  id int(8) primary key auto_increment,
  business_person varchar(64) not null default '' comment '业务员',
  car_brand varchar(64) not null default '' comment '车辆品牌',
  car_model varchar(64) not null default '' comment '车型',
  consumer_name varchar(64) not null default '' comment '客户姓名',
  consumer_sex tinyint(4) not null default 0 comment '客户性别：1.男，2.女',
  consumer_birth varchar(64) not null default '' comment '出生日期',
  consumer_phone varchar(64) not null default '' comment '电话号码',
  travel_register bigint(20) default 0 not null comment '行驶证登记日期',
  travel_lssuing bigint(20) default 0 not null comment '行驶证发证日期',
  consumer_type tinyint(4) not null default 0 comment '客户类别：（点选全款、按揭、外拓）',
  renewal_fee double default 0 comment '续保押金',
  deal_renewal tinyint(4) not null default 0 comment '续保押金处理：0.无，1.转收入，2.退还，3.暂存',
  renewal_desc varchar(128) not null default '' comment '备注原因',
  display_status tinyint(4) not null default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_insurance_business(
  id int(8) primary key auto_increment,
  business_type tinyint(4) not null default 0 comment '保险业务类型，对应配置表',
  insurance_date bigint(20) default 0 not null comment '投保时间',
  qzx_date bigint(20) default 0 not null comment '强制险到期时间',
  qzx_company varchar(64) not null default '' comment '强制险保险公司',
  qzx_fee double default 0 comment '强制险保费',
  syx_date bigint(20) default 0 not null comment '商业险到期时间',
  syx_company varchar(64) not null default '' comment '商业险保险公司',
  syx_fee double default 0 comment '商业险保费',
  syx_discount varchar(64) not null default '' comment '商业险折扣',
  expense_rebate varchar(64) default '' comment '保险返点',
  rebate_fee double default 0 comment '返点金额',
  return_fee double default 0 comment '返还客户金额',
  business_desc varchar(128) not null default '' comment '备注信息',
  insurance_id int(8) default 0 comment '保险基础表Id',
  display_status tinyint(4) not null default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_service_fund(
  id int(8) primary key auto_increment,
  use_date bigint(20) default 0 not null comment '使用日期',
  person varchar(64) not null default '' comment '经办人',
  project varchar(64) not null default '' comment '项目',
  fee double not null default 0 comment '金额',
  remark varchar(64) not null default '' comment '备注',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_new_car(
  id int(8) primary key auto_increment,
  car_brand varchar(64) not null default '' comment '车辆品牌',
  car_model varchar(64) not null default '' comment '车型',
  car_config varchar(256) not null default '' comment '车配置',
  guidance_price double default 0 comment '厂家指导价',
  purchase_person varchar(64) not null default '' comment '采购员',
  purchase_money double default 0 comment '采购价格',
  sale_person varchar(64) not null default '' comment '销售员',
  sale_date bigint(20) default 0 comment '销售日期',
  sale_money double default 0 comment '销售价格',
  expense_company varchar(64) not null default '' comment '保险公司',
  business_expense_fee double not null default 0 comment '商业保险费',
  force_expense_fee double not null default 0 comment '强制保险费',
  expense_rebate varchar(64) not null default '' comment '保险返点',
  agency_fee double default 0 comment '中介费',
  pay_money double default 0 comment '应付金额',
  paid_money double default 0 comment '已付金额',
  consumer_property tinyint(4) not null default 0 comment '客户属性：关联车辆属性表',
  consumer_resource tinyint(4) not null default 0 comment '获客渠道：关联车辆属性表',
  purchase_use tinyint(4) not null default 0 comment '购车用途：关联车辆属性表',
  consumer_name varchar(64) not null default '' comment '客户姓名',
  consumer_sex tinyint(4) not null default 0 comment '客户性别：1.男，2.女',
  consumer_age tinyint(4) not null default 0 comment '客户年龄段：关联车辆属性表',
  consumer_address varchar(64) not null default '' comment '客户居住地',
  consumer_phone varchar(64) not null default '' comment '电话号码',
  sale_type tinyint(4) not null default 0 comment '付款方式：1.全款，2.按揭',
  other_cost double default 0 comment '其他成本录入',
  other_income double default 0 comment '其他收入录入',
  mortgage_id int(8) default 0 comment '按揭表ID',
  record_status tinyint(4) not null default 0 comment '记录状态，1：卖出中，2：已售',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_new_car_finance(
  id int(8) primary key auto_increment,
  finance_company varchar(64) not null default '' comment '金融公司',
  car_brand varchar(64) default '' comment '车辆品牌',
  car_model varchar(64) default '' comment '车型',
  car_config varchar(256) not null default '' comment '车配置',
  guidance_price double default 0 comment '厂家指导价',
  down_payments double default 0 comment '首付',
  month_mortgage double default 0 comment '月供',
  service_fee double default 0 comment '服务费',
  other_fee double default 0 comment '其他费用',
  other_cost double default 0 comment '其他成本录入',
  consumer_property tinyint(4) not null default 0 comment '客户属性：关联车辆属性表',
  consumer_resource tinyint(4) not null default 0 comment '获客渠道：关联车辆属性表',
  purchase_use tinyint(4) not null default 0 comment '购车用途：关联车辆属性表',
  consumer_name varchar(64) not null default '' comment '客户姓名',
  consumer_sex tinyint(4) not null default 0 comment '客户性别：1.男，2.女',
  consumer_age tinyint(4) not null default 0 comment '客户年龄段：关联车辆属性表',
  consumer_address varchar(64) not null default '' comment '客户居住地',
  consumer_phone varchar(64) not null default '' comment '电话号码',
  display_status tinyint(4) not null default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_money_manager(
  id int(8) primary key auto_increment,
  action_person varchar(64) not null default '' comment '经办人或项目',
  action_date bigint(20) default 0 not null comment '操作日期或开始日期',
  action_end_date bigint(20) default 0 comment '截至日期',
  action_desc varchar(256) default '' comment '事由',
  action_fee double default 0 comment '金额',
  action_type tinyint(4) default 0 comment '科目：1.支出或贷，2.收入或借',
  money_type tinyint(4) not null default 0 comment '资金类型',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_wages_assist(
  id int(8) primary key auto_increment,
  sale_date bigint(20) default 0 not null comment '销售日期',
  sold_date bigint(20) default 0 not null comment '转已售日期',
  sale_person varchar(64) not null default '' comment '销售人',
  purchase_person varchar(64) not null default '' comment '采购人',
  inside_person varchar(64) not null default '' comment '内部合伙人',
  car_commission double not null default 0 comment '销售提成',
  purchase_commission double not null default 0 comment '采购提成',
  share_dividends double not null default 0 comment '入股分红',
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_wages(
  id int(8) primary key auto_increment,
  staff varchar(64) not null default '' comment '员工',
  pay_month varchar(64) not null default '' comment '月份',
  base_pay double not null default 0 comment '基本工资',
  compassionate_leave double not null default 0 comment '事假',
  late double not null default 0 comment '迟到',
  un_hit_card double not null default 0 comment '未打卡',
  quality_commission double not null default 0 comment '质保提成',
  car_commission double not null default 0 comment '销售提成',
  auth_commission double not null default 0 comment '认证提成',
  deposit_commission double not null default 0 comment '定金提成',
  bill_commission double not null default 0 comment '签单提成',
  insurance_commission double not null default 0 comment '保险提成',
  purchase_commission double not null default 0 comment '采购提成',
  share_dividends double not null default 0 comment '车辆分红',
  new_car_commission double not null default 0 comment '新车销售提成',
  meal_supplement double not null default 0 comment '餐补',
  other_supplement double not null default 0 comment '其他补贴',
  wages double not null default 0 comment '实发工资',
  stime varchar(64) not null default '' comment '开始时间',
  etime varchar(64) not null default '' comment '结束时间',
  sold_time varchar(64) not null default '' comment '转已售截至时间',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table cm.cm_car_dossier(
  id int(8) primary key auto_increment,
  car_record_id int(8) not null default 0 comment '采购记录表ID',
  car_key_num int(8) default 0 comment '钥匙数量',
  car_owner varchar(64) default '' comment '现车主名字',
  car_num varchar(64) default '' comment '现车牌',
  purchase_times tinyint(4) default 0 comment '过户次数',
  car_num_resource varchar(64) default '' comment '现车牌所在地',
  qzx_date bigint(20) default 0 comment '强制险到期时间',
  qzx_person varchar(64) default '' comment '强制险被保险人',
  qzx_company varchar(64) default '' comment '强制险保险公司',
  qzx_address varchar(64) default '' comment '强制险保险公司所在地',
  syx_date bigint(20) default 0 comment '商业险到期时间',
  syx_person varchar(64) default '' comment '商业险被保险人',
  syx_company varchar(64) default '' comment '商业险保险公司',
  syx_address varchar(64) default '' comment '商业险保险公司所在地',
  bill_sign tinyint(4) default 0 comment '签单照片，0：无，1：有',
  register tinyint(4) default 0 comment '登记证书，0：无，1：有',
  driving_license tinyint(4) default 0 comment '行驶证，0：无，1：有',
  break_rule tinyint(4) default 0 comment '违章是否已处理，0：否，1：是',
  qzx_stick tinyint(4) default 0 comment '强制险贴，0：无，1：有',
  car_discharge varchar(64) default '' comment '排放标准',
  annual_trial bigint(20) default 0 comment '年审到期时间',
  insurance_budget double default 0 comment '保险预算',
  car_status tinyint(4) default 0 comment '车辆状态',
  abs varchar(512) default '' comment '摘要',
  display_status tinyint(4) not null default 0 comment '显示状态：1.显示，2.隐藏',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;


create table cm.cm_mortgage_rebate (
  id int(8) primary key auto_increment,
  car_model varchar(64) default '' comment '车型',
  number varchar(64) default '' comment '编号',
  loan_date bigint(20) default 0 comment '贷款日期',
  bill_person varchar(64) default '' comment '签单员',
  loan double default 0 comment '贷款金额',
  interest_rate varchar(64) default '' comment '利率',
  back_money double default 0 comment '返点金额',
  abs varchar(256) default '' comment '摘要',
  ctime bigint(20) default 0 not null comment '创建时间',
  utime bigint(20) default 0 not null comment '更新时间')ENGINE=MyISAM DEFAULT CHARSET=utf8;
