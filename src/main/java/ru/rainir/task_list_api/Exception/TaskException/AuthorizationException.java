package ru.rainir.task_list_api.Exception.TaskException;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}