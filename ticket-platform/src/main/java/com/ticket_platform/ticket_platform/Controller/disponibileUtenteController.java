package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.disponibileUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class disponibileUtenteController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private disponibileUtenteService disponibileUtenteService;

    @PostMapping("/path")
    public String gestioneDisponibilitaUtente( @RequestParam(value = "disponibilitaUtente",required = false, defaultValue = "false")
                                                   boolean checkDisponibile,
                                               Principal principal){
        disponibileUtenteService.disponibilitaUtente(checkDisponibile,principal);
        return "redirect:/";
    }
}
