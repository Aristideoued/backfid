package com.wuri.demowuri.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wuri.demowuri.absctractClass.AbstractId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
@Entity
public class Produit extends AbstractId {
    private String nom;
    private String description;
    private  int prix;

    @ManyToOne
    @JoinColumn(name="categorie_id")
    @JsonBackReference
    private Categorie categorie;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
