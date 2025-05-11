package com.ticket_platform.ticket_platform.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private Integer idUtente;

    private String username;

    @Column(name = "nome_utente")
    private String nome;

    @Column(name = "cognome_utente")
    private String cognome;

    private String password;

    private boolean disponibile;

    @ManyToMany
    @JoinTable(
            name = "utente_role",
            joinColumns = @JoinColumn(name = "id_utente", referencedColumnName = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    )
    private List<Role> role;

    @OneToMany(mappedBy = "utente")
    private List<Ticket> ticket;

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
