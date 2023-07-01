create table if not exists employee
(
    id  bigserial  not null
        primary key,
    duration_app integer not null,
    full_name    varchar(255),
    job_title    varchar(255),
    work_end     time,
    work_start   time
);

create table if not exists good
(
    id  bigserial not null
        primary key,
    active boolean not null,
    name   varchar(255),
    price  real    not null
);

create table if not exists outpatient_card
(
    id  bigserial not null
        primary key,
    full_name varchar(255),
    email     varchar(255),
    gender    varchar(255)
);

create table if not exists appointment
(
    id  bigserial not null
        primary key,
    active     boolean,
    conclusion text,
    date       timestamp,
    client_id  bigint
            references outpatient_card,
    doctor_id  bigint,
    foreign key (doctor_id) references employee(id)
);

create table if not exists app_check
(
    id  bigserial not null
        primary key,
    appointment_id bigint,
    foreign key (appointment_id) references appointment(id)
);

create table if not exists app_check_line
(
    id bigserial  not null
        primary key,
    price    real    not null,
    qty      integer not null,
    check_id bigint,
    good_id  bigint,
    foreign key (check_id) references app_check(id),
    foreign key (good_id) references good(id)
);

create table if not exists usr
(
    id  bigserial not null
        primary key,
    active             boolean not null,
    password           varchar(255),
    username           varchar(255),
    employee_id        bigint,
    outpatient_card_id bigint,
    foreign key (employee_id) references employee(id),
    foreign key (outpatient_card_id) references outpatient_card(id)
);

create table if not exists user_role
(
    user_id bigint,
    roles   varchar(255),
    foreign key (user_id) references usr(id)
);

create table if not exists work_days
(
    employee_id bigint,
    work_days   varchar(255),
    foreign key (employee_id) references employee(id)
);

insert into employee (duration_app, full_name, job_title, work_start, work_end) VALUES
    (30, 'Иванов Иван Иванович', 'Стоматолог-терапевт', '09:00:00', '18:00:00');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Петров Петр Петрович', 'patient@mailforspam.com', 'MALE');

insert into usr(username, password, active, employee_id, outpatient_card_id)
values ('admin', '$2y$10$1ALFTjjpKspNC0T2oE16PuiWa/lXaPWflAhJmcLx2bGGkpUNdzlty', true, null, null),
       ('doctor', '$2y$10$8VRppPIWIEGupCSpsPN8Ru0EWaJjALsX1WdQwrHcJpiLogBKhuoPu', true, 1, null),
       ('patient', '$2y$10$PKd1aLtZDWnFVl0egpPYWOzf7r4dacTmRYO8cCzY93BVjs2KoNoDK', true, null, 1);

insert into user_role (user_id, roles)
values (1, 'ADMIN'), (2, 'DOCTOR'), (3, 'USER');

INSERT INTO good (active, name, price) VALUES
    (true, 'Первичный прием', 2000.0),
    (true, 'Повтроный прием', 1500.0),
    (true, 'Удаление зуба', 2500.0),
    (true, 'Анестезия', 500.0);

insert into work_days(employee_id, work_days)
VALUES  (1, 'MONDAY'),
        (1, 'TUESDAY'),
        (1, 'WEDNESDAY'),
        (1, 'THURSDAY'),
        (1, 'FRIDAY');
