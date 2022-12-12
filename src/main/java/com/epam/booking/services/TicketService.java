package com.epam.booking.services;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.model.Category;
import com.epam.booking.model.Event;
import com.epam.booking.model.Ticket;
import com.epam.booking.model.User;
import com.epam.booking.model.dto.EventDto;
import com.epam.booking.model.dto.UserDto;
import com.epam.booking.repository.EventRepository;
import com.epam.booking.repository.TicketRepository;
import com.epam.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public Ticket createTicket(long userId, long eventId, int place, Category category){

        userRepository.findById(userId).orElseThrow(NonExistentUserException::new);
        eventRepository.findById(eventId).orElseThrow(NonExistentEventException::new);

        Ticket ticket = new Ticket();
        ticket.setCategory(category);
        ticket.setPlace(place);
        ticket.setUser(new User(userId));
        ticket.setEvent(new Event(eventId));

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getBookedTicketsByEvent(EventDto eventDto, int pageSize, int pageNum){
       return ticketRepository.getTicketsByEventId(eventDto.getId());
    }



}
