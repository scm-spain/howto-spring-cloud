package com.scmspain.howtospring.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException error) {
    logger.info(error.getMessage(), error);

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body("{\"message\": \"personalized message\"}");
  }
}