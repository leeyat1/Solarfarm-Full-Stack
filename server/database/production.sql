
drop database if exists solar_farm;
create database solar_farm;
use solar_farm;

create table user (
    id int primary key auto_increment,
    username varchar(255) not null,
    password varchar(255) not null
);

create table solar_panel (
	id int primary key auto_increment,
	section varchar(100) not null,
	`row` int not null,
    `column` int not null,
    year_installed int not null,
    material varchar(10) not null,
    is_tracking bit not null,
    user_id int not null,
    constraint fk_solar_panel_user_id
		foreign key (user_id)
        references user(id)
);

insert into user (username, password) values
	("test@test.com", "password"),
    ("test2@test.com", "password");

insert into solar_panel (section, `row`, `column`, year_installed, material, is_tracking, user_id)
	values
    ('The Ridge', 1, 1, 2020, 'POLY_SI', true, 1),
    ('The Ridge', 1, 2, 2019, 'MONO_SI', true, 2),
    ('Flats', 1, 1, 2017, 'A_SI', true, 1),
    ('Flats', 2, 6, 2017, 'CD_TE', true, 1),
    ('Flats', 3, 7, 2000, 'CIGS', false, 2);