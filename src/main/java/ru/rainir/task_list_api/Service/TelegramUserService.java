package ru.rainir.task_list_api.Service;

import jakarta.persistence.EntityNotFoundException;
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

        if (telegramUserRepository.existsById(createTelegramUserDto.getTelegramId())) {
            throw new RuntimeException("Вы уже зарегистрированы!");
        }

        User user = new User();
        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setTelegramId(createTelegramUserDto.getTelegramId());
        telegramUser.setTelegramUsername(createTelegramUserDto.getTelegramUsername());

        if (createTelegramUserDto.getUserId() == null) {

            user.setUsername(createTelegramUserDto.getUsername());
            user.setPassword(createTelegramUserDto.getPassword());

        } else {
            if (checkUserInBd(createTelegramUserDto.getUsername() + " " + createTelegramUserDto.getUserId())) {
                user = userRepository.getReferenceById(createTelegramUserDto.getUserId());
                telegramUser.setUserId(user.getId());

            } else {
                throw new EntityNotFoundException("Пользователь с Username: " + createTelegramUserDto.getUsername() + " и Id: " + createTelegramUserDto.getUserId() + " не существует!");
            }
        }

        user.setTelegramUser(telegramUser);
        telegramUser.setUsername(user.getUsername());
        if (user.getId() == null) {
            telegramUser.setUserId(
                    userRepository.saveAndFlush(user).getId()
            );
        } else {
            telegramUser.setUserId(user.getId());
            userRepository.save(user);
        }

        return telegramUserRepository.save(telegramUser);
    }

    public TelegramUser getTelegramUserByTelegramId(Long telegramId) {
        return telegramUserRepository.findById(telegramId).orElseThrow(() -> new EntityNotFoundException("Пользователь ID: " + telegramId + " не найден!"));
    }

    public Long getUserIdByTelegramId(Long telegramId) {
        return getTelegramUserByTelegramId(telegramId).getUserId();
    }

    public boolean checkUserInBd(String usernameAndId) {
        String[] usernameAndIdArr = usernameAndId.split(" ");

        User user = userRepository.getReferenceById(Long.parseLong(usernameAndIdArr[1]));

        return user.getUsername().equals(usernameAndIdArr[0]);
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