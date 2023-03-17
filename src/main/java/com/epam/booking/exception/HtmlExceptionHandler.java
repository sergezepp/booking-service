package com.epam.booking.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
public class HtmlExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        log.warn(e.toString());
        model.addAttribute("message", "No Element Found");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error(e.toString());
        return "error";
    }

}
