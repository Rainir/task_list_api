package ru.rainir.task_list_api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rainir.task_list_api.Dto.UserDto.CreateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UpdateUserDto;
import ru.rainir.task_list_api.Dto.UserDto.UserDto;
import ru.rainir.task_list_api.Service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserDto(createUserDto));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUser(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @ResponseBody
    @PatchMapping
    public ResponseEntity<UserDto> updateUser(UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUserDto(updateUserDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDtoByUsername(username));
    }
}