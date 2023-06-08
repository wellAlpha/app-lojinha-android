package com.example.app_final.models;

public class Produto {
    private Integer id;
    private String nome;

    public Produto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id + "-" + nome;
    }
}
