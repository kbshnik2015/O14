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
  (nextval('idSeq'), 'Zheleznodorozhniy', 1),
  (nextval('idSeq'), 'Promishlenniy', 1);

insert into customers values
  (nextval('idSeq'), 'Vladimir', 'Ivanov', 'ivanovlogin', 'ivanovpass', 1, 'Gagarina 41', 1500),
  (nextval('idSeq'), 'Oleg', 'Seleznyov', 'oseleznyov', 'qwerty123', default, null, 1200),
  (nextval('idSeq'), 'Ivan', 'Sidorov', 'ivansid', 'dubai201', 1, null, 1399.99),
  (nextval('idSeq'), 'Vadim', 'Ivlev', 'ivlev2000', 'kort141598', 4, 'Vladimirskaya 114', 150),
  (nextval('idSeq'), 'Nikolay', 'Kuprin', 'kuprinnik', '123qwerty', 1, null, 650);

insert into employees values
  (nextval('idSeq'), 'Alexey', 'Konev', 'emplogin', 'emppass', 'WORKING', false),
  (nextval('idSeq'), 'Artur', 'Starodub', 'artlogin', 'zxcvbn123', 'WORKING', false),
  (nextval('idSeq'), 'Ivan', 'Belenko', 'belenk76', 'beilipe118', 'ON_VACATION', false);

insert into specifications values
  (nextval('idSeq'), 'Unlimited Internet', 599.99, 'Unlimited internet for 599.99 per month', true),
  (nextval('idSeq'), '10GB of Internet', 199.99, '10GB of internet for 199.99 per month. After 30 days period you will lose remains of GBs', false),
  (nextval('idSeq'), '20GB of Internet', 299.99, '20GB of internet for 299.99 per month. After 30 days period you will lose remains of GBs', false),
  (nextval('idSeq'), '30GB of Internet', 359.99, '10GB of internet for 359.99 per month. After 30 days period you will lose remains of GBs', false),
  (nextval('idSeq'), '700 minutes', 450, '700 minutes for outgoing calls for 450 per month', false),
  (nextval('idSeq'), '500 minutes', 350, '500 minutes for outgoing calls for 350 per month', false),
  (nextval('idSeq'), '300 minutes', 250, '300 minutes for outgoing calls for 250 per month', false),
  (nextval('idSeq'), '150 minutes', 150, '150 minutes for outgoing calls for 150 per month', false);

insert into specifications_to_districts values
  (14, 1),
  (14, 2);

insert into services values
  (nextval('idSeq'), null, 14, 'SUSPENDED', 6),
  (nextval('idSeq'), current_date, 16, 'ACTIVE', 6),
  (nextval('idSeq'), current_date, 15, 'ACTIVE', 7),
  (nextval('idSeq'), null, 14, 'PAY_MONEY_SUSPENDED', 6),
  (nextval('idSeq'), current_date, 17, 'ACTIVE', 8),
  (nextval('idSeq'), current_date, 16, 'ACTIVE', 8);

insert into orders values
  (nextval('idSeq'), 6, 11, 16, 23, 'DISCONNECT', 'ENTERING', null),
  (nextval('idSeq'), 6, 11, 14, 22, 'RESTORE', 'IN_PROGRESS', null),
  (nextval('idSeq'), 7, default, 21, default, 'NEW', 'ENTERING', null),
  (nextval('idSeq'), 7, 11, 15, 24, 'NEW', 'COMPLETED', null),
  (nextval('idSeq'), 7, default, 14, default, 'NEW', 'ENTERING', null);