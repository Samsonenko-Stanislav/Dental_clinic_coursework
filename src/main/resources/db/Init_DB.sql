CREATE TABLE appointment
(
    id         BIGINT NOT NULL,
    client_id  BIGINT,
    doctor_id  BIGINT,
    date       TIMESTAMP WITHOUT TIME ZONE,
    active     BOOLEAN,
    conclusion VARCHAR(255),
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES outpatient_card (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES employee (id);
CREATE TABLE app_check
(
    id             BIGINT NOT NULL,
    appointment_id BIGINT,
    CONSTRAINT pk_app_check PRIMARY KEY (id)
);

ALTER TABLE app_check
    ADD CONSTRAINT FK_APP_CHECK_ON_APPOINTMENT FOREIGN KEY (appointment_id) REFERENCES appointment (id);
CREATE TABLE app_check_line
(
    id       BIGINT  NOT NULL,
    check_id BIGINT,
    good_id  BIGINT,
    qty      INTEGER NOT NULL,
    price    FLOAT   NOT NULL,
    CONSTRAINT pk_app_check_line PRIMARY KEY (id)
);

ALTER TABLE app_check_line
    ADD CONSTRAINT FK_APP_CHECK_LINE_ON_CHECK FOREIGN KEY (check_id) REFERENCES app_check (id);

ALTER TABLE app_check_line
    ADD CONSTRAINT FK_APP_CHECK_LINE_ON_GOOD FOREIGN KEY (good_id) REFERENCES good (id);
CREATE TABLE employee
(
    id         BIGINT NOT NULL,
    full_name  VARCHAR(255),
    job_title  VARCHAR(255),
    work_start TIME WITHOUT TIME ZONE,
    work_end   TIME WITHOUT TIME ZONE,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);
CREATE TABLE good
(
    id     BIGINT  NOT NULL,
    active BOOLEAN NOT NULL,
    name   VARCHAR(255),
    price  FLOAT   NOT NULL,
    CONSTRAINT pk_good PRIMARY KEY (id)
);
CREATE TABLE outpatient_card
(
    id        BIGINT NOT NULL,
    full_name VARCHAR(255),
    CONSTRAINT pk_outpatientcard PRIMARY KEY (id)
);
CREATE TABLE user_role
(
    user_id BIGINT NOT NULL,
    roles   VARCHAR(255)
);

CREATE TABLE usr
(
    id                 BIGINT  NOT NULL,
    username           VARCHAR(255),
    password           VARCHAR(255),
    active             BOOLEAN NOT NULL,
    employee_id        BIGINT,
    outpatient_card_id BIGINT,
    CONSTRAINT pk_usr PRIMARY KEY (id)
);

ALTER TABLE usr
    ADD CONSTRAINT FK_USR_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE usr
    ADD CONSTRAINT FK_USR_ON_OUTPATIENT_CARD FOREIGN KEY (outpatient_card_id) REFERENCES outpatient_card (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES usr (id);