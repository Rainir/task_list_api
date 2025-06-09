package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public TelegramUser createTelegramUser(CreateTelegramUserDto createTelegramUserDto) {

        User user = new User();

        user.setUsername(createTelegramUserDto.getUsername());
        user.setPassword(createTelegramUserDto.getPassword());

        if (createTelegramUserDto.isOnlyTelegram()) {
            user.setUsername(createTelegramUserDto.getTelegramUsername());
            user.setPassword("randomPassword");   //TODO create random pass
        }

        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setTelegramId(createTelegramUserDto.getTelegramId());
        telegramUser.setTelegramUsername(createTelegramUserDto.getTelegramUsername());

        user.setTelegramUser(telegramUser);

        telegramUser.setUserId(
                 userRepository.saveAndFlush(user).getId()
         );

        return telegramUserRepository.save(telegramUser);
    }

    public TelegramUser getTelegramUserByTelegramId(Long telegramId) {
        return telegramUserRepository.getTelegramUserByTelegramId(telegramId);
    }

    public Long getUserIdByTelegramId(Long telegramId) {
        return getTelegramUserByTelegramId(telegramId).getUserId();
    }

    private TelegramUser checkChangeTelegramUsernameUpdate(TelegramUser telegramUser) {
        if (!getTelegramUserByTelegramId(
                telegramUser.getTelegramId()).getTelegramUsername()
                .equals(telegramUser.getTelegramUsername())) {
            return telegramUserRepository.save(telegramUser);
        }
        return telegramUser;
    }
}