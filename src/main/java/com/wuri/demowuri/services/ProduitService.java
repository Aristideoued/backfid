package com.wuri.demowuri.services;

import com.wuri.demowuri.model.Produit;

import java.util.List;
import java.util.UUID;

public interface ProduitService {

    Produit creerProduit(Produit produit);

    Produit modifierProduit(Produit produit, UUID id);

    List<Produit> listerProduits();

    Produit showProduit(UUID id);

    void deleteProduit(UUID id);

    Produit affecterProduitCategorie(UUID idProduit, UUID idCategorie);
    
    List<String> readLogFileByDate(String logDirectory,String date);
    List<String> readCurrentLogFile(String logFilePath);
}
