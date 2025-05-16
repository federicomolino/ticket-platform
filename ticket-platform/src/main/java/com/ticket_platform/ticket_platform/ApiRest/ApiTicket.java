package com.ticket_platform.ticket_platform.ApiRest;

import com.ticket_platform.ticket_platform.Entity.Categoria;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Exception.ExceptionNessunTicket;
import com.ticket_platform.ticket_platform.Exception.ExceptionNessunaCategoria;
import com.ticket_platform.ticket_platform.Exception.ExceptionStatoNonValido;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ticket")
public class ApiTicket {

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private categoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<?> showTicket(){

        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()){
            throw new ExceptionNessunTicket("TK500_NESSUN_TICKET_PRESENTE");
        }
        return ResponseEntity.ok(tickets);
    }

    //Filtro per categoria
    @GetMapping("/{id}")
    public ResponseEntity<?> showTicketFiltroCategoria(@PathVariable("id") Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        //Verifico se la categoria esiste
        if (categoria.isEmpty()){
            throw new ExceptionNessunaCategoria("TK404_CATEGORIA_NON_VALIDA");
        }
        List<Ticket> ticket = ticketRepository.findAll();
        List<Ticket> ticketFiltrati = new ArrayList<>();
        for (Ticket t : ticket){
            for (Categoria c : t.getCategoria()){
                //Verifico che id categoria del ticket sia uguale all'id passato
                if (c.getIdCategoria().equals(categoria.get().getIdCategoria())){
                    ticketFiltrati.add(t);
                }
            }
        }

        if (ticketFiltrati.isEmpty()){
            throw new ExceptionNessunTicket("TK500_NESSUN_TICKET_CON_CATEGORIA_SELEZIONATA");
        }
        return ResponseEntity.ok(ticketFiltrati);
    }

    //filtro per stato
    @GetMapping("/stato")
    public ResponseEntity<?> showTicketPerStato(@RequestParam(name = "stato", required = true)String statoTicket){
        List<Ticket> ticket = ticketRepository.findAll();
        List<Ticket> ticketFilito = new ArrayList<>();

        if (statoTicket.equals("DA_FARE") || statoTicket.equals("IN_CORSO") || statoTicket.equals("COMPLETATO")){
            for (Ticket t : ticket){
                if (t.getStato().equals(statoTicket)){
                    ticketFilito.add(t);
                }
            }
            if (ticketFilito.isEmpty()){
                throw new ExceptionStatoNonValido("TK500_NESSUN_TICKET_CON_STATO_PASSATO");
            }
            return ResponseEntity.ok(ticketFilito);
        }else {
            throw new ExceptionStatoNonValido("TK404_STATO_NON_VALIDO");
        }
    }
}
