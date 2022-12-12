package com.epam.booking.services;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.exception.UserNameTakenException;
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
import java.util.stream.Collectors;


@Component
@Slf4j
public class BookingService implements BookingFacade {

    @Autowired
    EventService eventService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    private static final ModelMapper mapper = new ModelMapper();


    @Override
    public EventDto getEventById(long eventId) {
        Event event = eventService.getEventById(eventId).orElseThrow(NoSuchElementException::new);
        log.info("Getting Event:" + event);
        return mapper.map(event, EventDto.class);
    }

    @Override
    public List<EventDto> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public List<EventDto> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public EventDto createEvent(EventDto event) {
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setDate( new java.sql.Date( event.getDate().getTime()));

        return  mapper.map(eventService.createEvent(newEvent), EventDto.class);
    }

    @Override
    public EventDto updateEvent(EventDto event) {
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return false;
    }


    @Override
    public UserDto getUserById(long userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> getUsersByName(String name, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public UserDto createUser(UserDto user) {
        UserDto  userDto = null;
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

       try{
          userDto = mapper.map(userService.createUser(newUser) , UserDto.class);
       }catch(UserNameTakenException userNameTakenException){
            log.error("Error creating User: User Name Taken -" + newUser.getUserName());
            throw new UserNameTakenException();
        }

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        return false;
    }


    @Override
    public TicketDto bookTicket(long userId, long eventId, int place, Category category) {
        Ticket ticketOutput = null;

        try {
            ticketOutput = ticketService.createTicket(userId, eventId, place, category);
        }catch(NonExistentEventException nonExistentEventException){
             log.error("Error creating Ticket: Non Existent Event:" + eventId);
             throw new NonExistentEventException();
        }catch (NonExistentUserException nonExistentUserException){
            log.error("Error creating Ticket: Non Existent User:" + userId);
            throw new NonExistentUserException();
        }

        return mapper.map(ticketOutput , TicketDto.class);
    }

    @Override
    public List<TicketDto> getBookedTickets(UserDto user, int pageSize, int pageNum) {



        return null;
    }

    @Override
    public List<TicketDto> getBookedTickets(EventDto event, int pageSize, int pageNum) {

        List<Ticket>  ticketList = ticketService.getBookedTicketsByEvent(event ,1 ,1);

        return ticketList.stream().map( item -> mapper.map(item, TicketDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return false;
    }
}
