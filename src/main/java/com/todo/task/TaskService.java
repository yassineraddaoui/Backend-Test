package com.todo.task;

import java.util.List;

public interface TaskService {


    List<TaskDto> findAllByCompanyIdAndUserIdAndStatus(long companyId,
                                                       long userId,
                                                       String status,
                                                       int pageNumber,
                                                       int pageSize,
                                                       String sort,
                                                       String order);

    List<TaskDto> findAll();

    List<TaskDto> findAllByCompanyId(Long companyId);

    List<TaskDto> findAllByUserId(Long userId);

    Long saveTask(TaskDto taskDto);

    TaskDto findById(Long taskId);

    void deleteById(Long id);

    Long updateTask(TaskDto taskDto);

    List<TaskDto> findAllByCompanyIdAndUserId(long companyId, long userId);

    void deleteAllByCompanyIdAndUserId(long companyId, long userId);

    Long updateTaskByCompanyId(long companyId, TaskDto taskDto);

    TaskDto findByIdAndCompanyId(Long companyId, Long taskId);

    TaskDto findByIdAndUserId(Long taskId, Long userId);

    void deleteByIdAndUserId(Long taskId, Long userId);

    Long updateTaskByUserId(TaskDto taskDto, long userId);
}
