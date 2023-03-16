package com.todolist.list;

import java.io.Serializable;

/**
 * A DTO for the {@link ListModel} entity
 */
public record ListDto(String name) implements Serializable {
}