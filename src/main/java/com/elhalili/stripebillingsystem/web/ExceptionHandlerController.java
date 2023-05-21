package com.elhalili.stripebillingsystem.web;

import com.elhalili.stripebillingsystem.exception.NotSupportedFeatureException;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(StripeException.class)
    public ResponseEntity<Object> stripeException(StripeException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());

        return ResponseEntity.internalServerError().body(message);
    }
    @ExceptionHandler(NotSupportedFeatureException.class)
    public ResponseEntity<Object> stripeException(NotSupportedFeatureException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(message);
    }
}
