create table if not exists users (
    id bigserial primary key,
    full_name varchar(120) not null,
    email varchar(120) unique not null,
    password varchar(120) not null,
    role varchar(30) not null default 'CUSTOMER'
);

create table if not exists accounts (
    id bigserial primary key,
    user_id bigint not null references users(id) on delete cascade,
    account_number varchar(30) unique not null,
    account_type varchar(30) not null,
    balance numeric(15,2) not null default 0,
    status varchar(20) not null default 'ACTIVE',
    created_at timestamp not null default current_timestamp
);

create table if not exists transactions (
    id bigserial primary key,
    account_id bigint not null references accounts(id) on delete cascade,
    transaction_type varchar(20) not null,
    amount numeric(15,2) not null,
    reference_note varchar(255),
    transaction_time timestamp not null default current_timestamp
);
