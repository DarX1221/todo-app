package com.example.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task's description must be not null")
    private String description;
    private boolean done;
    private LocalDateTime deadline;


    //@Transient
    @ManyToOne
    @JoinColumn(name = "tasks_group_id")
    private TaskGroup taskGroup;

    //@Embedded
    //Audit audit= new Audit();
    /*
    */


    public Task() { }

    public Task(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


    TaskGroup getTaskGroup() {
        return taskGroup;
    }

    void setTaskGroup(TaskGroup taskGroup) {
        this.taskGroup = taskGroup;
    }
    /*
     */

    public void updateFromTask(Task task) {
        this.done = task.done;
        this.description = task.description;
        this.deadline = task.deadline;
        this.taskGroup = task.taskGroup;
    }

}





/*

package com.example.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="tasks_groups")
public class TaskGroup extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task group's description must be not null")
    private String description;
    private boolean done;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskGroup")
    Set<Task> tasks;

    //@Embedded
    //Audit audit= new Audit();



    public TaskGroup() {

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }
}


 */
