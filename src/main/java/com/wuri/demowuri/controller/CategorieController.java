package com.wuri.demowuri.controller;

import com.wuri.demowuri.dto.CategorieDto;
import com.wuri.demowuri.model.Categorie;
import com.wuri.demowuri.repository.CategorieRepository;
import com.wuri.demowuri.services.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping("creer")
    public ResponseEntity<Categorie> creerCategorie(@RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.creerCategorie(categorie));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Categorie> updateCategorie(@RequestBody CategorieDto categorieDto, @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(categorieService.modifierCategorie(categorieDto, id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable UUID id) {
        categorieService.deleteCategorie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("liste")
    public ResponseEntity<List<Categorie>> listeCategorie() {
        return ResponseEntity.status(HttpStatus.OK).body(categorieService.listeCategorie());
    }

    @GetMapping("show/{id}")
    public ResponseEntity<Categorie> showCategorie(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(categorieService.showCategorie(id));
    }
}
