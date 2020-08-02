package com.example.todoapp.logic;

import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupWriteModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source, null);
    }

    public GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup taskGroup = repository.save(source.toGroup(project));
        return new GroupReadModel(taskGroup);
    }

    public List<GroupReadModel> findAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }


    public void toggleGroup(int groupId) throws IllegalStateException, IllegalArgumentException, NullPointerException {
        if(repository.findById(groupId).equals(Optional.empty())){
            throw new IllegalArgumentException("Task group does'n exist!!!");
        }
        if (!taskRepository.existsByDoneIsFalseAndTasks_Group_ID(groupId)) {
            throw new IllegalStateException("Group has undone tasks!");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Task group does'n exist!"));   // this excption is useless
        result.setDone(!result.isDone());
        //
        //throw new IllegalStateException("Implement existsByDoneIsFalseAndTasks_Group_ID method in SqlTaskRepositroy!!!");
    }



    /*
    To bylo wczesniej

    List<String> temp(TaskGroupRepository repository) {
        return repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
    */

}
