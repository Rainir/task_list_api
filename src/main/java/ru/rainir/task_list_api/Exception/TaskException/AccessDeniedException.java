package ru.rainir.task_list_api.Exception.TaskException;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}