package ru.rainir.task_list_api.Dto.TelegramUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTelegramUserDto {

    private Long userId;

    private String username;
    private String password;

    private Long telegramId;
    private String telegramUsername;
}