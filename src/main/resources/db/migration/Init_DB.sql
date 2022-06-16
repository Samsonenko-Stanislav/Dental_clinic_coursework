create table usr (
    id int8 not null,
    active boolean not null,
    password varchar(255),
    username varchar(255),
    employee_id int8,
    outpatient_card_id int8,
    primary key (id)
);

create table appointment (
    id int8 not null,
    active boolean,
    conclusion text,
    date timestamp,
    status varchar(255),
    client_id int8,
    doctor_id int8,
    primary key (id)
);

create table app_check (
    id int8 not null,
    appointment_id int8,
    primary key (id)
);

create table app_check_line (
    id int8 not null,
    price float4 not null,
    qty int4 not null,
    check_id int8,
    good_id int8,
    primary key (id)
);

create table employee (
    id int8 not null,
    full_name varchar(255),
    job_title varchar(255),
    work_end time,
    work_start time,
    primary key (id)
);

create table good (
    id int8 not null,
    is_active boolean not null,
    name varchar(255),
    price float4 not null,
    primary key (id)
);

create table outpatient_card (
    id int8 not null,
    full_name varchar(255),
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

alter table if exists appointment add constraint FKbssqq2v4js7nrx4uqkin7kpip foreign key (client_id) references outpatient_card;
alter table if exists appointment add constraint FKluecmccq4yh46ixfixwuu3m78 foreign key (doctor_id) references employee;
alter table if exists app_check add constraint FKjii5rphoc7eymdm21iokxbqlc foreign key (appointment_id) references appointment;