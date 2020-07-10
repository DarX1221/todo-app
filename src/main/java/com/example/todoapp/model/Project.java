package com.example.todoapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Project {

    private List<TaskGroup> taskGroups;
    private LocalDateTime finalDeadLine;
    private List<LocalDateTime> deadLines;
    private List<Integer> dayToDeadLine;

}
