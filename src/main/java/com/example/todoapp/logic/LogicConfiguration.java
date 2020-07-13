package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.ProjectRepository;
import com.example.todoapp.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            ProjectRepository projectRepository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties taskConfigurationProperties) {
        return new ProjectService(projectRepository, taskGroupRepository, taskConfigurationProperties);
    }
}
