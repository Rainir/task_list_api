package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Dto.UserDto.CreateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UpdateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UserDto;
import ru.rainir.task_list_api.Model.User;
import ru.rainir.task_list_api.Repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUserDto(CreateUserDto createUserDto) {
        User user = new User();

        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        user.setEmail(createUserDto.getEmail());
        return convertUserToUserDto(userRepository.save(user));
    }

    public UserDto updateUserDto(UpdateUserDto updateUserDto) {
        User user = userRepository.findById(updateUserDto.getId())
                        .orElseThrow(() -> new NoSuchElementException("Пользователь не найден!"));
        if (updateUserDto.getFirstName() != null && !updateUserDto.getFirstName().isEmpty()) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null && !updateUserDto.getLastName().isEmpty()) {
            user.setLastName(updateUserDto.getLastName());
        }
        return convertUserToUserDto(userRepository.save(user));
    }

    public UserDto getUserDtoById(Long id) {
        return userRepository.findById(id)
                .map(this::convertUserToUserDto)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с данным id: " + id + " не найден!"));
    }

    public UserDto getUserDtoByUsername(String username) {
        return convertUserToUserDtoWithoutId(userRepository.getUsersByUsername((username)));
    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private UserDto convertUserToUserDtoWithoutId(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
