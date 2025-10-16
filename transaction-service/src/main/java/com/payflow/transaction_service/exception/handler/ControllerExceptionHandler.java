package com.payflow.transaction_service.exception.handler;

import com.payflow.transaction_service.dtos.ExceptionDTO;
import com.payflow.transaction_service.exception.AuthorizationDeniedException;
import com.payflow.transaction_service.exception.RemoteServiceException;
import com.payflow.transaction_service.exception.TransactionNotFoundException;
import com.payflow.transaction_service.exception.TransactionProcessingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationException(MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<ExceptionDTO> handleRemoteServiceException(RemoteServiceException exception) {
        ExceptionDTO response = new ExceptionDTO(
                exception.getStatus().value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(exception.getStatus().value()).body(response);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleTransactionNotFoundException(TransactionNotFoundException exception) {
        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionDTO> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(TransactionProcessingException.class)
    public ResponseEntity<ExceptionDTO> handleTransactionProcessingException(TransactionProcessingException exception) {
        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleException(Exception exception) {
        ExceptionDTO response = new ExceptionDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
