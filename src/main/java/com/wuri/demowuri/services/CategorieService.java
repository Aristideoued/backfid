package com.wuri.demowuri.services;

import com.wuri.demowuri.dto.CategorieDto;
import com.wuri.demowuri.model.Categorie;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    Categorie creerCategorie(Categorie categorie);

    Categorie modifierCategorie(CategorieDto categorieDto, UUID id);

    void deleteCategorie(UUID id);

    Categorie showCategorie(UUID id);

    List<Categorie> listeCategorie();
}
