package com.example.cityproject.exception;

public class CityAlreadyDefinedException extends RuntimeException{
    public CityAlreadyDefinedException(String message) {
        super(message);
    }

}
