package com.example.mk.cc;

/**
 * Created by M&K on 09/07/2017.
 */

public class ObjectDefect {
    private String nome;
    private int contagem;

    public ObjectDefect(String nome, int contagem) {
        this.nome = nome;
        this.contagem = contagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getContagem() {
        return contagem;
    }

    public void setContagem(int contagem) {
        this.contagem = contagem;
    }
}