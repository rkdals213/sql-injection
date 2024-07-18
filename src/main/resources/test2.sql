insert into test.test_entity (id, data)
values (1, 'asdasd');
insert into test.test_entity
    (id, data)
values (2, 'asdasd');
insert into test.test_entity (id, data) values (3, 'asdasd');insert into test.test_entity (id, data) values (4, 'asdasd');
update test.test_entity
set data = '11111'
where id = 1;
delete from
           test.test_entity
where id = 2
