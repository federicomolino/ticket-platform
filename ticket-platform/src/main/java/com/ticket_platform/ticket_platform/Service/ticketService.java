package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.*;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.notaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ticketService {

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private categoriaRepository categoriaRepository;

    @Autowired
    private notaRepository notaRepository;

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

    public Ticket editTicket(Ticket ticketForm, Integer utenteSelezionatoId, List<Integer> categoriaSelezionataId, String nota){
        //Assegno categoria
        List<Categoria> categoriaSelezionata = ticketForm.getCategoria();
        ticketForm.setCategoria(categoriaSelezionata);
        Ticket editTicket = newTicket(ticketForm,utenteSelezionatoId,categoriaSelezionataId);
        return editTicket;
    }

    public void deleteTicket(Integer id){
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()){
            //recupero il ticket
            Ticket t = ticket.get();

            //setto a null l'utente
            if (t.getUtente() != null){
                t.setUtente(null);
                ticketRepository.save(t);
            }

            //setto a null eventuali note
            List<Nota> nota = notaRepository.findByTicketId(id);
            if (!nota.isEmpty()){
                for (Nota note : nota){
                    notaRepository.delete(note);
                }
            }
            ticketRepository.deleteById(id);
        }
    }

    public List<Ticket> ticketPerNomeETitolo(Utente utente, String titolo){
        if (titolo == null || titolo.isEmpty()){
            return ticketRepository.findByUtente(utente);
        }
        return ticketRepository.findByUtenteAndTitoloTicketContainingIgnoreCase(utente,titolo);
    }

    public List<Ticket> ticketPerUtente(Utente utenteLoggato, String titoloTicket){
        boolean isAdmin = false;
        for (Role role : utenteLoggato.getRole()){
            if ("ADMIN".equalsIgnoreCase(role.getNomeRegola())){
                isAdmin=true;
                break;
            }
        }
        List<Ticket> tickets;
        if (isAdmin){
            tickets = showTicket(titoloTicket);
        }else {
            tickets = ticketPerNomeETitolo(utenteLoggato,titoloTicket);
        }
        return tickets;
    }

}