package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskService;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private TaskService service;

    //public TaskController(@Qualifier("sqlTaskRepository") final TaskRepository taskRepository) {
    public TaskController(@Lazy final TaskRepository taskRepository, TaskService service) {
        this.taskRepository = taskRepository;
        this.service = service;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {

        logger.warn("Exposing all the tasks!");

        return service.finadAllAsync().thenApply(ResponseEntity::ok);
        //return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readTasksByDone(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(taskRepository.findByDone(state));
    }
    //Test
    //@GetMapping("/tasks/{id}")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }
    /*
     */


    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task taskToUpdate) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateFromTask(taskToUpdate);
                    taskRepository.save(taskToUpdate);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));

        return ResponseEntity.noContent().build();
    }

    // Add Task
    @PostMapping
    ResponseEntity<Task> postTask(@RequestBody @Valid Task taskToCreate) {
        Task result = taskRepository.save(taskToCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);

    }

}