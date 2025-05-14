package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.loginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private loginService loginService;

    @GetMapping()
    public String login(Model model){
        model.addAttribute("UtenteFormLogin", new Utente());
        return "LoginRegister/Login";
    }

    @GetMapping("register")
    public String showRegisterUtente(Model model){
        model.addAttribute("UtenteForm", new Utente());
        return "LoginRegister/RegisterUtente";
    }

    @PostMapping("register")
    public String showRegisterUtente(@Valid @ModelAttribute("UtenteForm") Utente utenteForm, BindingResult bindingResult,
                                     @RequestParam(name = "ruoli", required = false)List<String> ruoli){

        Optional<Utente> utenteUsername = utenteRepository.findByUsername(utenteForm.getUsername());
        List<Utente> utenteEmail = utenteRepository.findByEmail(utenteForm.getEmail());

        if (bindingResult.hasErrors()){
            return "LoginRegister/RegisterUtente";
        }

        loginService.newUtente(utenteForm,ruoli);
        return "redirect:/";
    }
}
