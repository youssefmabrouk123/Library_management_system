package com.example.library.model;

import java.sql.Date;

public class Abonnement {
    private int idAbonnement;
    private Date creationDate;
    private double frais;
    private int listeEmprunt;

    public Abonnement(int idAbonnement, Date creationDate, double frais, int listeEmprunt) {
        this.idAbonnement = idAbonnement;
        this.creationDate = creationDate;
        this.frais = frais;
        this.listeEmprunt = listeEmprunt;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public int getListeEmprunt() {
        return listeEmprunt;
    }

    public void setListeEmprunt(int listeEmprunt) {
        this.listeEmprunt = listeEmprunt;
    }
}
