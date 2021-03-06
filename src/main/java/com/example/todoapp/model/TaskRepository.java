package com.example.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface TaskRepository {

    //public boolean existsByDoneIsFalse(Integer groupId);

    //Task findTaskByID(Integer id);

    List<Task> findAll();
    Page<Task> findAll(Pageable page);

    Optional<Task> findById(Integer id);

    Task save(Task task);

    boolean existsById(Integer id);


    boolean existsByDoneIsFalseAndTasks_Group_ID(Integer groupId);

    List<Task> findByDone(@Param("state") boolean done);


    List<Task> findAllByTaskGroupId(Integer groupId);
}
