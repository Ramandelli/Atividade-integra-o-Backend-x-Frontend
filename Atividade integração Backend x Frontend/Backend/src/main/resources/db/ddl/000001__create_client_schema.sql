create sequence seq_client_id cache 10;

create table client
(
    id        int primary key not null default nextval('seq_client_id'),
    firstname varchar(15)     not null,
    lastname  varchar(15)     not null,
    document  char(11)        not null,
    birth     date            not null
);