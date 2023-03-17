package com.todolist.task;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link TaskModel} entity
 */
public record TaskDto(String name, String description, LocalDate dueDate, Priority priority,
                           String note) implements Serializable {
}