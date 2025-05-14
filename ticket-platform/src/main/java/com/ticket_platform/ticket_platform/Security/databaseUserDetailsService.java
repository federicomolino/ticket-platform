package com.ticket_platform.ticket_platform.Security;

import com.ticket_platform.ticket_platform.Entity.Utente;
import com.ticket_platform.ticket_platform.Repository.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class databaseUserDetailsService implements UserDetailsService {
    @Autowired
    private utenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> optUtente = utenteRepository.findByUsername(username);

        if (optUtente.isPresent()){
            return new dataBaseUserDetails(optUtente.get());
        }else {
            throw new UsernameNotFoundException("Utente non valido");
        }
    }
}
