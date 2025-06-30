package ru.rainir.task_list_api.Controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rainir.task_list_api.Dto.TaskDto.CreateTaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.TaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.UpdateTaskDto;
import ru.rainir.task_list_api.Model.TaskStatus;
import ru.rainir.task_list_api.Service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TaskDto> createTask(CreateTaskDto createTaskDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskDto.getAuthorId(), createTaskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksByAuthorId(@RequestParam Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasksByAuthorId(authorId));
    }

    @PatchMapping
    public ResponseEntity<TaskDto> updateTask(UpdateTaskDto updateTaskDto) {
        return ResponseEntity.ok(taskService.updateTask(updateTaskDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @PathParam("taskStatus") TaskStatus taskStatus, Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskStatus(id, taskStatus, authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}