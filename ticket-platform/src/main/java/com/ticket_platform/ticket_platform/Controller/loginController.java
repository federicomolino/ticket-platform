package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.disponibileUtenteService;
import com.ticket_platform.ticket_platform.Service.loginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private loginService loginService;

    @Autowired
    private disponibileUtenteService disponibileUtenteService;

    @GetMapping()
    public String login(Model model){
        model.addAttribute("UtenteFormLogin", new Utente());
        return "LoginRegister/Login";
    }

    @GetMapping("register")
    public String showRegisterUtente(Model model, Principal principal){
        Optional<Utente> utente = disponibileUtenteService.recuperoUtente(principal);
        if (utente.isPresent()){
            model.addAttribute("utente",utente.get());
        }
        model.addAttribute("UtenteForm", new Utente());
        return "LoginRegister/RegisterUtente";
    }

    @PostMapping("register")
    public String showRegisterUtente(@Valid @ModelAttribute("UtenteForm") Utente utenteForm, BindingResult bindingResult,
                                     @RequestParam(name = "ruoli", required = false)List<String> ruoli, Model model,
                                     Principal principal){

        Optional<Utente> utente = disponibileUtenteService.recuperoUtente(principal);
        if (utente.isPresent()) {
            model.addAttribute("utente", utente.get());
        }

        if (utenteRepository.findByUsername(utenteForm.getUsername()).isPresent()){
            bindingResult.rejectValue("username", "ErrorUsername", "Username già inserito");
            return "LoginRegister/RegisterUtente";
        }else if (utenteRepository.findByEmail(utenteForm.getEmail()).isPresent()){
            bindingResult.rejectValue("email", "ErrorEmail", "Email già inserita");
            return "LoginRegister/RegisterUtente";
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("UtenteForm", utenteForm);
            return "LoginRegister/RegisterUtente";
        }

        loginService.newUtente(utenteForm,ruoli);
        return "redirect:/";
    }
}
