package com.wuri.demowuri.serviceImpl;

import com.wuri.demowuri.model.Categorie;
import com.wuri.demowuri.model.Produit;
import com.wuri.demowuri.repository.CategorieRepository;
import com.wuri.demowuri.repository.ProduitRepository;
import com.wuri.demowuri.services.ProduitService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Produit creerProduit(Produit produit){
        return  this.produitRepository.save(produit);
    }

    @Override
    public Produit modifierProduit(Produit produit, UUID id){
        Produit produitExisted = produitRepository.findById(id).get();
        if (produitExisted != null){
            produitExisted.setNom(produit.getNom());
            produitExisted.setPrix(produit.getPrix());
            produitExisted.setDescription(produit.getDescription());
            return produitRepository.save(produitExisted);
        }else {
            return null;
        }
    }

    @Override
    public List<Produit> listerProduits(){
        return produitRepository.findAll();
    }

    @Override
    public Produit showProduit(UUID id){
        return produitRepository.findById(id).get();
    }

    @Override
    public void deleteProduit(UUID id){
        produitRepository.deleteById(id);
    }

    @Override
    public Produit affecterProduitCategorie(UUID idProduit, UUID idCategorie){
        Produit produit = produitRepository.findById(idProduit).orElseThrow( ()-> new RuntimeException("Produit non retrouvé avec id:"+idProduit));
        Categorie categorie = categorieRepository.findById(idCategorie).orElseThrow(() -> new RuntimeException("Categorie non retrouvée avec id:"+idCategorie));
        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }

    @Override
    public List<String> readCurrentLogFile(String logFilePath){
      
        List<String> logLines =new ArrayList<>();

        try(BufferedReader reader =new BufferedReader(new FileReader (logFilePath))){
            String line;
            while ((line=reader.readLine())!=null){
                logLines.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return logLines;

    }

    @Override
    public List<String> readLogFileByDate(String logDirectory,String date){
        List<String> logLines=new ArrayList<>();
        String fileName= "demowuri."+date+".log";
        Path logPath=Paths.get(logDirectory, fileName);
        if(!Files.exists(logPath)){
            System.err.println("Fichier de log introuvable : "+logPath);
            return logLines;
        }
        try (BufferedReader reader =new BufferedReader(new FileReader(logPath.toFile()))){
            String line;
            while ((line=reader.readLine())!=null){
                logLines.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return logLines;

    }

}
