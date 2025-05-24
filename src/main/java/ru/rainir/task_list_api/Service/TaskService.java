package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Dto.TaskDto.CreateTaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.TaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.UpdateTaskDto;
import ru.rainir.task_list_api.Exception.TaskException.AuthorizationException;
import ru.rainir.task_list_api.Model.Task;
import ru.rainir.task_list_api.Model.TaskStatus;
import ru.rainir.task_list_api.Repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDto createTask(Long authorId, CreateTaskDto createTaskDto) {
        Task task = new Task();

        task.setAuthorId(authorId);
        task.setTitle(createTaskDto.getTitle());
        task.setDescription(createTaskDto.getDescription());
        task.setPriority(createTaskDto.getPriority());

        task.setStatus(TaskStatus.OPEN);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        task.setCompletedAt(createTaskDto.getCompletedAt());

        return convertTaskToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(UpdateTaskDto updateTaskDto) {

        Long updateAuthorId = updateTaskDto.getAuthorId();

        Task task = taskRepository.findById(updateTaskDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Задача с ID: " + updateTaskDto.getId() + "не найдена!"));

        if (!updateAuthorId.equals(task.getAuthorId())) {
            throw new AuthorizationException("У вас нет прав на изменение этой задачи!");
        }

        if (updateTaskDto.getTitle() != null && !updateTaskDto.getTitle().isEmpty()) {
            task.setTitle(updateTaskDto.getTitle());
        }
        if (updateTaskDto.getDescription() != null && !updateTaskDto.getDescription().isEmpty()) {
            task.setDescription(updateTaskDto.getDescription());
        }
        if (updateTaskDto.getPriority() != null) {
            task.setPriority(updateTaskDto.getPriority());
        }
        if (updateTaskDto.getStatus() != null) {
            task.setStatus(updateTaskDto.getStatus());
        }
        task.setUpdatedAt(LocalDateTime.now());

        return convertTaskToDto(taskRepository.save(task));
    }

    public TaskDto getTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Задача с ID: " + id + " не найдена!"));
        System.out.println(task);
        assert task != null;
        return convertTaskToDto(task);
    }

    public List<TaskDto> getAllTasksByAuthorId(Long authorId) {
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskRepository.findByAuthorId(authorId).forEach(task -> taskDtoList.add(convertTaskToDto(task)));
        return taskDtoList;
    }

    private TaskDto convertTaskToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setAuthorId(task.getAuthorId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        taskDto.setCompletedAt(task.getCompletedAt());
        return taskDto;
    }
}
