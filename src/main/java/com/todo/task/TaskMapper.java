package com.todo.task;

import com.todo.user.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskDto task) {
        return
                Task.builder()
                        .title(task.getTitle())
                        .status(TaskStatus.valueOf(task.getStatus()))
                        .description(task.getDescription())
                        .owner(User.builder()
                                .id(task.getOwnerId())
                                .build())
                        .build();
    }

    public TaskDto toTaskDto(Task task) {
        return
                TaskDto.builder()
                        .id(task.getId())
                        .description(task.getDescription())
                        .title(task.getTitle())
                        .ownerId(task.getOwner().getId())
                        .status(task.getStatus().name())
                        .build();
    }
}
