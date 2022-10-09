package de.dataenv.game.match.rest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class MatchConstraintAdvice {

    public static final String UNIQUE_NAME_ERROR_MESSAGE = "Match names have to be unique. Please choose an other name.";
    public static final String UNEXPECTED_CONSTRAINT_VIOLATION_MESSAGE = "An unexpected Constraint Violation occurred";
    public static final String NAME_SIZE_ERROR_MESSAGE = "The name of a match must be at least 4 characters long.";
    public static final String NAME_NULL_ERROR_MESSAGE = "Match name cannot be empty.";

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BadRequestResponse handleBadRequestException(DataIntegrityViolationException ex) {
        if (ex.getMessage().toLowerCase().contains("unique_match_name")) {
            return new BadRequestResponse(UNIQUE_NAME_ERROR_MESSAGE);
        } else {
            return new BadRequestResponse(UNEXPECTED_CONSTRAINT_VIOLATION_MESSAGE);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BadRequestResponse handleBadRequestException(ConstraintViolationException ex) {
        if ("name_size".equals(ex.getConstraintViolations().stream().findFirst().get().getMessage())) {
            return new BadRequestResponse(NAME_SIZE_ERROR_MESSAGE);
        } else if ("name_not_null".equals(ex.getConstraintViolations().stream().findFirst().get().getMessage())) {
            return new BadRequestResponse(NAME_NULL_ERROR_MESSAGE);
        } else {
            return new BadRequestResponse(UNEXPECTED_CONSTRAINT_VIOLATION_MESSAGE);
        }
    }

    @ExceptionHandler(MatchNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BadRequestResponse handleBadRequestException(MatchNotFoundException ex) {
        return new BadRequestResponse("Match Not Found");
    }


}