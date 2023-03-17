package com.epam.booking.service;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.exception.NonExistentUserException;
import com.epam.booking.model.Category;
import com.epam.booking.model.Event;
import com.epam.booking.model.Ticket;
import com.epam.booking.model.User;
import com.epam.booking.model.dto.EventDto;
import com.epam.booking.model.dto.TicketDto;
import com.epam.booking.repository.EventRepository;
import com.epam.booking.repository.TicketRepository;
import com.epam.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public Ticket createTicket(long userId, long eventId, int place, Category category) {

        User user = userRepository.findById(userId).orElseThrow(NonExistentUserException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NonExistentEventException::new);

        Ticket ticket = new Ticket();
        ticket.setCategory(category);
        ticket.setPlace(place);
        ticket.setUser(user);
        ticket.setEvent(event);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getBookedTicketsByEvent(EventDto eventDto, int pageSize, int pageNum) {
        return ticketRepository.getTicketsByEventId(eventDto.getId(), PageRequest.of(pageNum, pageSize));
    }

    public List<Ticket> getBookedTicketsByUser(Long userId, int pageSize, int pageNum) {
        return ticketRepository.getTicketsByUserId(userId, PageRequest.of(pageNum, pageSize));
    }

    public void deleteTicket(Long ticketId) {
        if (ticketRepository.findById(ticketId).isPresent()) {
            ticketRepository.deleteById(ticketId);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void loadBookedTickets(List<TicketDto> ticketList){
        ticketList.forEach( item -> createTicket(item.getUserId(), item.getEventId(), item.getPlace(), item.getCategory()));
    }

}

