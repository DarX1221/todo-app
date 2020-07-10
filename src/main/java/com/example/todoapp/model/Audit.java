package com.example.todoapp.model;


import javax.persistence.*;
import java.time.LocalDateTime;


//@Embeddable
@MappedSuperclass
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}
