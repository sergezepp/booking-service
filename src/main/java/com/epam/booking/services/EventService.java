package com.epam.booking.services;

import com.epam.booking.exception.NonExistentEventException;
import com.epam.booking.model.Event;
import com.epam.booking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event>  getAllEvents(){
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(long id){
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public List<Event> getEventsByTitle(String title, int pagesize, int pagenum){
        return eventRepository.getEventsByTitleContains(title , PageRequest.of(pagenum, pagesize));
    }

    public List<Event> getEventsByDate(Date date, int pagesize, int pagenum){
        return eventRepository.getEventsByDate(date, PageRequest.of(pagenum, pagesize));
    }

    public Event updateEvent(Event event){
        if( eventRepository.existsById(event.getId())){
            return eventRepository.save(event);
        }else{
            throw new NonExistentEventException();
        }
    }


    public void deleteEvent(Event event){
        if( eventRepository.existsById(event.getId())){
             eventRepository.delete(event);
        }else{
            throw new NonExistentEventException();
        }
    }


}
