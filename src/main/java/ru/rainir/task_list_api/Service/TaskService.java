package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Dto.TaskDto.CreateTaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.TaskDto;
import ru.rainir.task_list_api.Dto.TaskDto.UpdateTaskDto;
import ru.rainir.task_list_api.Exception.TaskException.AccessDeniedException;
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

        task.setCompletedAt(createTaskDto.getCompletedAt() != null ? createTaskDto.getCompletedAt()
                : LocalDateTime.of(3000, 1, 1, 0, 0));

        task.setCompletedAt(createTaskDto.getCompletedAt());

        return saveAndConvertToDto(task);
    }

    public TaskDto updateTask(UpdateTaskDto updateTaskDto) {

        Task task = getTaskFromBd(updateTaskDto.getId());

        if (!updateTaskDto.getAuthorId().equals(task.getAuthorId())) {
            throw new AccessDeniedException("У вас нет прав на изменение этой задачи!");
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

        return saveAndConvertToDto(task);
    }

    public TaskDto deleteTask(Long id) {
        return convertTaskToDto(taskRepository.deleteTaskById(id));
    }

    public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus, Long authorId) {

        Task task = getTaskFromBd(id);

        if (!authorId.equals(task.getAuthorId())) {
            throw new AccessDeniedException("У вас нет прав на изменение статуса этой задачи!");
        }
        task.setStatus(taskStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return saveAndConvertToDto(task);
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
        return new TaskDto(
                task.getId(),
                task.getAuthorId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getCompletedAt()
        );
    }

    private TaskDto saveAndConvertToDto(Task task) {
        return convertTaskToDto(taskRepository.save(task));
    }

    private Task getTaskFromBd(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Задача с ID: " + id + " не найдена!"));
    }
}
