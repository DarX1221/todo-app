package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.ProjectRepository;
import com.example.todoapp.model.TaskGroupRepository;
import com.example.todoapp.model.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            ProjectRepository projectRepository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties taskConfigurationProperties,
            TaskGroupService taskGroupService) {
        return new ProjectService(projectRepository, taskGroupRepository, taskGroupService,  taskConfigurationProperties);
    }

    @Bean
    TaskGroupService taskGroupService(
            TaskGroupRepository repository,
            @Qualifier("sqlTaskRepository") TaskRepository taskRepository ) {
        return new TaskGroupService(repository, taskRepository);
    }

}
