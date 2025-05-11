package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Categoria;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ticketService {

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private categoriaRepository categoriaRepository;

    public List<Ticket> showTicket(String titoloTicket){
        List<Ticket> ticket;
        if (titoloTicket == null || titoloTicket.isEmpty()){
            ticket = ticketRepository.findAll();
        }else {
            ticket = ticketRepository.findByTitoloTicketContainingIgnoreCase(titoloTicket);
        }
        return ticket;
    }

    public Ticket newTicket(Ticket ticketForm, Integer utenteSelezionatoId, List<Integer> categoriaSelezionataId){
        //Assegno categoria
        List<Categoria> categoriaSelezionata = categoriaRepository.findAllById(categoriaSelezionataId);
        ticketForm.setCategoria(categoriaSelezionata);
        //Assegno utente
        Utente utente = utenteRepository.findByidUtente(utenteSelezionatoId).get();
        ticketForm.setUtente(utente);
        return ticketRepository.save(ticketForm);
    }
}
