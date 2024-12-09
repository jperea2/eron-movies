package com.iugo.eron;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.iugo.eron.controller.advice.HandlerController;
import com.iugo.eron.dto.ErrorMessage;

public class HandlerControllerTest {

    private HandlerController handlerController;

    @BeforeEach
    public void setUp() {
        handlerController = new HandlerController();
    }

    @Test
    public void testValidationMethodArgumentTypeMismatch() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        ResponseEntity<ErrorMessage> response = handlerController.validationMethodArgumentTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(ex.getMessage(), response.getBody().getError());
    }
}