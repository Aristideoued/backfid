package com.wuri.demowuri.dto;

import com.wuri.demowuri.model.Produit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CategorieDto {

    private String libelle;
    private String description;

    private List<Produit> produits = new ArrayList<>();

    private List<UUID> produitIds = new ArrayList<>();

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

    public List<UUID> getProduitIds() {
        return produitIds;
    }

    public void setProduitIds(List<UUID> produitIds) {
        this.produitIds = produitIds;
    }
}
