package com.ticket_platform.ticket_platform.Repository;

import com.ticket_platform.ticket_platform.Entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface notaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findByTicketId (Integer id);
}
