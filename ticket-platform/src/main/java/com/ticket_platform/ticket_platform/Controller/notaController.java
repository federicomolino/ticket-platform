package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Nota;
import com.ticket_platform.ticket_platform.Entity.Ticket;
import com.ticket_platform.ticket_platform.Repository.notaRepository;
import com.ticket_platform.ticket_platform.Repository.ticketRepository;
import com.ticket_platform.ticket_platform.Service.notaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("ticket")
public class notaController {

    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private notaRepository notaRepository;

    @Autowired
    private notaService notaService;

    @GetMapping("newNota/{id}")
    public String showNewNota(Model model, @PathVariable("id")Integer id){
        Ticket ticket = ticketRepository.findById(id).get();
        Optional<Nota> nota = notaRepository.findById(id);
        if (nota.isPresent()){
            model.addAttribute("nota", nota.get());
            model.addAttribute("utente", ticketRepository.findUtenteById(id));
        }
        model.addAttribute("ticket",ticket);
        model.addAttribute("formNewNota",new Nota());
        model.addAttribute("utente", ticketRepository.findUtenteById(id));
        return "Note/newNota";
    }

    @PostMapping("newNota/{id}")
    public String newNota(@Valid @ModelAttribute("formNewNota") Nota notaForm, BindingResult bindingResult,
                          @PathVariable("id")Integer id, Model model){
        if (notaForm.getData().isAfter(LocalDate.now())){
            bindingResult.rejectValue("data","dataError", "La data inserita non è corretta," +
                    "non può essere superiore ad oggi");
        }

        if (bindingResult.hasErrors()){
            Ticket ticket = ticketRepository.findById(id).get();
            model.addAttribute("ticket", ticket);
            return "Note/newNota";
        }
        notaService.newNota(id,notaForm);
        return "redirect:/ticket/infoTicket/" + id;
    }
}
