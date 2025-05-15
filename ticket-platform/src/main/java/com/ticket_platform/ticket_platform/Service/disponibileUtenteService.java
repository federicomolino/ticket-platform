package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class disponibileUtenteService {

    @Autowired
    private utenteRepository utenteRepository;

    //recupera utente
    public Optional<Utente> recuperoUtente(Principal principal){
        String username = principal.getName();
        Optional<Utente> utente = utenteRepository.findByUsername(username);
        return utente;
    }

    //check disponibilità
    public void disponibilitaUtente(boolean checkDisponibile, Principal principal){
        String username = principal.getName();
        Optional<Utente> utente = utenteRepository.findByUsername(username);
        if (utente.isPresent()){
            Utente usernameUtente = utente.get();
            usernameUtente.setDisponibile(checkDisponibile);
            utenteRepository.save(usernameUtente);
        }
    }
}
