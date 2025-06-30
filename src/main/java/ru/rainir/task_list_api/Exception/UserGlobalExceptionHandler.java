package ru.rainir.task_list_api.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static ru.rainir.task_list_api.Exception.TaskGlobalExceptionHandler.getMapResponseEntity;

@ControllerAdvice
public class UserGlobalExceptionHandler extends Exception {

    Log logger = LogFactory.getLog(UserGlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(String message) {
        logger.error(message);
        return createErrorResponse(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handlerUserExist(String message) {
        logger.error(message);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String message) {
        return getMapResponseEntity(status, message);
    }
}