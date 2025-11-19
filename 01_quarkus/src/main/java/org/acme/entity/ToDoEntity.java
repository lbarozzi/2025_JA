package org.acme.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.List;
import java.time.LocalDateTime;

@Table(name = "todos")
@Entity
public class ToDoEntity extends PanacheEntity {

    @Column(nullable = false)
    public String title;
    @Column(length = 1000)
    public String description;
    @Column(nullable = false)
    public boolean completed;
    public LocalDateTime lastModifiedAt;
    public LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        lastModifiedAt = LocalDateTime.now();
    }

    public ToDoEntity() {
        createdAt = LocalDateTime.now();
    }

    public ToDoEntity(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public static List<ToDoEntity> findByCompletionStatus(boolean completed) {
        return list("completed", completed);
    }
}