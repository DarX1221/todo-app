package com.example.todoapp.logic;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import com.example.todoapp.model.TaskRepository;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup taskGroup = repository.save(source.toGroup());
        return new GroupReadModel(taskGroup);
    }

    public List<GroupReadModel> findAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if(taskRepository.existsByDoneIsFalseAndTasks_Group_ID(groupId)) {
            throw new IllegalStateException("Group has undone tasks!");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(
                () -> new IllegalStateException("Task group does'n exist!"));
        result.setDone(!result.isDone());
        throw new IllegalStateException("Implement existsByDoneIsFalseAndTasks_Group_ID method in SqlTaskRepositroy!!!");
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
