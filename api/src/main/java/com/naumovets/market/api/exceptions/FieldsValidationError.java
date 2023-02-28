package com.naumovets.market.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FieldsValidationError {
    private List<String> errorMessage;
}
