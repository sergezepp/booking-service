package com.epam.booking.services;

import com.epam.booking.model.Event;
import com.epam.booking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
