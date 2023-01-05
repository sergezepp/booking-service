package com.epam.booking.service;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.exception.UserNameTakenException;
import com.epam.booking.exception.UserNotFoundException;
import com.epam.booking.model.Category;
import com.epam.booking.model.dto.EventDto;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.model.dto.UserDto;
import com.epam.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("integration-test")
@Slf4j
class BookingServiceIntegrationTests {

    @Autowired
    BookingService bookingService;


    @Test
    @DisplayName("Get Event By ID")
    void testGetEventById() {
        EventDto event = bookingService.getEventById(1);
        assertEquals("FORMULA 1", event.getTitle());
    }

    @Test
    void testGetEventByIdNoValidId() {
        assertThrows(NoSuchElementException.class, () -> bookingService.getEventById(100));
    }


    @Test
    void testCreateEvent() {
        EventDto newEvent = new EventDto();
        newEvent.setDate(java.sql.Date.valueOf("2022-12-01"));
        newEvent.setTitle("FIFA WORLD CUP");
        EventDto eventOutput = bookingService.createEvent(newEvent);
        assertEquals(newEvent.getTitle(), eventOutput.getTitle());
    }

    @Test
    void testBookTicket() {
        TicketDto ticket = bookingService.bookTicket(1, 1, 35, Category.STANDARD);
        assertNotNull(ticket);
        assertEquals(35, ticket.getPlace());
    }

    @Test
    void testBookTicketInvalidUser() {
        assertThrows(NonExistentUserException.class, () -> bookingService.bookTicket(999, 1, 35, Category.STANDARD));
    }

    @Test
    void testBookTicketInvalidEvent() {
        assertThrows(NonExistentEventException.class, () -> bookingService.bookTicket(1, 999, 35, Category.STANDARD));
    }


    @Test
    @DisplayName("Create User")
    void testCreateUser() {
        UserDto user = new UserDto();
        user.setUserName("test_user");
        user.setFirstName("testy");
        user.setLastName("testk");
        user.setEmail("test@test.com");

        UserDto userCreated = bookingService.createUser(user);
        assertEquals("test_user", userCreated.getUserName());
    }


    @Test
    @DisplayName("Create User - User Name Taken")
    void testCreateUserUserNAmeTaken() {
        UserDto user = new UserDto();
        user.setUserName("sergio.cepeda");
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@test.com");

        assertThrows(UserNameTakenException.class, () -> bookingService.createUser(user));
    }

    @Test
    void testGetBookedTicketsByEventId() {
        EventDto event = new EventDto();
        event.setId(1L);

        List<TicketDto> ticketlist = bookingService.getBookedTickets(event, 1, 0);

        assertEquals(1, ticketlist.size());
    }

    @Test
    void testGetBookedTicketsByEventIdEmptyReturnList() {
        EventDto event = new EventDto();
        event.setId(999L);

        List<TicketDto> ticketlist = bookingService.getBookedTickets(event, 1, 1);

        assertEquals(0, ticketlist.size());
    }

    @Test
    void testGetBookedTicketsByUser() {
        UserDto userSaved = bookingService.getUserByUserName("sergio.cepeda");
        List<TicketDto> ticketlist = bookingService.getBookedTickets(userSaved, 10, 0);
        assertEquals(2, ticketlist.size());
    }

    @Test
    void testGetEventsByTitle() {
        List<EventDto> eventslist = bookingService.getEventsByTitle("FORMULA", 1, 0);

        assertEquals(1, eventslist.size());
    }

    @Test
    void testGetEventsByTitleNoFoundEvents() {
        List<EventDto> eventslist = bookingService.getEventsByTitle("XXXX", 1, 1);

        assertEquals(0, eventslist.size());
    }


    @Test
    void testUpdateEvent() {
        EventDto event = new EventDto();
        event.setId(2L);
        event.setTitle("SODA STEREO EN VIVO");
        event.setDate(Date.valueOf("2022-12-12"));

        EventDto eventUpdated = bookingService.updateEvent(event);

        assertEquals("SODA STEREO EN VIVO", eventUpdated.getTitle());
        assertEquals(Date.valueOf("2022-12-12"), eventUpdated.getDate());
    }

    @Test
    void testUpdateEventNonExistentEventId() {
        EventDto event = new EventDto();
        event.setId(999L);
        event.setTitle("FORMULA 1 MEXICO");
        event.setDate(Date.valueOf("2022-12-12"));

        assertThrows(NonExistentEventException.class, () -> bookingService.updateEvent(event));
    }

    @Test
    void testGetUserByGivenName() {
        UserDto user = new UserDto();
        user.setUserName("test_user_A");
        user.setFirstName("Gerardo");
        user.setLastName("Cerati");
        user.setEmail("test@test.com");
        bookingService.createUser(user);

        UserDto user2 = new UserDto();
        user2.setUserName("test_user_2");
        user2.setFirstName("Izza");
        user2.setLastName("Cerati");
        user2.setEmail("test@test.com");

        bookingService.createUser(user2);

        StopWatch watch = new StopWatch();
        watch.start();
        List<UserDto> userList = bookingService.getUsersByGivenName("", "Cerati", 1, 0);
        watch.stop();
        log.info("Threads: " + watch.getTotalTimeMillis());


        StopWatch watch2 = new StopWatch();
        watch2.start();
        List<UserDto> userList2 = bookingService.getUsersByGivenNameSync("", "Cerati", 1, 0);
        watch2.stop();
        log.info("SYNC: " + watch2.getTotalTimeMillis());


        assertEquals(2, userList.size());

    }


    @Test
    void testGetEventsForDay() {
        List<EventDto> list = bookingService.getEventsForDay(java.sql.Date.valueOf("2022-10-10"), 1, 0);
        assertEquals(1, list.size());
    }


    @Test
    void testDeleteEvent() {
        EventDto event = new EventDto();
        event.setTitle("FORMULA 1 MEXICO");
        event.setDate(Date.valueOf("2022-12-12"));
        EventDto eventOutput = bookingService.createEvent(event);

        Long eventId = eventOutput.getId();
        bookingService.deleteEvent(eventId);

        assertThrows(NoSuchElementException.class, () -> bookingService.getEventById(eventId));

    }

    @Test
    void testDeleteEventInvalidId() {
        assertThrows(NonExistentEventException.class, () -> bookingService.deleteEvent(99999L));
    }

    @Test
    void testGetUserByUserName() {
        UserDto user = new UserDto();
        user.setUserName("testUser01");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");
        bookingService.createUser(user);

        UserDto userSaved = bookingService.getUserByUserName(user.getUserName());

        assertNotNull(userSaved);
        assertEquals("testUser01", userSaved.getUserName());

    }


    @Test
    void testGetUserByEmail() {
        UserDto user = new UserDto();
        user.setUserName("testUser02");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");
        bookingService.createUser(user);

        UserDto userSaved = bookingService.getUserByEmail(user.getEmail());

        assertNotNull(userSaved);
        assertEquals("test@test.com", userSaved.getEmail());

    }

    @Test
    void testGetUserById() {
        UserDto user = new UserDto();
        user.setUserName("testUser03");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");
        UserDto userSaved = bookingService.createUser(user);

        assertNotNull(userSaved);

        UserDto userObtained = bookingService.getUserById(userSaved.getId());

        assertNotNull(userObtained);
        assertEquals(userSaved.getId(), userObtained.getId());

    }

    @Test
    void testUpdateUser() {
        UserDto user = new UserDto();
        user.setUserName("testUser04");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");
        UserDto userSaved = bookingService.createUser(user);

        assertNotNull(userSaved);

        userSaved.setEmail("new_email@gmail.com");

        UserDto userObtained = bookingService.updateUser(userSaved);

        assertNotNull(userObtained);
        assertEquals(userSaved.getEmail(), userObtained.getEmail());

    }

    @Test
    void testUpdateUserNotValidId() {
        UserDto user = new UserDto();
        user.setId(9999L);
        user.setUserName("testUser04");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");

        assertThrows(UserNotFoundException.class, () -> bookingService.updateUser(user));
    }

    @Test
    void testDeleteUser() {
        UserDto user = new UserDto();
        user.setUserName("testUser05");
        user.setFirstName("Gerardo");
        user.setLastName("Cepeda");
        user.setEmail("test@test.com");
        UserDto userSaved = bookingService.createUser(user);

        assertNotNull(userSaved);

        bookingService.deleteUser(userSaved.getId());


        assertThrows(UserNotFoundException.class, () -> bookingService.getUserById(userSaved.getId()));

    }

    @Test
    void testDeleteUserNotValidID() {
        assertThrows(UserNotFoundException.class, () -> bookingService.deleteUser(9999L));
    }

    @Test
    void testCancelTicket() {
        assertTrue(bookingService.cancelTicket(3L));
    }

    @Test
    void testCancelTicketInvalidId() {
        assertThrows(NoSuchElementException.class, () -> bookingService.cancelTicket(9999L));
    }
}
