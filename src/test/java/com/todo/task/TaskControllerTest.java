package com.todo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.company.Company;
import com.todo.exception.TaskNotFoundException;
import com.todo.user.Role;
import com.todo.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    private static final String END_POINT_PATH = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        String requestURI = END_POINT_PATH + "/tasks";

        var task = TaskDto.builder()
                .ownerId(1L)
                .description("desc")
                .status("PENDING")
                .title("title")
                .build();

        Mockito.when(taskService.saveTask(task)).thenReturn(1L);

        String requestBody = objectMapper.writeValueAsString(task);

        mockMvc.perform(post(requestURI)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testGetTaskByIdShouldReturn404NotFound() throws Exception {
        var taskId = 1L;
        String requestURI = END_POINT_PATH + "/tasks/" + taskId;

        Mockito.when(taskService.findById(taskId)).thenThrow(TaskNotFoundException.class);
        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetTaskByIdShouldReturn200OK() throws Exception {
        Long taskId = 123L;
        String requestURI = END_POINT_PATH + "/tasks/" + taskId;

        var taskDto = TaskDto.builder()
                .id(taskId)
                .description("desc")
                .title("title")
                .status("TERMINATED")
                .build();

        Mockito.when(taskService.findById(taskId)).thenReturn(taskDto);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(taskDto)))
                .andDo(print());
    }

    @Test
    public void testGetTasksShouldReturn200OK() throws Exception {
        String requestURI = END_POINT_PATH + "/tasks";

        var task = TaskDto.builder()
                .description("desc")
                .status("PENDING")
                .title("title")
                .build();
        var task2 = TaskDto.builder()
                .description("desc")
                .status("PENDING")
                .title("title")
                .build();
        List<TaskDto> listTasks = List.of(task, task2);

        Mockito.when(taskService.findAll(0,10,"id","ASC")).thenReturn(listTasks);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(listTasks)))
                .andDo(print());
    }

    @Test
    public void testUpdateTaskShouldReturn404NotFound() throws Exception {
        String requestURI = END_POINT_PATH + "/tasks";

        var task = TaskDto.builder()
                .id(33L)
                .ownerId(1L)
                .description("desc")
                .status("PENDING")
                .title("title")
                .build();

        Mockito.when(taskService.updateTask(Mockito.any(TaskDto.class)))
                .thenThrow(TaskNotFoundException.class);
        String requestBody = objectMapper.writeValueAsString(task);
        mockMvc.perform(put(requestURI)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Long taskId = 123L;
        String requestURI = END_POINT_PATH + "/tasks/" + taskId;

        Mockito.doThrow(TaskNotFoundException.class).when(taskService).deleteById(taskId);
        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetTaskByCompanyIdAndTaskIdShouldReturn200OK() throws Exception {
        Long companyId = 10L;
        Long taskId = 123L;
        String requestURI = END_POINT_PATH + "/company/" + companyId + "/tasks/" + taskId;

        var user = User.builder()
                .id(1L)
                .role(Role.COMPANY_ADMIN)
                .name("user")
                .company(Company.builder()
                        .id(companyId)
                        .build())
                .build();

        var taskDto = TaskDto.builder()
                .id(taskId)
                .ownerId(user.getId())
                .description("Company task")
                .status("PENDING")
                .title("Company Task Title")
                .build();

        Mockito.when(taskService.findByIdAndCompanyId(companyId, taskId)).thenReturn(taskDto);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(taskDto)))
                .andDo(print());
    }
    @Test
    public void testGetTasksByCompanyIdShouldReturn200OK() throws Exception {
        Long companyId = 10L;
        String requestURI = END_POINT_PATH + "/company/" + companyId + "/tasks";
        var user = User.builder()
                .id(1L)
                .role(Role.COMPANY_ADMIN)
                .name("user")
                .company(Company.builder()
                        .id(companyId)
                        .build())
                .build();

        var task1 = TaskDto.builder()
                .ownerId(user.getId())
                .description("Company task")
                .status("PENDING")
                .title("Company Task Title")
                .build();
        var task2 = TaskDto.builder()
                .ownerId(user.getId())
                .description("Company task")
                .status("PENDING")
                .title("Company Task Title")
                .build();

        List<TaskDto> tasks = List.of(task1, task2);

        Mockito.when(taskService.findAllByCompanyId(companyId,0,10,"id","ASC")).thenReturn(tasks);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)))
                .andDo(print());
    }

    @Test
    public void testUpdateTaskByCompanyIdShouldReturn200OK() throws Exception {
        long companyId = 10L;
        String requestURI = END_POINT_PATH + "/company/" + companyId + "/tasks";

        var user = User.builder()
                .id(1L)
                .role(Role.COMPANY_ADMIN)
                .name("user")
                .company(Company.builder()
                        .id(companyId)
                        .build())
                .build();

        var taskDto = TaskDto.builder()
                .id(0L)
                .ownerId(user.getId())
                .description("Company task")
                .status("PENDING")
                .title("Company Task Title")
                .build();

        Mockito.when(taskService.updateTaskByCompanyId(companyId, taskDto)).thenReturn(123L);

        String requestBody = objectMapper.writeValueAsString(taskDto);

        mockMvc.perform(put(requestURI)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Task %d updated successfully", taskDto.getId())))
                .andDo(print());
    }

    @Test
    public void testDeleteTasksByUserIdAndCompanyIdShouldReturn204NoContent() throws Exception {
        long companyId = 10L;
        long userId = 1L;
        String requestURI = END_POINT_PATH + "/company/" + companyId + "/user/" + userId + "/tasks";

        Mockito.doNothing().when(taskService).deleteAllByCompanyIdAndUserId(companyId, userId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}