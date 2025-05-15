package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.disponibileUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class disponibileUtenteController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private disponibileUtenteService disponibileUtenteService;

    @Autowired
    private ticketRepository ticketRepository;

    @PostMapping("/path")
    public String gestioneDisponibilitaUtente(@RequestParam(value = "disponibilitaUtente",required = false, defaultValue = "false")
                                               boolean checkDisponibile,
                                              Principal principal, RedirectAttributes redirectAttributes){

        boolean disponibileUtente = disponibileUtenteService.disponibilitaUtente(checkDisponibile,principal);

        if(!disponibileUtente){
            redirectAttributes.addFlashAttribute("erroreDisponibilita",
                    "Non puoi modificare la disponibilità, ticket attivi.");
        }
        return "redirect:/";
    }
}