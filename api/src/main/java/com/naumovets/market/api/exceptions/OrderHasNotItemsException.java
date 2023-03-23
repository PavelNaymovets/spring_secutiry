package com.naumovets.market.api.exceptions;

public class OrderHasNotItemsException extends RuntimeException {
    public OrderHasNotItemsException(String message) {
        super(message);
    }
}
