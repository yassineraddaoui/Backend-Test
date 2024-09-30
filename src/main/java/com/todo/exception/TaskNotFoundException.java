package com.todo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String taskNotFound) {
        super(taskNotFound);
    }
}
