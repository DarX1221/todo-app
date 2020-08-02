package com.example.todoapp.adapter;

import com.example.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.todoapp.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);


    @Override
    @Query(nativeQuery = true, value = "select done from tasks where done=false and tasks_group_Id=:groupId group by tasks_group_id")
    boolean existsByDoneIsFalseAndTasks_Group_ID(@Param("groupId") Integer groupId);

    @Override
    List<Task> findAllByTaskGroupId(Integer groupId);
}
