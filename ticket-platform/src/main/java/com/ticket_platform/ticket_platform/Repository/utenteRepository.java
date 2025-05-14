package com.ticket_platform.ticket_platform.Repository;

import com.ticket_platform.ticket_platform.Entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface utenteRepository extends JpaRepository<Utente,Integer> {
    Optional<Utente> findByidUtente(Integer utenteSelezionatoId);

    Optional<Utente> findByUsername(String username);

    List<Utente> findByEmail(String email);
}
