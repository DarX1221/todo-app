package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskGroupService;
import com.example.todoapp.logic.TaskService;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@IllegalExceptionProcessing
@RestController
@RequestMapping("/groups")
public class TaskGroupController {
    public static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService taskGroupService;
    private final TaskRepository taskRepository;
    private TaskService service;


    //public TaskController(@Qualifier("sqlTaskRepository") final TaskRepository taskRepository) {
    public TaskGroupController(TaskGroupService taskGroupService, @Qualifier("sqlTaskRepository") @Lazy final TaskRepository taskRepository, TaskService service) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;
        this.service = service;
    }


    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.info("Custom pageable");
        return ResponseEntity.ok(taskGroupService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByTaskGroupId(id));
    }


    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    // Add Task
    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel groupToCreate) {
        GroupReadModel groupReadModel = taskGroupService.createGroup(groupToCreate);
        return ResponseEntity.created(URI.create("/" + groupReadModel.getId())).body(groupReadModel);
    }



}


