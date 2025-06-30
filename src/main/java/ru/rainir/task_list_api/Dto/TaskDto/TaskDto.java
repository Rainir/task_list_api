package ru.rainir.task_list_api.Dto.TaskDto;

import lombok.*;
import ru.rainir.task_list_api.Model.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private Long authorId;
    private String title;
    private String description;
    private int priority;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}