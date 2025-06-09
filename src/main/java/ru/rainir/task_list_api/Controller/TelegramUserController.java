package ru.rainir.task_list_api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rainir.task_list_api.Dto.TelegramUserDto.CreateTelegramUserDto;
import ru.rainir.task_list_api.Model.TelegramUser;
import ru.rainir.task_list_api.Service.TelegramUserService;

@RestController
@RequestMapping("/api/v1/user/telegram")
public class TelegramUserController {

    private final TelegramUserService telegramUserService;

    public TelegramUserController(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @PostMapping
    public ResponseEntity<TelegramUser> createTelegramUser(CreateTelegramUserDto createTelegramUserDto) {
        return ResponseEntity.ok(telegramUserService.createTelegramUser(createTelegramUserDto));
    }

    @GetMapping
    public ResponseEntity<TelegramUser> getTelegramUser(@RequestParam Long telegramId) {
        return ResponseEntity.ok(telegramUserService.getTelegramUserByTelegramId(telegramId));
    }


}