package org.legend8883.taskmanager.globalException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.auth.domain.exceptions.AuthException;
import org.legend8883.taskmanager.globalException.dto.BaseErrorResponse;
import org.legend8883.taskmanager.globalException.dto.ValidationErrorResponse;
import org.legend8883.taskmanager.globalException.dto.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.legend8883.taskmanager.globalException.messages.GlobalErrorMessages.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        log.error(e.getMessage(), e);

        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(
                        violation -> new Violation(
                                violation.getField(),
                                violation.getDefaultMessage()
                        )
                )
                .toList();

        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleConstraintViolationException(
            ConstraintViolationException e
    ) {
        log.error(e.getMessage(), e);

        List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();

        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleAuthException(
            AuthException e
    ) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                AUTH_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseErrorResponse handleBadCredentialsException(
            BadCredentialsException e
    ) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                BAD_CREDENTIALS,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseErrorResponse handleAuthorizationDeniedException(
            AuthorizationDeniedException e
    ) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                AUTH_DENIED,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseErrorResponse handleUsernameNotFoundException(
            UsernameNotFoundException e
    ) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                USER_NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseErrorResponse handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        log.error(e.getMessage(), e);

        return new BaseErrorResponse(
                ENTITY_NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
