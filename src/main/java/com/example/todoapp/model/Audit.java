package com.example.todoapp.model;


import javax.persistence.*;
import java.time.LocalDateTime;


//@Embeddable
@MappedSuperclass
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}


/*
sql
create table projects
(
    id int primary key auto_increment,
    description varchar(100) not null
);
create table project_steps
(
    id int primary key auto_increment,
    description varchar(100) not null,
    days_to_deadline int not null,
    project_id int not null,
    foreign key (project_id) references projects (id)
);
alter table tasks_groups add column project_id int null;
alter table tasks_groups add
    foreign key (project_id) references projects (id);

 */