create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table employee
(
    id           bigint  not null
        primary key,
    duration_app integer not null,
    full_name    varchar(255),
    job_title    varchar(255),
    work_end     time,
    work_start   time
);

alter table employee
    owner to postgres;

create table good
(
    id     bigint  not null
        primary key,
    active boolean not null,
    name   varchar(255),
    price  real    not null
);

alter table good
    owner to postgres;

create table outpatient_card
(
    id        bigint not null
        primary key,
    full_name varchar(255),
    email     varchar(255),
    gender    varchar(255)
);

alter table outpatient_card
    owner to postgres;

create table appointment
(
    id         bigint not null
        primary key,
    active     boolean,
    conclusion text,
    date       timestamp,
    client_id  bigint
        constraint fkbssqq2v4js7nrx4uqkin7kpip
            references outpatient_card,
    doctor_id  bigint
        constraint fkluecmccq4yh46ixfixwuu3m78
            references employee
);

alter table appointment
    owner to postgres;

create table app_check
(
    id             bigint not null
        primary key,
    appointment_id bigint
        constraint fknth7mp34fxpnikch6879niko2
            references appointment
);

alter table app_check
    owner to postgres;

create table app_check_line
(
    id       bigint  not null
        primary key,
    price    real    not null,
    qty      integer not null,
    check_id bigint
        constraint fk39rjnii8fv6390a6r3fqtpux3
            references app_check,
    good_id  bigint
        constraint fkypq41xjbjmyibkp2x83k967d
            references good
);

alter table app_check_line
    owner to postgres;

create table usr
(
    id                 bigint  not null
        primary key,
    active             boolean not null,
    password           varchar(255),
    username           varchar(255),
    employee_id        bigint
        constraint fkdaisuejo3tsn8e3hvcq0kqwam
            references employee,
    outpatient_card_id bigint
        constraint fkgjhvke8c45f7y59a5o7bupse5
            references outpatient_card
);

alter table usr
    owner to postgres;

create table user_role
(
    user_id bigint not null
        constraint fkfpm8swft53ulq2hl11yplpr5
            references usr,
    roles   varchar(255)
);

alter table user_role
    owner to postgres;

insert into usr(id, username, password, active)
values (1, 'admin', '$2a$08$REVL9IX0UmZZJHP4zXxix.IrMws8ELlYUbmdqbv9AYnYeZoNef.SO', true);
insert into user_role (user_id, roles)
values (1, 'ADMIN');