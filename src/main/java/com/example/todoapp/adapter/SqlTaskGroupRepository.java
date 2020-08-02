package com.example.todoapp.adapter;

import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

    @Override
    @Query("select distinct tg from TaskGroup tg join fetch tg.tasks")
    List<TaskGroup> findAll();

    @Override
    @Query(nativeQuery = true, value = "select done from tasks where done=false and tasks_group_Id=:groupId group by tasks_group_id")
    boolean existsByDoneIsFalseAndTasks_Group_ID(@Param("groupId") Integer groupId);


}
