package com.epam.booking.repository;

import com.epam.booking.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByEventId(Long eventId, Pageable page);

    List<Ticket> getTicketsByUserId(Long userId , Pageable page);
}
