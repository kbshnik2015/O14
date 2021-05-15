drop table customers cascade;
drop table employees cascade;
drop table specifications_to_districts cascade;
drop table districts cascade;
drop table specifications cascade;
drop table services cascade;
drop table orders cascade;
drop table order_status;
drop table order_aim;
drop table employee_status;
drop table service_status;
drop sequence idSeq;

create table order_status
(
  name varchar(20) primary key not null
);

create table order_aim
(
  name varchar(20) primary key not null
);

create table employee_status
(
  name varchar(20) primary key not null
);

create table service_status
(
  name varchar(20) primary key not null
);

create table districts
(
  id bigint primary key not null,
  name varchar(20) not null,
  id_parent bigint references districts (id) on delete cascade default null
);

create table customers
(
  id bigint primary key not null,
  first_name varchar(20),
  last_name varchar(30),
  login varchar(20) unique not null,
  password varchar(20) not null,
  district_id bigint references districts (id) on delete restrict default null,
  address varchar (50),
  balance real
);

create table employees
(
  id BIGINT primary key not null,
  first_name varchar(20),
  last_name varchar(30),
  login varchar(20) unique not null,
  password varchar(20) not null,
  status varchar(20) references employee_status (name) on delete restrict,
  is_waiting boolean
);

create table specifications
(
  id BIGINT primary key not null,
  name varchar(30) not null,
  price real not null,
  description varchar(100),
  is_address_depended boolean
);

create table specifications_to_districts
(
  specification_id bigint references specifications (id) on delete cascade,
  district_id bigint references districts (id) on delete cascade,
  unique (specification_id, district_id)
);

create table services
(
  id bigint primary key not null,
  pay_day date,
  specification_id bigint references specifications (id) on delete cascade,
  status varchar(20) references service_status (name) on delete restrict,
  customer_id bigint references customers (id) on delete cascade
);

create table orders
(
  id bigint primary key not null,
  customer_id bigint references customers (id) on delete cascade,
  employee_id bigint references employees (id) default null,
  specification_id bigint references specifications (id) on delete cascade,
  service_id bigint references services (id) default null,
  aim varchar(20) references order_aim (name) on delete restrict,
  status varchar(20) references order_status (name) on delete restrict,
  address varchar (50)
);

create sequence idSeq as BIGINT;

insert into order_status values
  ('IN_PROGRESS'), ('SUSPENDED'), ('COMPLETED'), ('ENTERING'), ('CANCELLED');

insert into order_aim values
  ('NEW'), ('SUSPEND'), ('RESTORE'), ('DISCONNECT');

insert into employee_status values
  ('WORKING'), ('ON_VACATION'), ('RETIRED');

insert into service_status values
  ('ACTIVE'), ('DISCONNECTED'), ('PAY_MONEY_SUSPENDED'), ('SUSPENDED');

insert into districts values
  (nextval('idSeq'), 'Samara', default),
  (nextval('idSeq'), 'Togliatti', default),
  (nextval('idSeq'), 'Sovetskiy', 1),
  (nextval('idSeq'), 'Mashstroy', 3),
  (nextval('idSeq'), 'Promishlenniy', 1);

insert into customers values
  (nextval('idSeq'), 'Vladimir', 'Ivanov', 'ivanovlogin', 'ivanovpass', default, null, 500),
  (nextval('idSeq'), 'Rassul', 'Biskek_Ogly', 'rassullogin', 'pass', default, 'Tadjikistan', 200),
  (nextval('idSeq'), 'Ivan', 'Stukachev', 'stukachlogin', 'pass', default, null, 300);

insert into employees values
  (nextval('idSeq'), 'Rabotnik', 'Rabotnikov', 'emplogin', 'emppass', 'WORKING', false),
  (nextval('idSeq'), 'Artur', 'Arturov', 'artlogin', 'pass', 'WORKING', false),
  (nextval('idSeq'), 'Ivan', 'Durakov', 'durlogin', 'pass', 'ON_VACATION', false);

insert into specifications values
  (nextval('idSeq'), 'Internet100', 100, 'Internet for 100 per month', true),
  (nextval('idSeq'), 'Internet200', 200, null, false),
  (nextval('idSeq'), '600minutes', 150, '600 minutes for 150 per month', false),
  (nextval('idSeq'), '500 minutes', 120, null, false);

insert into specifications_to_districts values
  (12, 1),
  (12, 2);

insert into services values
  (nextval('idSeq'), null, 12, 'SUSPENDED', 6),
  (nextval('idSeq'), current_date, 14, 'ACTIVE', 6),
  (nextval('idSeq'), current_date, 13, 'ACTIVE', 7),
  (nextval('idSeq'), null, 12, 'PAY_MONEY_SUSPENDED', 6),
  (nextval('idSeq'), current_date, 15, 'ACTIVE', 8),
  (nextval('idSeq'), current_date, 14, 'ACTIVE', 8);

insert into orders values
  (nextval('idSeq'), 6, default, 14, 17, 'DISCONNECT', 'ENTERING', null),
  (nextval('idSeq'), 6, 9, 12, 16, 'RESTORE', 'IN_PROGRESS', null),
  (nextval('idSeq'), 7, default, 15, default, 'NEW', 'ENTERING', null),
  (nextval('idSeq'), 7, 9, 13, 18, 'NEW', 'COMPLETED', null),
  (nextval('idSeq'), 7, default, 12, default, 'NEW', 'ENTERING', null);