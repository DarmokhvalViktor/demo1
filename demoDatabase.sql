# Create database
create database if not exists DemoDB;
use DemoDB;

-- Create table
create table if not exists USER_ACCOUNT
(
    USER_NAME VARCHAR(30) not null,
    GENDER    VARCHAR(1) not null,
    PASSWORD  VARCHAR(30) not null,
    primary key (USER_NAME)
);

-- Create table
create table if not exists PRODUCT
(
    CODE  VARCHAR(20) not null,
    NAME  VARCHAR(128) not null,
    PRICE FLOAT not null,
    primary key (CODE)
) ;


-- Insert data: ---------------------------------------------------------------

insert ignore into user_account (USER_NAME, GENDER, PASSWORD)
values ('tom', 'M', 'tom001');

insert ignore into user_account (USER_NAME, GENDER, PASSWORD)
values ('jerry', 'M', 'jerry001');

insert ignore into product (CODE, NAME, PRICE)
values ('P001', 'Java Core', 100);

insert ignore into product (CODE, NAME, PRICE)
values ('P002', 'C# Core', 90);