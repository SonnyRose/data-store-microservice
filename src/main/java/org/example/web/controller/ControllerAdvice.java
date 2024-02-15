package org.example.web.controller;

import org.example.model.exception.SensorNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(SensorNotFoundException.class)
    public String sensorNotFound(SensorNotFoundException sensorNotFoundException){
        return "Sensor not found: " + sensorNotFoundException;
    }
    @ExceptionHandler
    public String server(Exception e){
        e.printStackTrace();
        return "Things happens";
    }
}
