package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Categoria;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoriaService {

    @Autowired
    private categoriaRepository categoriaRepository;

    @Autowired
    private ticketRepository ticketRepository;

    public Categoria addCategoria(Categoria categoriaForm){
        return categoriaRepository.save(categoriaForm);
    }

    public boolean cancellaCategoria(Integer id){
        Categoria categoria = categoriaRepository.findById(id).get();
        List<Ticket> tickets = categoria.getTickets();
        for (Ticket t : tickets){
            return false;
        }
        categoriaRepository.delete(categoria);
        return true;
    }
}
