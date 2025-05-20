package ru.rainir.task_list_api.Service;

import org.springframework.stereotype.Service;
import ru.rainir.task_list_api.Dto.UserDto.CreateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UpdateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UserDto;
import ru.rainir.task_list_api.Model.User;
import ru.rainir.task_list_api.Repository.UserRepository;

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

    public UserDto updateUserDto(String id, UpdateUserDto updateUserDto) {
        User user  = userRepository.findById(Long.parseLong(id)).get();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        return convertUserToUserDto(userRepository.save(user));
    }

    public UserDto getUserDtoById(Long id) {
        return convertUserToUserDto(userRepository.findById(id).get());
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
}
