package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Dto.TelegramUserDto.CreateTelegramUserDto;
import ru.rainir.task_list_api.Model.TelegramUser;
import ru.rainir.task_list_api.Model.User;
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

    public TelegramUser createTelegramUser(CreateTelegramUserDto createTelegramUserDto) {
        User user = new User();
        TelegramUser telegramUser = new TelegramUser();

        user.setUsername(createTelegramUserDto.getUsername());
        user.setPassword(createTelegramUserDto.getPassword());


        if (createTelegramUserDto.isOnlyTelegram()) {
            user.setUsername(createTelegramUserDto.getTelegramUsername());
            user.setPassword("randomPassword");   //TODO create random pass
        }

        userRepository.save(user);

        telegramUser.setUserId(user.getId());
        telegramUser.setTelegramUsername(createTelegramUserDto.getTelegramUsername());
        telegramUser.setTelegramId(createTelegramUserDto.getTelegramId());
        return telegramUserRepository.save(telegramUser);
    }
}