package ru.rainir.task_list_api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rainir.task_list_api.Dto.TaskDto.CreateTaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.TaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.UpdateTaskDto;
import ru.rainir.task_list_api.Service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(CreateTaskDto createTaskDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskDto.getAuthorId(), createTaskDto));
    }

    @PatchMapping
    public ResponseEntity<TaskDto> updateTask(UpdateTaskDto updateTaskDto) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(updateTaskDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksByUserId(Long authorId) {
        System.out.println(authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.getAllTasksByAuthorId(authorId));
    }
}