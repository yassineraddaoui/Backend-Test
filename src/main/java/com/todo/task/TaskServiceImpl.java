package com.todo.task;

import com.todo.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper mapper;
    private final TaskRepository taskRepository;

    public List<TaskDto> findAllByCompanyIdAndUserIdAndStatus(Long companyId,
                                                              Long userId,
                                                              String status,
                                                              int pageNumber,
                                                              int pageSize,
                                                              String sort,
                                                              String order) {
        Specification<Task> spec = Specification.where(null);

        if (companyId != null) {
            spec = spec.and(TaskSpecefication.hasCompanyId(companyId));
        }

        if (userId != null) {
            spec = spec.and(TaskSpecefication.hasUserId(userId));
        }

        if (status != null && !status.isEmpty()) {
            spec = spec.and(TaskSpecefication.hasTaskStatus(status.toUpperCase()));
        }

        return taskRepository.findAll(
                        spec,
                        PageRequest.of(pageNumber, pageSize,
                                Sort.Direction.fromString(order), sort)
                ).stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> findAll(int pageNumber, int pageSize, String sort, String order) {
        var tasks = taskRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(order), sort));
        return tasks.stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> findAllByCompanyId(Long companyId, int pageNumber, int pageSize, String sort, String order) {
        var tasks = taskRepository.findAllByOwnerCompany_Id(companyId, PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(order), sort));
        return tasks.stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> findAllByUserId(Long userId, int pageNumber, int pageSize, String sort, String order) {
        var tasks = taskRepository.findAllByOwner_Id(userId, PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(order), sort));
        return tasks.stream()
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

    public List<TaskDto> findAllByCompanyIdAndUserId(Long companyId, Long userId, int pageNumber, int pageSize, String sort, String order) {
        var tasks = taskRepository.findAllByOwnerCompany_IdAndOwner_Id(companyId, userId, PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(order), sort));
        return tasks.stream()
                .map(mapper::toTaskDto)
                .toList();
    }

    public void deleteAllByCompanyIdAndUserId(Long companyId, Long userId) {
        taskRepository.deleteAllByOwnerCompany_IdAndOwner_Id(companyId, userId);
    }

    public Long updateTaskByCompanyId(Long companyId, TaskDto taskDto) {
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

    public Long updateTaskByUserId(TaskDto taskDto, Long userId) {
        taskRepository.findByIdAndOwner_Id(taskDto.getId(), userId)
                .orElseThrow(() -> new TaskNotFoundException("No Task found with the ID::" + taskDto.getId()));
        var task = this.mapper.toTask(taskDto);
        taskRepository.save(task);
        return task.getId();
    }
}
