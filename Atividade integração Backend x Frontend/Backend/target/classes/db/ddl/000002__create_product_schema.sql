create sequence seq_product_id cache 10;

create table product
(
    id                int primary key not null default nextval('seq_product_id'),
    name              varchar(30)     not null,
    description       varchar(100)    not null,
    initial_inventory decimal(16, 3)  not null default 0.0
);