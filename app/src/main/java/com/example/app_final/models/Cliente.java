package com.example.app_final.models;

import java.io.Serializable;

public class Cliente implements Serializable {
    private Integer id;
    private  String nome;

    public  Cliente (Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
