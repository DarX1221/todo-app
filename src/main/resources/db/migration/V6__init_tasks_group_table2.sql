alter table tasks add column tasks_group_id int null;
alter table tasks
    add foreign key (tasks_group_id) references tasks (id);
