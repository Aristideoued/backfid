package com.wuri.demowuri.controller;

import com.wuri.demowuri.model.Produit;
import com.wuri.demowuri.repository.ProduitRepository;
import com.wuri.demowuri.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1")
@Controller
public class ProduitController {

    private final ProduitService produitService;
    private final ProduitRepository produitRepository;

    public ProduitController(ProduitService produitService, ProduitRepository produitRepository) {
        this.produitService = produitService;
        this.produitRepository = produitRepository;
    }

    @PostMapping("produit/creer")
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit){
        Produit produitCreer =  produitService.creerProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitCreer);

    }


    @PutMapping("produit/update/{id}")
    public ResponseEntity<Produit> modiferProduit(@RequestBody Produit produit, @PathVariable UUID id){
        Produit produitUpdate = produitService.modifierProduit(produit,id);
        return ResponseEntity.status(HttpStatus.OK).body(produitUpdate);
    }

    @GetMapping("produits")
    public ResponseEntity<List<Produit>> listerProduits(){
        return ResponseEntity.status(HttpStatus.OK).body(produitService.listerProduits());
    }

    @GetMapping("produit/show/{id}")
    public ResponseEntity<Produit> showProduit(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(produitService.showProduit(id));
    }


    @GetMapping("/logsOld")
    public  List<String> getCurrentLogOld(){
        String currentLogFilePath="logs/demowuri.log";
        return produitService.readCurrentLogFile(currentLogFilePath);
    }

     @GetMapping("logs")
    public  ResponseEntity< List<String>> getCurrentLog(){
        String currentLogFilePath="logs/demowuri.log";
        List <String> logs= produitService.readCurrentLogFile(currentLogFilePath);
         return ResponseEntity.ok(logs);


    }



    @GetMapping("logs/date/{date}")
    public ResponseEntity<List<String>> getLogByDate(@PathVariable String date){
       List< String> logs=produitService.readLogFileByDate("logs", date);
       
       if(logs.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("Aucun log trouvé pour cette date"));
       }
       return ResponseEntity.ok(logs);
    }

    @DeleteMapping("produit/delete/{id}")
    public ResponseEntity<Object> deleteProduit(@PathVariable UUID id){
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent()){
            produitService.deleteProduit(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("produit/categorie/{idPro}/{idCat}")
    public ResponseEntity<Produit> affecterCategorie(@PathVariable UUID idPro, @PathVariable UUID idCat){
        return ResponseEntity.status(HttpStatus.OK).body(produitService.affecterProduitCategorie(idPro,idCat));
    }



}
