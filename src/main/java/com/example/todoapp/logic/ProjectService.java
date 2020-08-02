package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupTaskWriteModel;
import com.example.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService taskGroupService;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskGroupService taskGroupService,
                          TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.config = config;
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
