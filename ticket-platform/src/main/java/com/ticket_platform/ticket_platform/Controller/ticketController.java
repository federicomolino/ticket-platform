package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Repository.notaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.ticketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ticket")
public class ticketController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private categoriaRepository categoriaRepository;

    @Autowired
    private ticketService ticketService;

    @Autowired
    private notaRepository notaRepository;

    @GetMapping("newTicket")
    public String newTicket(Model model){
        model.addAttribute("formNewTicket", new Ticket());
        model.addAttribute("listUtente", utenteRepository.findAll());
        model.addAttribute("listCategoria", categoriaRepository.findAll());
        return "homeTicket/newTicket";
    }

    @PostMapping("newTicket")
    public String newTicket(@Valid @ModelAttribute("formNewTicket") Ticket ticketForm, BindingResult bindingResult, Model model,
                            @RequestParam(value = "utenteSelezionato", required = false)Integer utenteSelezionatoId,
                            @RequestParam(value = "categoriaSelezionata", required = false) List<Integer> categoriaSelezionataId){

        if (utenteSelezionatoId == null){
            bindingResult.rejectValue("utente","errorUtente","Selezionare l'utente");
        } else if (categoriaSelezionataId == null || categoriaSelezionataId.isEmpty()) {
            bindingResult.rejectValue("categoria", "errorCategoria","Selezionare almeno una categoria");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("listUtente", utenteRepository.findAll());
            model.addAttribute("listCategoria", categoriaRepository.findAll());
            return "homeTicket/newTicket";
        }

        ticketService.newTicket(ticketForm, utenteSelezionatoId, categoriaSelezionataId);
        return "redirect:/";
    }

    @GetMapping("infoTicket/{id}")
    public String showInfoTicket(Model model, @PathVariable("id") Integer id){
        Ticket ticket = ticketRepository.findById(id).get();
        model.addAttribute("ticket",ticket);
        model.addAttribute("listUtente", ticket.getUtente());
        model.addAttribute("listCategoria", ticket.getCategoria());
        model.addAttribute("listNote", ticket.getNota());
        return "homeTicket/infoTicket";
    }

    @GetMapping("editTicket/{id}")
    public String editTicketShow(Model model, @PathVariable("id") Integer id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            model.addAttribute("formEditTicket", ticket.get());
            model.addAttribute("listUtente", utenteRepository.findAll());
            model.addAttribute("listCategoria", categoriaRepository.findAll());
        }
        return "homeTicket/editTicket";
    }

    @PostMapping("editTicket/{id}")
    public String editTicket(@Valid @PathVariable("id")Integer id,@Valid @ModelAttribute("formEditTicket") Ticket ticketForm,
                             BindingResult bindingResult,
                             @RequestParam(value = "categoriaSelezionata", required = false) List<Integer> categoriaSelezionataId,
                             @RequestParam(value = "nota", required = false) String nota, Model model){

        if (categoriaSelezionataId == null || categoriaSelezionataId.isEmpty()) {
            bindingResult.rejectValue("categoria", "errorCategoria","Selezionare almeno una categoria");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("listUtente", utenteRepository.findAll());
            model.addAttribute("listCategoria", categoriaRepository.findAll());
            return "homeTicket/editTicket";
        }

        ticketService.editTicket(ticketForm,ticketForm.getUtente().getIdUtente(),categoriaSelezionataId,nota);

        return "redirect:/ticket/infoTicket/" + id;
    }
}
