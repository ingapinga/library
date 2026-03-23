drop schema if exists library cascade;
create schema library;
SET search_path TO library;

create table book (
    book_id serial primary key,
    book_name varchar(1000) not null,
    book_isbn varchar(17) unique not null,
    book_quantity int not null,
    book_available int not null
);
create sequence book_id_gen as integer start with 5 increment by 1;
alter sequence book_id_gen owned by book.book_id;
create index book_name on book(book_name);

create table author (
    author_id int primary key,
    author_name varchar(50) not null,
    author_lastname varchar(50) not null
);
create sequence author_id_gen as integer start with 5 increment by 1;
alter sequence author_id_gen owned by author.author_id;

create table authorbook (
    author_id int references author(author_id),
    book_id int references book(book_id),
    primary key (author_id, book_id)
);

create table reader (
    reader_id int primary key,
    reader_name varchar(50) not null,
    reader_lastname varchar(50) not null,
    reader_phone varchar(11) unique not null
);
create sequence reader_id_gen as integer start with 3 increment by 1;
alter sequence reader_id_gen owned by reader.reader_id;

create table loan (
    loan_id int primary key,
    book_id int not null references book(book_id),
    reader_id int not null references reader(reader_id),
    loan_createddate date not null,
    loan_returndate date,
    loan_status int not null
);
create sequence loan_id_gen as integer start with 5 increment by 1;
alter sequence loan_id_gen owned by loan.loan_id;

/*
create table if not exists TEST (
    id int primary key,
    name varchar(50) not null
);*/