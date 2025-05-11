package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.ticketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("ticket")
public class ticketController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private categoriaRepository categoriaRepository;

    @Autowired
    private ticketService ticketService;

    @GetMapping("newTicket")
    public String newTicket(Model model){
        model.addAttribute("formNewTicket", new Ticket());
        model.addAttribute("listUtente", utenteRepository.findAll());
        model.addAttribute("listCategoria", categoriaRepository.findAll());
        return "homeTicket/newTicket";
    }

    @PostMapping("newTicket")
    public String newTicket(@Valid @ModelAttribute("formNewTicket") Ticket ticketForm, BindingResult bindingResult, Model model,
                            @RequestParam(value = "utenteSelezionato")Integer utenteSelezionatoId,
                            @RequestParam(value = "categoriaSelezionata") List<Integer> categoriaSelezionataId){

        if (utenteSelezionatoId == null){
            bindingResult.rejectValue("utente","errorUtente","Selezionare l'utente");
        } else if (categoriaSelezionataId == null || categoriaSelezionataId.isEmpty()) {
            bindingResult.rejectValue("categoriaSelezionata","errorCategoria","Selezionare la Categoria");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("listUtente", utenteRepository.findAll());
            model.addAttribute("listCategoria", categoriaRepository.findAll());
            return "homeTicket/newTicket";
        }

        ticketService.newTicket(ticketForm, utenteSelezionatoId, categoriaSelezionataId);
        return "redirect:/";
    }
}
