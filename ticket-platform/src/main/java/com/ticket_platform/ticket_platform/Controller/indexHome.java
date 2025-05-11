package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.ticketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexHome {

    @Autowired
    private ticketService ticketService;

    @Autowired
    private utenteRepository utenteRepository;

    @GetMapping("/")
    public String showIndexHome(@RequestParam(name = "titolo", required = false) String titoloTicket, Model model){
        model.addAttribute("listTicket", ticketService.showTicket(titoloTicket));
        model.addAttribute("utente", utenteRepository.findAll());
        return "homeTicket/index";
    }
}
