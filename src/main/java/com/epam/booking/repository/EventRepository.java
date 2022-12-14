package com.epam.booking.repository;

import com.epam.booking.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsByTitleContains(String title , Pageable page);

    List<Event> getEventsByDate(Date date, Pageable page);

}
