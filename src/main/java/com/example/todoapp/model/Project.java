package com.example.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Project's descripton can't be empty!")
    private String description;

    @OneToMany
    @JoinColumn(name = "project_id")
    private Set<TaskGroup> grups;


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskGroup> getGrups() {
        return grups;
    }

    public void setGrups(final Set<TaskGroup> grups) {
        this.grups = grups;
    }
}
