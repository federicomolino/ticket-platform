package com.ticket_platform.ticket_platform.Entity;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;

    @Column(name = "name_role")
    private String nomeRegola;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeRegola() {
        return nomeRegola;
    }

    public void setNomeRegola(String nomeRegola) {
        this.nomeRegola = nomeRegola;
    }
}
