package com.example.todoapp.model.projection;

import com.example.todoapp.model.Project;
import com.example.todoapp.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    @NotBlank(message = "description can't be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps = new ArrayList<>();


    public ProjectWriteModel() {
        steps.add(new ProjectStep());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    Project toProject() {
        Project project = new Project();
        project.setDescription(description);
        steps.forEach(step -> step.setProject(project));
        project.setSteps(new HashSet<>(steps));
        return project;
    }
}
