create database if not exists genesis;

use genesis;

create table if not exists users (
    id bigint primary key auto_increment,
    name varchar(255) not null,
    surname varchar(255) not null,
    person_id varchar(50) not null unique,
    uuid varchar(255) not null unique
);

insert into users (person_id, name, surname, uuid) values ('jXa4g3H7oPq2', 'Anna', 'Chalupníková', 'c02a0c9c-b61e-41bd-865d-ac7b853f0392');
insert into users (person_id, name, surname, uuid) values ('yB9fR6tK0wLm', 'Konrád', 'Blažek', 'a588ff36-bbb1-47a2-af86-d3d98fe71e52');
insert into users (person_id, name, surname, uuid) values ('cN1vZ8pE5sYx', 'Milan', 'Slovák', '7132dc02-8aae-4736-b720-a468543fbbde');
insert into users (person_id, name, surname, uuid) values ('tQdG2kP3mJfB', 'Vilemína', 'Kovářová', 'e6c860dd-e79d-4d32-8bde-0d73f7c314e0');
insert into users (person_id, name, surname, uuid) values ('iM5sO6zXcW7v', 'Vanda', 'Skálová', 'e2361069-2ce6-4bbc-b400-46eebb6a54b6');
insert into users (person_id, name, surname, uuid) values ('rU8nA9eT2bYh', 'Patrik', 'Stárek', 'f9c0be9f-84a0-44a6-b4ab-586b1576ba67');
insert into users (person_id, name, surname, uuid) values ('wV6eH1fK7qZj', 'Klaudie', 'Marešová', 'bce5eb18-3caa-4add-88c1-a9cca5174377');
insert into users (person_id, name, surname, uuid) values ('sL4gN9dC3bXz', 'Brigita', 'Navrátilová', '0c5b20bb-9b0a-4b93-9e74-24344039fd23');
insert into users (person_id, name, surname, uuid) values ('kR0aZ7vW2nDl', 'Augustin', 'Ševčík', 'a28dc22a-a247-4c1e-86b0-94a9d86aeedb');
insert into users (person_id, name, surname, uuid) values ('eI1oY6tQ9dKj', 'Miluše', 'Skálová', '043149f3-f202-451b-8046-f50fc727bd5e');
