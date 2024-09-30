package com.todo.task;

import lombok.*;

@Builder
@Getter
@Setter
public class TaskDto {
    private Long id;
    private String description;
    private String title;
    private Long ownerId;
    private String status;
}
