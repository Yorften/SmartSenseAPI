package com.smartsense.exceptions;

public class InactiveDeviceException extends RuntimeException {
    public InactiveDeviceException(String message) {
        super(message);
    }
}
