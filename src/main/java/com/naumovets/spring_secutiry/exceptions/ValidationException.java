package com.naumovets.spring_secutiry.exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationException extends RuntimeException {
    private List<String> errorMessage;

    public ValidationException(List<String> errorMessage) {
        super(errorMessage.stream().collect(Collectors.joining(",")));
        this.errorMessage = errorMessage;
    }
}
