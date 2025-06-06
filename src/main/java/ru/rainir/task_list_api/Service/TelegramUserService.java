package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Repository.TelegramUserRepository;
import ru.rainir.task_list_api.Repository.UserRepository;

@Service
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;
    private final UserRepository userRepository;


    public TelegramUserService(TelegramUserRepository telegramUserRepository, UserRepository userRepository) {
        this.telegramUserRepository = telegramUserRepository;
        this.userRepository = userRepository;
    }
}