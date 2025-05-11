package com.ticket_platform.ticket_platform.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer id;

    @Column(name = "titolo_ticket")
    @Valid
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titoloTicket;

    @Valid
    @Size(max = 20, message = "Il limite massimo è di 20 caratteri")
    private String descrizione;

    private String stato;

    @ManyToOne
    private Utente utente;

    @OneToMany(mappedBy = "ticket")
    private List<Nota> nota;

    @OneToMany(mappedBy = "ticket")
    private List<Categoria> categoria;

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }

    public List<Nota> getNota() {
        return nota;
    }

    public void setNota(List<Nota> nota) {
        this.nota = nota;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitoloTicket() {
        return titoloTicket;
    }

    public void setTitoloTicket(String titoloTicket) {
        this.titoloTicket = titoloTicket;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
