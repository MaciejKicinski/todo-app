create table project_steps
(
    id               int primary key auto_increment,
    description      varchar(100) not null,
    days_to_deadline int
);
alter table project_steps
    add column project_id int null;
alter table project_steps
    add foreign key (project_id) references projects (id);

