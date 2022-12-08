package com.epam.booking;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.model.Category;
import com.epam.booking.model.dto.EventDto;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.services.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookingIntegrationTests {

	@Autowired
	BookingService bookingService;


	@Test
	void testGetEventById(){
		EventDto event = bookingService.getEventById(1);
		assertEquals("FORMULA 1" , event.getTitle());
	}

	@Test
	void testGetEventByIdNoValidId(){
		assertThrows(NoSuchElementException.class,  ()-> bookingService.getEventById(100));
	}


	@Test
	void testCreateEvent(){
		EventDto newEvent = new EventDto();
		newEvent.setDate( java.sql.Date.valueOf("2022-12-01"));
		newEvent.setTitle("FIFA WORLD CUP");
		EventDto eventOutput = bookingService.createEvent(newEvent);
		assertEquals(newEvent.getTitle() , eventOutput.getTitle());
	}

	@Test
	void testBookTicket(){
		TicketDto ticket = bookingService.bookTicket(1,1,35, Category.STANDARD);
		assertNotNull(ticket);
		assertEquals(35 , ticket.getPlace());

	}

	@Test
	void testBookTicketInvalidUser(){
		assertThrows(NonExistentUserException.class,  ()-> bookingService.bookTicket(999,1,35, Category.STANDARD));
	}

	@Test
	void testBookTicketInvalidEvent(){
		assertThrows(NonExistentEventException.class,  ()-> bookingService.bookTicket(1,999,35, Category.STANDARD));
	}

}
