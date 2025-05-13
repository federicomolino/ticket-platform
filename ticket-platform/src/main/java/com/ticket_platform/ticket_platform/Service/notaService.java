package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Nota;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Repository.notaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class notaService {

    @Autowired
    private notaRepository notaRepository;

    @Autowired
    private ticketRepository ticketRepository;

    public Nota newNota(Integer id, Nota notaForm){
        Ticket ticket = ticketRepository.findById(id).get();

        //Creo nuova nota
        Nota nota = new Nota();
        nota.setNota(notaForm.getNota());
        nota.setData(notaForm.getData());
        //Assegno al ticket di riferimento la nota
        nota.setTicket(ticket);
        return notaRepository.save(nota);
    }
}
