package com.epam.booking.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.NoSuchElementException;

@Slf4j
public class PdfExceptionHandler {

    @ExceptionHandler(NonExistentUserException.class)
    public String handleNonExistentUserException(NonExistentUserException e, Model model) {
        log.warn(e.toString());
        model.addAttribute("message", "No User Found");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.warn(e.toString());
        model.addAttribute("message", "Error");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error(e.toString());
        return "error";
    }


}
