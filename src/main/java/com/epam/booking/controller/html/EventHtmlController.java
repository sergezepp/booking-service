package com.epam.booking.controller.html;

import com.epam.booking.exception.HtmlExceptionHandler;
import com.epam.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class EventHtmlController extends HtmlExceptionHandler {

    @Autowired
    BookingService bookingService;

    private static final String EVENT_VIEW = "event";

    @GetMapping("/getEventById/{id}")
    public String getEventById(@PathVariable Integer id, Model model) {
        model.addAttribute("events", bookingService.getEventById(id));
        return EVENT_VIEW;
    }

    @GetMapping("/getAllEvents")
    public String getAllEvents(Model model) {
        model.addAttribute("events", bookingService.getAllEvents());
        return EVENT_VIEW;
    }
}
