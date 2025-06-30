package ru.rainir.task_list_api.Dto.TaskDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    private Long authorId;
    private String title;
    private String description;
    private int priority;
    private LocalDateTime completedAt;
}