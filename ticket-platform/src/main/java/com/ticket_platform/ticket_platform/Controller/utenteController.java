package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import com.ticket_platform.ticket_platform.Service.disponibileUtenteService;
import com.ticket_platform.ticket_platform.Service.loginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("ticket")
public class utenteController {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private disponibileUtenteService disponibileUtenteService;

    @Autowired
    private loginService loginService;

    @GetMapping("/editUtente")
    public String showEditUtente(Model model, Principal principal){
        Optional<Utente> utenteLoggato = disponibileUtenteService.recuperoUtente(principal);
        model.addAttribute("utente", utenteLoggato);
        model.addAttribute("editUtente",utenteLoggato);
        return "LoginRegister/EditUtente";
    }

    @PostMapping("editUtente")
    public String editDatiUtente(@Valid @ModelAttribute("editUtente") Utente utente, BindingResult bindingResult,
                                 Principal principal, HttpServletRequest request){
        Optional<Utente> utenteLoggato = disponibileUtenteService.recuperoUtente(principal);


        Optional<Utente> emailEsistente = utenteRepository.findByEmail(utente.getEmail());
        if (emailEsistente.isPresent() && !emailEsistente.get().getIdUtente().equals(utenteLoggato.get().getIdUtente())){
            bindingResult.rejectValue("email", "ErrorEmail", "Email già inserita");
            return "LoginRegister/EditUtente";
        }

        Optional<Utente> usernameEsistente = utenteRepository.findByUsername(utente.getUsername());
        if (usernameEsistente.isPresent() && !usernameEsistente.get().getIdUtente().equals(utenteLoggato.get().getIdUtente())) {
            bindingResult.rejectValue("username", "ErrorUsername", "Username già inserito");
            return "LoginRegister/EditUtente";
        }

        if (bindingResult.hasErrors()){
            return "LoginRegister/EditUtente";
        }
        loginService.editUtente(utente,principal);

        //Invalido la sessione
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate(); // invalida la sessione
        }

        SecurityContextHolder.clearContext(); // pulisce il contesto di sicurezza
        return "redirect:/login?logout";
    }
}
