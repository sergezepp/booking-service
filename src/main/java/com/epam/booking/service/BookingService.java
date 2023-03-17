package com.epam.booking.service;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.exception.UserNameTakenException;
import com.epam.booking.exception.UserNotFoundException;
import com.epam.booking.model.Category;
import com.epam.booking.model.Event;
import com.epam.booking.model.Ticket;
import com.epam.booking.model.User;
import com.epam.booking.model.dto.EventDto;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Component
@Slf4j
public class BookingService implements BookingFacade {

    @Autowired
    EventService eventService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper mapper;


    @Override
    public EventDto getEventById(long eventId) {
        Event event = eventService.getEventById(eventId).orElseThrow(NoSuchElementException::new);
        log.info("Getting Event:" + event);
        return mapper.map(event, EventDto.class);
    }

    @Override
    public List<EventDto> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum).stream()
                .map(item -> mapper.map(item, EventDto.class))
                .toList();
    }

    @Override
    public List<EventDto> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsByDate(new java.sql.Date(day.getTime()), pageSize, pageNum).stream()
                .map(item -> mapper.map(item, EventDto.class))
                .toList();
    }


    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents().stream()
                .map(item -> mapper.map(item, EventDto.class))
                .toList();
    }

    @Override
    public EventDto createEvent(EventDto event) {
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setDate(new java.sql.Date(event.getDate().getTime()));

        return mapper.map(eventService.createEvent(newEvent), EventDto.class);
    }

    @Override
    public EventDto updateEvent(EventDto event) {
        Event eventUpdated;
        try {
            eventUpdated = eventService.updateEvent(mapper.map(event, Event.class));
        } catch (NonExistentEventException nonExistentEventException) {
            log.error("Error updating Event: Non Existent Event:" + event.getTitle());
            throw new NonExistentEventException();
        }

        return mapper.map(eventUpdated, EventDto.class);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        try {
            eventService.deleteEvent(new Event(eventId));
        } catch (NonExistentEventException nonExistentEventException) {
            log.error("Error updating Event: Non Existent Event");
            throw new NonExistentEventException();
        }

        return true;
    }


    @Override
    public UserDto getUserById(long userId) {
        return mapper.map(userService.getUserById(userId).orElseThrow(UserNotFoundException::new)
                , UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return mapper.map(userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new),
                UserDto.class);
    }

    @Override
    public List<UserDto> getUsersByGivenName(String firstName, String lastName, int pageSize, int pageNum) {
        return userService.getUsersByGivenName(firstName, lastName).stream()
                .map(item -> mapper.map(item, UserDto.class))
                .toList();
    }


    public List<UserDto> getUsersByGivenNameSync(String firstName, String lastName, int pageSize, int pageNum) {
        return userService.getUsersByGivenNameSync(firstName, lastName).stream()
                .map(item -> mapper.map(item, UserDto.class))
                .toList();
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        User user = userService.getUserByUserIdentifier(userName).orElseThrow(UserNotFoundException::new);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto user) {
        UserDto userDto = null;
        User newUser = new User();
        newUser.setUserIdentifier(user.getUserIdentifier());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        try {
            userDto = mapper.map(userService.createUser(newUser), UserDto.class);
        } catch (UserNameTakenException userNameTakenException) {
            log.error("Error creating User: User Name Taken -" + newUser.getUserIdentifier());
            throw new UserNameTakenException();
        }

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User newUser = mapper.map(user, User.class);
        UserDto userDto = null;

        try {
            userDto = mapper.map(userService.updateUser(newUser), UserDto.class);
        } catch (UserNotFoundException userNotFoundException) {
            log.error("Error creating User: User Not Found -" + newUser.getUserIdentifier());
            throw new UserNotFoundException();
        }

        return userDto;
    }

    @Override
    public boolean deleteUser(long userId) {
        try {
            userService.deleteUser(userId);
        } catch (UserNotFoundException userNotFoundException) {
            log.error("Error creating User: User Not Found -" + userId);
            throw new UserNotFoundException();
        }
        return true;
    }

    @Override
    public TicketDto bookTicket(long userId, long eventId, int place, Category category) {
        Ticket ticketOutput = null;

        try {
            ticketOutput = ticketService.createTicket(userId, eventId, place, category);
        } catch (NonExistentEventException nonExistentEventException) {
            log.error("Error creating Ticket: Non Existent Event:" + eventId);
            throw new NonExistentEventException();
        } catch (NonExistentUserException nonExistentUserException) {
            log.error("Error creating Ticket: Non Existent User:" + userId);
            throw new NonExistentUserException();
        }

        return mapper.map(ticketOutput, TicketDto.class);
    }

    public void loadBookedTickets(List<TicketDto> ticketList){
        ticketService.loadBookedTickets(ticketList);
    }

    @Override
    public List<TicketDto> getBookedTickets(UserDto user, int pageSize, int pageNum) {
        return ticketService.getBookedTicketsByUser(user.getId(), pageSize, pageNum).stream()
                .map(item -> mapper.map(item, TicketDto.class))
                .toList();
    }

    public List<TicketDto> getBookedTicketsByUserIdentifier(String userIdentifier, int pageSize, int pageNum) {
        User user =  userService.getUserByUserIdentifier(userIdentifier).orElseThrow(NonExistentUserException::new);
        return ticketService.getBookedTicketsByUser(user.getId(), pageSize, pageNum).stream()
                .map(item -> mapper.map(item, TicketDto.class))
                .toList();
    }

    @Override
    public List<TicketDto> getBookedTickets(EventDto event, int pageSize, int pageNum) {
        return ticketService.getBookedTicketsByEvent(event, pageSize, pageNum).stream()
                .map(item -> mapper.map(item, TicketDto.class))
                .toList();
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
        } catch (NoSuchElementException noSuchElementException) {
            log.error("Error Cancel Ticket,  Ticket Not Found" + ticketId);
            throw new NoSuchElementException();
        }
        return true;
    }
}
