package com.ticket_platform.ticket_platform.Repository;

import com.ticket_platform.ticket_platform.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ticketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByTitoloTicketContainingIgnoreCase(String titoloTicket);

}
