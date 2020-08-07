package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.adapter.SqlProjectRepository;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupTaskWriteModel;
import com.example.todoapp.model.projection.GroupWriteModel;
import com.example.todoapp.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService taskGroupService;
    private TaskConfigurationProperties config;


    public ProjectService(final ProjectRepository repository,
                          final TaskGroupRepository taskGroupRepository,
                          TaskGroupService taskGroupService,
                          TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.config = config;
    }


    public List<Project> readAll() {
        List<Project> list = repository.findAll();
        list.forEach(project -> System.out.println(project.getId() + ":  " + project.description));
        return list;



        // testing return repository.findAll();
    }
/*
    public Project save(Project projectToSave) {
        return repository.save(projectToSave);
    }*/
    public Project save(ProjectWriteModel projectToSave) {
        Project project = projectToSave.toProject();
        System.out.println("description:" + project.description);
        System.out.println("Id:" + project.getId());
        System.out.println("Steps:" + project.getSteps());
        Set<ProjectStep> steps = project.getSteps();
        steps.forEach(projectStep -> System.out.println(projectStep.getDescription()));
        return repository.save(project);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) throws Exception {
        if(!config.getTemplate().isAllowMultipleTasks() &&
         taskGroupRepository.existsByDoneIsFalseAndTasks_Group_ID(projectId)) {
            throw new IllegalStateException("Only one undone group");
        }
        GroupReadModel result2 = repository.findById(projectId)
                .map(project ->{
                        GroupWriteModel result = new GroupWriteModel();
                        result.setDescription(project.getDescription());
                        result.setTasks(
                                project.getSteps().stream()
                                .map(step ->{
                                    GroupTaskWriteModel task = new GroupTaskWriteModel();
                                    task.setDescription(step.getDescription());
                                    task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                    return task;
                                }
                                ).collect(Collectors.toSet())
                        );
                        return taskGroupService.createGroup(result, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with this id is not found"));
        return result2;
    }

}
