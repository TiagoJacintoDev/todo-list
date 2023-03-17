package com.todolist.task;

import com.todolist.list.ListModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private ListModel listModel;

    @Column(nullable = false)
    private String name;

    private String description;
    private LocalDate dueDate;

    private String priority;
    private String note;
}