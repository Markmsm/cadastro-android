package com.example.mk.cc;

import java.util.Date;

/**
 * Created by ODETE on 30/03/2017.
 */
public class ObjectOs {
    private int id;
    private int idClient;
    private String descricao;
    private String marca;
    private String defeito;
    private String voltagem;
    private String acessorios;
    private String observacao;
    private boolean isVisible;
    private String creationDate;
    private String deleteDate;
    private String executedService;
    private int valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() { return idClient; }

    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVoltagem() { return voltagem; }

    public void setVoltagem(String voltagem) { this.voltagem = voltagem; }

    public String getMarca() { return marca; }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(String acessorios) {
        this.acessorios = acessorios;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isVisible() { return isVisible; }

    public void setVisible(boolean visible) { isVisible = visible; }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getExecutedService() { return executedService; }

    public void setExecutedService(String executedService) { this.executedService = executedService; }

    public int getValor() { return valor; }

    public void setValor(int valor) { this.valor = valor; }
}
