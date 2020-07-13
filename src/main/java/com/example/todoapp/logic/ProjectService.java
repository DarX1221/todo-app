package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private ProjectService projectService;

    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskConfigurationProperties config,
                          ProjectService projectService) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.projectService = projectService;
    }


    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(Project projectToSave) {
        return repository.save(projectToSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) throws Exception {
        if(!config.getTemplate().isAllowMultipleTasks() &&
         taskGroupRepository.existsByDoneIsFalseAndTasks_Group_ID(projectId)) {
            throw new IllegalStateException("Only one undone group");
        }
        TaskGroup result2 = repository.findById(projectId)
                .map(project ->{
                        TaskGroup result = new TaskGroup();
                        result.setDescription(project.getDescription());
                        result.setTasks(
                                project.getSteps().stream()
                                .map(step -> new Task(step.getDescription(), deadline.plusDays(step.getDaysToDeadline())))
                                .collect(Collectors.toSet())
                        );
                        return result;
                }).orElseThrow(() -> new IllegalArgumentException("Project with this id is not found"));
        return new GroupReadModel(result2);
    }

}
