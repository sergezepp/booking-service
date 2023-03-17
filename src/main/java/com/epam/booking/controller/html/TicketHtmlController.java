package com.epam.booking.controller.html;

import com.epam.booking.exception.HtmlExceptionHandler;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.service.BookingFacade;
import com.epam.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/html")
public class TicketHtmlController extends HtmlExceptionHandler {

    @Autowired
    BookingFacade bookingService;

    private static final String TICKET_VIEW = "ticket";


    @GetMapping(value = "/getBookedTickets/{userIdentifier}")
    public String getBookedTicketsByUserIdentifier(@PathVariable String userIdentifier, Model model) {
        List<TicketDto> bookedTicketsList = bookingService.getBookedTicketsByUserIdentifier(userIdentifier, 10, 0);
        model.addAttribute("tickets", bookedTicketsList);
        return TICKET_VIEW;
    }


}
