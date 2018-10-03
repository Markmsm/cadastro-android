package com.example.mk.cc;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by M&K on 12/03/2017.
 */
public class ObjectClient {
    private int id;
    private String name;
    private String adress;
    private String phone;
    private String email;
    private String cpf;
    private boolean isVisible;
    private Date creationDate;
    private Date deleteDate;
    private ArrayList<ObjectOs> ordensServico;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public boolean isVisible() { return isVisible; }

    public void setVisible(boolean visible) { isVisible = visible; }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeleteDate() { return deleteDate; }

    public void setDeleteDate(Date deleteDate) { this.deleteDate = deleteDate; }

    public ArrayList<ObjectOs> getOrdensServico() { return ordensServico; }

    public void setOrdensServico(ArrayList<ObjectOs> ordensServico) { this.ordensServico = ordensServico; }
}
