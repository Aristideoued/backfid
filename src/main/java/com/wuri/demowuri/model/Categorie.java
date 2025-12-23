package com.wuri.demowuri.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wuri.demowuri.absctractClass.AbstractId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
public class Categorie extends AbstractId {

    private String libelle;
    private String description;

    @OneToMany(mappedBy = "categorie")
    @JsonManagedReference
    private List<Produit> produits = new ArrayList<>();

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
