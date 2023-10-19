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

insert into work_days(employee_id, work_days)
VALUES  (1, 'MONDAY'),
        (1, 'TUESDAY'),
        (1, 'WEDNESDAY'),
        (1, 'THURSDAY'),
        (1, 'FRIDAY');


insert into employee (duration_app, full_name, job_title, work_start, work_end) VALUES
    (60, 'Демидов Дмитрий Сергеевич', 'Стоматолог-хирург', '13:00:00', '18:00:00');

insert into work_days(employee_id, work_days)
VALUES  (2, 'FRIDAY'),
        (2, 'SATURDAY'),
        (2, 'SUNDAY');

insert into employee (duration_app, full_name, job_title, work_start, work_end) VALUES
    (45, 'Сергеева Ольга Валерьевна', 'Стоматолог-ортопед', '14:00:00', '20:00:00');

insert into employee(duration_app, full_name, job_title, work_start, work_end) VALUES
   (30, 'Петрунина Анастасия Андреевна', 'Ортодонт', '15:00:00', '21:00:00');

insert into work_days(employee_id, work_days)
VALUES (4, 'MONDAY'),
       (4, 'WEDNESDAY'),
       (4, 'FRIDAY');



insert into outpatient_card (full_name, email, gender) VALUES
    ('Петров Петр Петрович', 'patient@mailforspam.com', 'MALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Алеексеев Алексей Алексеевич', 'patient2@mailforspam.com', 'MALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Романова Ольга Васильевна', 'romanova@mailforspam.com', 'FEMALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Шолохов Тимур Геннадьевич', 'sholohov1978@mailforspam.com', 'MALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Федоров Федр Романович', 'fedorov@mailforspam.com', 'MALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Федорова Маргарита Федоровна', 'patient@mailforspam.com', 'MALE');

insert into outpatient_card (full_name, email, gender) VALUES
    ('Петрунина Анастасия Андреевна', 'petrunina_A@mailforspam.com', 'FEMALE');

insert into usr(username, password, active, employee_id, outpatient_card_id)
values ('admin', '$2y$10$1ALFTjjpKspNC0T2oE16PuiWa/lXaPWflAhJmcLx2bGGkpUNdzlty', true, null, null),
       ('doctor', '$2y$10$8VRppPIWIEGupCSpsPN8Ru0EWaJjALsX1WdQwrHcJpiLogBKhuoPu', true, 1, null),
       ('patient', '$2y$10$PKd1aLtZDWnFVl0egpPYWOzf7r4dacTmRYO8cCzY93BVjs2KoNoDK', true, null, 1),

       ('demidov', '$2y$10$MK6XPXiqLtrXmR6uvi8qDuRYCDRcDsDEEax7V202cR1VTCgkvCHkO', true, 2, null),
       ('sergeeva', '$2y$10$JZPHu4y1dcQwub0XgdtxC.zMdmS0G1xb/EMwCGjL3ROSzryMVzXem', true, 3, null),

       ('petrunina', '$2y$10$KQ5Mm3sIFhkOOMpeYCQ2/us/MMyNpWbV3SM7BXY547ns0afDclEE6', true, 4, 7),

       ('alex', '$2y$10$tMPQdnTsasYRCczXGkkqCOJ.ScLF/NYxwkCVqaRQA3bA7mdBjtlG.', true, null,  2),
       ('romanova', '$2y$10$a4VJ/Ejq6Hvui2c7x2oxYuQgSo20v6J9QtbCJeSpcpHAOqrqNBfyi', true, null, 3),
       ('sholohov', '$2y$10$n.9iB5OoRqSKz6IGEloQqefIAp6Avn9.YWDCEVt1l6RzpGoowEN7G', true, null, 4),
       ('feodorov', '$2y$10$nh0Ns42dt1i3EAUfHaHjaOB9/76WnARCyPv20RqT7EvbAOlA68njm', true, null, 5),
       ('feodorova', '$2y$10$PL1Wc4TXdMHIuOSb74EUf.26wCNW81ucDnz22pZAHmb9DD9zxJ4By', true, null, 6);


insert into user_role (user_id, roles)
values (1, 'ADMIN'),
       (2, 'DOCTOR'),
       (3, 'USER'),
       (4, 'DOCTOR'),
       (5, 'DOCTOR'),
       (6, 'DOCTOR'),
       (6, 'USER'),
       (7, 'USER'),
       (8, 'USER'),
       (9, 'USER'),
       (10, 'USER'),
       (11, 'USER');


INSERT INTO good (active, name, price) VALUES
    (true, 'Первичный прием', 2000.0),
    (true, 'Повтроный прием', 1500.0),
    (true, 'Удаление зуба', 2500.0),
    (true, 'Анестезия', 500.0),
    (true, 'Установка пломбы', 3000);



-- INSERT INTO appointment(active, conclusion, date, client_id, doctor_id)
-- VALUES (false, 'Установлена пломба', '2023-06-30 13:00:00', 1,  1),
--        (true, null, '2023-07-07 09:00:00', 1, 1),
--        (true, null, '2023-07-07 13:00:00', 1, 2),
--        (true, null, '2023-07-07 14:00:00', 2, 2),
--        (true, null, '2023-07-07 15:00:00', 3, 2),
--        (true, null, '2023-07-07 16:00:00', 4, 2),
--        (true, null, '2023-07-07 17:00:00', 7, 2),
--        (false, 'Удалены 2 зуба. Полоскать ротовую полость Мирамистинов в течении 7 дней.', '2023-07-02 14:00:00', 7,2),
--        (false, 'Профилактический прием.', '2023-06-30 15:00:00', 1,4),
--        (true, null, '2023-07-07 15:00:00', 1, 4);


--insert into app_check(appointment_id)
--values (1), (8), (9);

--insert into app_check_line(price, qty, check_id, good_id)

--values (2000, 1, 1, 1),
--       (3000, 1, 1, 5),
--       (2000, 1, 2, 1),
--       (500, 1, 2, 4),
--       (2500, 2, 2, 3),
--       (2000, 1, 3, 1);