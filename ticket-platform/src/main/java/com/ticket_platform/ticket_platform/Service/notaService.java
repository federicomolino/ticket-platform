package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Nota;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.notaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class notaService {

    @Autowired
    private notaRepository notaRepository;

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private utenteRepository utenteRepository;

    public Nota newNota(Integer id, Nota notaForm, Principal principal){
        Ticket ticket = ticketRepository.findById(id).get();

        //recupero l'utente loggato
        String username = principal.getName();
        Utente utente = utenteRepository.findByUsername(username).get();

        //Creo nuova nota
        Nota nota = new Nota();
        nota.setNota(notaForm.getNota());
        nota.setData(notaForm.getData());
        //Assegno al ticket di riferimento la nota
        nota.setTicket(ticket);
        nota.setUtenteNote(utente);
        return notaRepository.save(nota);
    }
}
