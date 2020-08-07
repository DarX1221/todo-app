package com.example.todoapp.model.projection;

import com.example.todoapp.model.Project;
import com.example.todoapp.model.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public GroupWriteModel() {
    }

    public GroupWriteModel(String description, Set<GroupTaskWriteModel> tasks) {
        this.description = description;
        this.tasks = tasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    public TaskGroup toGroup(final Project project) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setDescription(description);
        taskGroup.setTasks(
                tasks.stream()
                .map(source -> source.toTask(taskGroup))
                .collect(Collectors.toSet()));
        taskGroup.setProject(project);
        return taskGroup;
    }
}
