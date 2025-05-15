package com.ticket_platform.ticket_platform.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private Integer idUtente;

    @Column(unique = true)
    @Valid
    @NotBlank(message = "Username non valido")
    private String username;

    @Column(name = "nome_utente")
    @NotBlank(message = "Nome non valido")
    private String nome;

    @Column(name = "cognome_utente")
    @NotBlank(message = "Cognome non valido")
    private String cognome;

    @Valid
    @NotBlank(message = "Password non valida")
    private String password;

    @Valid
    @NotBlank(message = "Email non valida")
    private String email;

    private boolean disponibile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utente_role",
            joinColumns = @JoinColumn(name = "id_utente", referencedColumnName = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    )
    private List<Role> role;

    @OneToMany(mappedBy = "utente")
    private List<Ticket> ticket;

    @OneToMany(mappedBy = "utenteNote")
    private List<Nota> note;

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
