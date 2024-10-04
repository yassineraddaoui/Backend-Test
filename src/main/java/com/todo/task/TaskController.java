package com.todo.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/company/{companyId}/user/{userId}/status/{status}")
    public ResponseEntity<List<TaskDto>> getTaskByCompanyIdAndUserIdAndStatus(@PathVariable Long companyId,
                                                                              @PathVariable Long userId,
                                                                              @PathVariable String status,
                                                                              @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                              @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                              @RequestParam(value = "order", defaultValue = "ASC") String order) {
        var tasks = taskService.findAllByCompanyIdAndUserIdAndStatus(companyId, userId, status, pageNumber, pageSize, sort, order);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        var task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                  @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                  @RequestParam(value = "order", defaultValue = "ASC") String order) {
        var tasks = taskService.findAll(pageNumber, pageSize, sort, order);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> saveTask(@RequestBody TaskDto taskDto) {
        var taskId = taskService.saveTask(taskDto);
        return new ResponseEntity<>(String.format("Task %d created successfully", taskId), HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/tasks")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto taskDto) {
        var taskId = taskService.updateTask(taskDto);
        return new ResponseEntity<>(String.format("Task %d updated successfully", taskId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(@PathVariable Long userId,
                                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                          @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                          @RequestParam(value = "order", defaultValue = "ASC") String order) {
        var tasks = taskService.findAllByUserId(userId, pageNumber, pageSize, sort, order);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskByIdByUserId(@PathVariable Long userId, @PathVariable Long taskId) {
        var task = taskService.findByIdAndUserId(taskId, userId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTaskByIdAndUserId(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteByIdAndUserId(taskId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/user/{userId}/tasks")
    public ResponseEntity<String> updateTaskByUserId(@RequestBody TaskDto taskDto, @PathVariable Long userId) {
        var taskId = taskService.updateTaskByUserId(taskDto, userId);
        return new ResponseEntity<>(String.format("Task %d updated successfully", taskId), HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskByIdAndCompanyId(@PathVariable Long companyId, @PathVariable Long taskId) {
        var task = taskService.findByIdAndCompanyId(companyId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByCompanyId(@PathVariable Long companyId,
                                                             @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                             @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                             @RequestParam(value = "order", defaultValue = "ASC") String order) {
        var tasks = taskService.findAllByCompanyId(companyId, pageNumber, pageSize, sort, order);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}/user/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserIdAndCompanyId(@PathVariable Long companyId,
                                                                      @PathVariable Long userId,
                                                                      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                      @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                      @RequestParam(value = "order", defaultValue = "ASC") String order) {
        var tasks = taskService.findAllByCompanyIdAndUserId(companyId, userId, pageNumber, pageSize, sort, order);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/company/{companyId}/user/{userId}/tasks")
    public ResponseEntity<Void> deleteTasksByUserIdAndCompanyId(@PathVariable Long companyId, @PathVariable Long userId) {
        taskService.deleteAllByCompanyIdAndUserId(companyId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/company/{companyId}/tasks")
    public ResponseEntity<String> updateTaskByCompanyId(@PathVariable Long companyId, @RequestBody TaskDto taskDto) {
        var taskId = taskService.updateTaskByCompanyId(companyId, taskDto);
        return new ResponseEntity<>(String.format("Task %d updated successfully", taskId), HttpStatus.OK);
    }
}
