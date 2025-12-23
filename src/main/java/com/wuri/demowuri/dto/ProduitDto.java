package com.wuri.demowuri.dto;

import com.wuri.demowuri.model.Categorie;
import lombok.Data;

@Data
public class ProduitDto {

    private String nom;
    private String description;
    private  int prix;
    private Categorie categorie;

}
