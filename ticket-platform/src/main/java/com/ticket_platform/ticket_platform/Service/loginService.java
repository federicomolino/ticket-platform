package com.ticket_platform.ticket_platform.Service;

import com.ticket_platform.ticket_platform.Entity.Role;
import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.roleRepository;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class loginService {

    @Autowired
    private utenteRepository utenteRepository;

    @Autowired
    private roleRepository roleRepository;

    public Utente newUtente(Utente formUtente, List<String> ruoli){

        //Assegno i ruoli all'utente
        List<Role> ruoliAssengati = new ArrayList<>();
        if (ruoli != null && !ruoli.isEmpty()){
            for (String r : ruoli){
                Role rNome = roleRepository.findByNomeRegola(r);
                ruoliAssengati.add(rNome);
            }
        }else{
            //Se nessun ruolo, in automatico user
            Role defaultRuolo = roleRepository.findByNomeRegola("USER");
            ruoliAssengati.add(defaultRuolo);
        }
        formUtente.setRole(ruoliAssengati);

        //setto la password e poi salvo
        formUtente.setPassword("{noop}" + formUtente.getPassword());

        return utenteRepository.save(formUtente);
    }
}
