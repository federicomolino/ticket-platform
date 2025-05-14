package com.ticket_platform.ticket_platform.Security;

import com.ticket_platform.ticket_platform.Entity.Role;
import com.ticket_platform.ticket_platform.Entity.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class dataBaseUserDetails implements UserDetails {

    private final Integer id_utente;
    private final String username;
    private final String password;
    //Lista che spring utilizza per validare le authorities
    private final List<GrantedAuthority> authorities;

    public dataBaseUserDetails(Utente utente) {
        this.id_utente = utente.getIdUtente();
        this.username = utente.getUsername();
        this.password = utente.getPassword();

        authorities = new ArrayList<>();
        for(Role role : utente.getRole()){
            authorities.add(new SimpleGrantedAuthority(role.getNomeRegola()));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
