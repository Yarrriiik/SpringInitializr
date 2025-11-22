create table if not exists users (
  id bigserial primary key,
  username varchar(100) unique not null,
  password varchar(200) not null,
  role varchar(30) not null,
  enabled boolean not null default true
);
