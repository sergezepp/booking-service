package com.epam.booking.controller.xml;

import com.epam.booking.exception.RestExceptionHandler;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.service.BookingFacade;
import com.epam.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xml")
public class LoadTicketsController extends RestExceptionHandler {

    @Autowired
    BookingFacade bookingService;

    @PostMapping(path = "/loadBookedTickets",
            consumes = {"application/xml"}, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Map<String,String>> loadTickets(@RequestBody List<TicketDto> ticketsList) {

        bookingService.loadBookedTickets(ticketsList);

        return ResponseEntity.ok( Map.of( "Message","Batch Successfully Processed"));
    }


}
