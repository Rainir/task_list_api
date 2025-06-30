package ru.rainir.task_list_api.Controller;

import jakarta.websocket.server.PathParam;
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

    @GetMapping("/getTelegramUser")
    public ResponseEntity<TelegramUser> getTelegramUser(@RequestParam Long telegramId) {
        return ResponseEntity.ok(telegramUserService.getTelegramUserByTelegramId(telegramId));
    }

    @GetMapping("/checkTelegramUser")
    public ResponseEntity<Boolean> checkTelegramUserRegistration(@RequestParam Long telegramId) {
        return ResponseEntity.ok(telegramUserService.checkTelegramUserRegistration(telegramId));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<Long> getUserIdByTelegramId(@RequestParam Long telegramId) {
        return ResponseEntity.ok(telegramUserService.getUserIdByTelegramId(telegramId));
    }

    @GetMapping
    public ResponseEntity<Boolean> checkUserIdBd(@PathParam("usernameAndId") String usernameAndId) {
        return ResponseEntity.ok(telegramUserService.checkUserInBd(usernameAndId));
    }
}