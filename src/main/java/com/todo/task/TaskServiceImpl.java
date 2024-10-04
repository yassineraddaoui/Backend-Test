package com.todo.task;

import com.todo.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper mapper;
    private final TaskRepository taskRepository;


    public List<TaskDto> findAllByCompanyIdAndUserIdAndStatus(long companyId,
                                                              long userId,
                                                              String status,
                                                              int pageNumber,
                                                              int pageSize,
                                                              String sort,
                                                              String order) {
        Specification<Task> spec = Specification.where(
                TaskSpecefication.hasCompanyId(companyId)
                        .and(TaskSpecefication.hasTaskStatus(status.toUpperCase()))
                        .and(TaskSpecefication.hasUserId(userId))
        );

        return taskRepository.findAll(
                        spec,
                        PageRequest.of(pageNumber, pageSize,
                                Sort.Direction.valueOf(order.toUpperCase()), sort)
                ).stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> findAll() {
        try {
            var tasks = this.taskRepository.findAll();
            return tasks.stream()
                    .map(mapper::toTaskDto)
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<TaskDto> findAllByCompanyId(Long companyId) {
        return taskRepository.findAllByOwnerCompany_Id(companyId)
                .stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> findAllByUserId(Long userId) {
        return taskRepository.findAllByOwner_Id(userId)
                .stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public Long saveTask(TaskDto taskDto) {
        var task = this.mapper.toTask(taskDto);
        return this.taskRepository.save(task).getId();
    }

    public TaskDto findById(Long taskId) {
        return this.taskRepository.findById(taskId)
                .map(mapper::toTaskDto)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskId));
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("No Task found with the ID::" + id);
        }
        taskRepository.deleteById(id);
    }

    public Long updateTask(TaskDto taskDto) {
        taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskDto.getId()));
        var task = this.mapper.toTask(taskDto);
        taskRepository.save(task);
        return task.getId();
    }

    public List<TaskDto> findAllByCompanyIdAndUserId(long companyId, long userId) {
        return taskRepository.findAllByOwnerCompany_IdAndOwner_Id(companyId, userId)
                .stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public void deleteAllByCompanyIdAndUserId(long companyId, long userId) {
        taskRepository.deleteAllByOwnerCompany_IdAndOwner_Id(companyId, userId);
    }

    public Long updateTaskByCompanyId(long companyId, TaskDto taskDto) {
        taskRepository.findByIdAndOwnerCompany_Id(taskDto.getId(), companyId)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskDto.getId()));
        var task = this.mapper.toTask(taskDto);
        taskRepository.save(task);
        return task.getId();
    }

    public TaskDto findByIdAndCompanyId(Long companyId, Long taskId) {
        return taskRepository.findByIdAndOwnerCompany_Id(taskId, companyId)
                .map(mapper::toTaskDto)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskId));
    }

    public TaskDto findByIdAndUserId(Long taskId, Long userId) {
        return this.taskRepository.findByIdAndOwner_Id(taskId, userId)
                .map(mapper::toTaskDto)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskId));
    }

    public void deleteByIdAndUserId(Long taskId, Long userId) {
        var task = this.taskRepository.findByIdAndOwner_Id(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskId));
        taskRepository.delete(task);
    }

    public Long updateTaskByUserId(TaskDto taskDto, long userId) {
        taskRepository.findByIdAndOwner_Id(taskDto.getId(), userId)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskDto.getId()));
        var task = this.mapper.toTask(taskDto);
        taskRepository.save(task);
        return task.getId();
    }
}
