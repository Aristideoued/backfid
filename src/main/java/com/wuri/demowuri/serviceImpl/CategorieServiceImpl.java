package com.wuri.demowuri.serviceImpl;

import com.wuri.demowuri.dto.CategorieDto;
import com.wuri.demowuri.model.Categorie;
import com.wuri.demowuri.repository.CategorieRepository;
import com.wuri.demowuri.services.CategorieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Categorie creerCategorie(Categorie categorie){
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie modifierCategorie(CategorieDto categorieDto, UUID id){
        Optional<Categorie> categorieOp = categorieRepository.findById(id);
        if (categorieOp.isPresent()){
            Categorie categorieEx = categorieOp.get();
            categorieEx.setLibelle(categorieDto.getLibelle());
            categorieEx.setDescription(categorieDto.getDescription());
            categorieEx.setProduits(categorieDto.getProduits());
            return categorieRepository.save(categorieEx);
        }else{
            return null;
        }
    }

    @Override
    public void deleteCategorie(UUID id){
        Optional<Categorie> categorieOp = categorieRepository.findById(id);
        if (categorieOp.isPresent()){
            Categorie categorieEx = categorieOp.get();
            categorieRepository.delete(categorieEx);
        }
    }

    @Override
    public Categorie showCategorie(UUID id){
        return categorieRepository.findById(id).get();
    }

    @Override
    public List<Categorie> listeCategorie(){
        return categorieRepository.findAll();
    }


}
