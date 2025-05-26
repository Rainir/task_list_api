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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        if (createTaskDto.getCompletedAt() == null) {
            task.setCompletedAt(LocalDateTime.of(3000, 1, 1, 0, 0));
        }
        task.setCompletedAt(createTaskDto.getCompletedAt());

        return convertTaskToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(UpdateTaskDto updateTaskDto) {

        Long updateAuthorId = updateTaskDto.getAuthorId();

        Task task = getTaskFromBd(updateAuthorId);

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

    public TaskDto deleteTask(Long id) {
        return convertTaskToDto(taskRepository.deleteTaskById(id));
    }

    public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus) {
        Task task = getTaskFromBd(id);
        task.setStatus(taskStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return convertTaskToDto(taskRepository.save(task));
    }

    public TaskDto getTask(Long id) {
        Task task = getTaskFromBd(id);
        System.out.println(task);
        return convertTaskToDto(task);
    }

    public List<TaskDto> getAllTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId).stream().map(
                task -> {
                    if (task.getCompletedAt().isBefore(LocalDateTime.now()) && task.getCompletedAt() != null) {
                        task.setStatus(TaskStatus.FAILED);
                        taskRepository.save(task);
                    }
                    return convertTaskToDto(task);
                }
        ).collect(Collectors.toList());
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

    private Task getTaskFromBd(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Задача с ID: " + id + " не найдена!"));
    }
}
