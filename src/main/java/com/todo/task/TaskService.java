package com.todo.task;

import java.util.List;

public interface TaskService {

    List<TaskDto> findAllByCompanyIdAndUserIdAndStatus(Long companyId,
                                                       Long userId,
                                                       String status,
                                                       int pageNumber,
                                                       int pageSize,
                                                       String sort,
                                                       String order);

    List<TaskDto> findAll(int pageNumber, int pageSize, String sort, String order);

    List<TaskDto> findAllByCompanyId(Long companyId, int pageNumber, int pageSize, String sort, String order);

    List<TaskDto> findAllByUserId(Long userId, int pageNumber, int pageSize, String sort, String order);

    Long saveTask(TaskDto taskDto);

    TaskDto findById(Long taskId);

    void deleteById(Long id);

    Long updateTask(TaskDto taskDto);

    List<TaskDto> findAllByCompanyIdAndUserId(Long companyId, Long userId, int pageNumber, int pageSize, String sort, String order);

    void deleteAllByCompanyIdAndUserId(Long companyId, Long userId);

    Long updateTaskByCompanyId(Long companyId, TaskDto taskDto);

    TaskDto findByIdAndCompanyId(Long companyId, Long taskId);

    TaskDto findByIdAndUserId(Long taskId, Long userId);

    void deleteByIdAndUserId(Long taskId, Long userId);

    Long updateTaskByUserId(TaskDto taskDto, Long userId);
}
