package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.disponibileUtenteService;
import com.ticket_platform.ticket_platform.Service.ticketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class indexHome {

    @Autowired
    private ticketService ticketService;

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private disponibileUtenteService disponibileUtenteService;

    @GetMapping("/")
    public String showIndexHome(@RequestParam(name = "titolo", required = false) String titoloTicket, Model model,
                                Principal principal){
        Optional<Utente> utente = disponibileUtenteService.recuperoUtente(principal);

        if (utente.isPresent()){
            Utente utenteloggato = utente.get();
            model.addAttribute("utenteloggato", utenteloggato);
            model.addAttribute("listTicket", ticketService.showTicket(titoloTicket));
            model.addAttribute("utente", utenteRepository.findAll());
        }
        return "homeTicket/index";
    }
}
