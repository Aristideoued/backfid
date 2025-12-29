package com.wuri.demowuri.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.services.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // 🔹 Créer un document
    @PostMapping
    public ResponseEntity<DocumentDto> create(@RequestBody DocumentDto dto) {
        DocumentDto created = documentService.create(dto);
        return ResponseEntity.ok(created);
    }

    // 🔹 Mettre à jour un document
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> update(@PathVariable Long id, @RequestBody DocumentDto dto) {
        DocumentDto updated = documentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Supprimer un document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Récupérer un document par ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getById(@PathVariable Long id) {
        DocumentDto document = documentService.getById(id);
        return ResponseEntity.ok(document);
    }

    // 🔹 Récupérer tous les documents
    @GetMapping
    public ResponseEntity<List<DocumentDto>> findAll() {
        List<DocumentDto> documents = documentService.findAll();
        return ResponseEntity.ok(documents);
    }

    // 🔹 Récupérer les documents par type (ID)
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<DocumentDto>> getByType(@PathVariable Long typeId) {
        List<DocumentDto> documents = documentService.getByType(typeId);
        return ResponseEntity.ok(documents);
    }

    // 🔹 Récupérer les documents par autorité (ID)
    @GetMapping("/autorite/{autoriteId}")
    public ResponseEntity<List<DocumentDto>> getByAutorite(@PathVariable Long autoriteId) {
        List<DocumentDto> documents = documentService.getByAutorite(autoriteId);
        return ResponseEntity.ok(documents);
    }

    // 🔹 Récupérer les documents par libelle du type et id de la personne
    @GetMapping("/search")
    public ResponseEntity<List<DocumentDto>> getByTypeLibelleAndPersonneId(
            @RequestParam String typeLibelle,
            @RequestParam Long personneId) {

        List<DocumentDto> documents = documentService.getByTypeLibelleAndPersonneId(typeLibelle, personneId);
        return ResponseEntity.ok(documents);
    }
}

