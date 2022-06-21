insert into usr (id, username, password, active)
values (1, 'admin', '$2a$08$REVL9IX0UmZZJHP4zXxix.IrMws8ELlYUbmdqbv9AYnYeZoNef.SO', true);

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');