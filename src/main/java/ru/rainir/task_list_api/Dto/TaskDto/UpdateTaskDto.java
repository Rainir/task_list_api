package ru.rainir.task_list_api.Dto.TaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rainir.task_list_api.Model.TaskStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private TaskStatus status;
}