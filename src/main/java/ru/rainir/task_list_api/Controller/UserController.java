package ru.rainir.task_list_api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rainir.task_list_api.Dto.UserDto.CreateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UpdateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UserDto;
import ru.rainir.task_list_api.Service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<UserDto> createUser(CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserDto(createUserDto));
    }

    @ResponseBody
    @PostMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, UpdateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUserDto(id, updateUserDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserDtoById(Long.parseLong(id)));
    }
}
