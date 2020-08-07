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
    public String description;

    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskGroup> getGrups() {
        return groups;
    }

    public void setGrups(final Set<TaskGroup> grups) {
        this.groups = grups;
    }

    public Set<ProjectStep> getSteps() {
        System.out.println("--- wywolano getSteps()");
        return steps;
    }

    public void setSteps(final Set<ProjectStep> steps) {
        System.out.println("--- wywolano setSteps()");
        this.steps = steps;
    }



    //      Test
    public static void main(String[] args) {

    }
}


//      <dl th:each="project : ${projects}" class="Bd P(10px)">