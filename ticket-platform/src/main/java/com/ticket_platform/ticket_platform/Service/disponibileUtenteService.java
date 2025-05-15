package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class disponibileUtenteService {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private ticketRepository ticketRepository;

    //recupera utente
    public Optional<Utente> recuperoUtente(Principal principal){
        String username = principal.getName();
        Optional<Utente> utente = utenteRepository.findByUsername(username);
        return utente;
    }

    //check disponibilità
    public boolean disponibilitaUtente(boolean checkDisponibile, Principal principal){
        Optional<Utente> utente = recuperoUtente(principal);

        Utente utenteLoggato = utente.get();
        List<Ticket> tickets = ticketRepository.findByUtente(utenteLoggato);
        for (Ticket ticket : tickets){
            if (ticket.getStato().equals("DA_FARE") || ticket.getStato().equals("IN_CORSO")){
                return false;
            }
        }

        utenteLoggato.setDisponibile(checkDisponibile);
        utenteRepository.save(utenteLoggato);

        return true;
    }
}