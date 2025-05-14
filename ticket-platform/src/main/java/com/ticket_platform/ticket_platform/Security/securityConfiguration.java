package com.ticket_platform.ticket_platform.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class securityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("/login/**").permitAll()
            .requestMatchers("/ticket/newTicket","/ticket/delete/**").hasAuthority("ADMIN")
            .requestMatchers("/", "/**").authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error=true") // Usa il parametro `error=true` per mostrare l'errore
            .permitAll()
            .and()
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            );
        return http.build();
    }

    @Bean
    databaseUserDetailsService userDetailService(){
        return new databaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
