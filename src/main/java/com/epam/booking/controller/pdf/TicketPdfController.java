package com.epam.booking.controller.pdf;


import com.epam.booking.exception.PdfExceptionHandler;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.service.BookingFacade;
import com.epam.booking.service.BookingService;
import com.epam.booking.service.PDFGenerationService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import java.util.List;

@Controller
@RequestMapping("/pdf")
public class TicketPdfController extends PdfExceptionHandler {

    @Autowired
    BookingFacade bookingService;

    @Autowired
    PDFGenerationService pdfGenerationService;

    private static final String TICKET_VIEW = "ticket";

    private static final String FILE_NAME = "output.pdf";


    @GetMapping(value = "/getBookedTickets/{userIdentifier}",
            produces = {"application/pdf; charset=utf-8"}, headers = {"Accept=application/pdf"})
    public ResponseEntity<byte[]> getBookedTicketsByUserIdentifier(@PathVariable String userIdentifier) throws DocumentException {

        List<TicketDto> bookedTicketsList = bookingService.getBookedTicketsByUserIdentifier(userIdentifier, 10, 0);
        Context model = new Context();
        model.setVariable("tickets", bookedTicketsList);

        return pdfGenerationService.createPDFandGenerateResponse(model, TICKET_VIEW, FILE_NAME);
    }


}




