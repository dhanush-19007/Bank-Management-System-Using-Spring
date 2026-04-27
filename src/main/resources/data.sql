insert into users(full_name, email, password, role)
values ('Dhanush Kumar', 'user@bank.com', 'user123', 'CUSTOMER')
on conflict (email) do nothing;

insert into accounts(user_id, account_number, account_type, balance, status)
select id, 'SBIN00012345', 'Savings', 54000.00, 'ACTIVE'
from users where email='user@bank.com'
on conflict (account_number) do nothing;

insert into accounts(user_id, account_number, account_type, balance, status)
select id, 'ICIC00088776', 'Current', 128500.00, 'ACTIVE'
from users where email='user@bank.com'
on conflict (account_number) do nothing;

insert into transactions(account_id, transaction_type, amount, reference_note)
select a.id, 'DEPOSIT', 12000.00, 'Initial account funding'
from accounts a
where a.account_number='SBIN00012345'
and not exists (
    select 1 from transactions t
    where t.account_id=a.id and t.reference_note='Initial account funding'
);

insert into transactions(account_id, transaction_type, amount, reference_note)
select a.id, 'WITHDRAW', 2500.00, 'ATM withdrawal'
from accounts a
where a.account_number='SBIN00012345'
and not exists (
    select 1 from transactions t
    where t.account_id=a.id and t.reference_note='ATM withdrawal'
);

insert into transactions(account_id, transaction_type, amount, reference_note)
select a.id, 'DEPOSIT', 50000.00, 'Business settlement'
from accounts a
where a.account_number='ICIC00088776'
and not exists (
    select 1 from transactions t
    where t.account_id=a.id and t.reference_note='Business settlement'
);
