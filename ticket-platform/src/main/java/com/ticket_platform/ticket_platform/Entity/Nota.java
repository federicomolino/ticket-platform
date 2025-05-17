package com.ticket_platform.ticket_platform.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Nota {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer id;

    @Valid
    @NotBlank(message = "La nota non può essere vuota")
    private String nota;

    @ManyToOne
    private Ticket ticket;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utenteNote;

    public Utente getUtenteNote() {
        return utenteNote;
    }

    public void setUtenteNote(Utente utente) {
        this.utenteNote = utente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
