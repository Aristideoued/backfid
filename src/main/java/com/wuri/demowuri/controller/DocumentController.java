package com.wuri.demowuri.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.services.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // 🔹 Créer un document
    @PostMapping("creer")
    public ResponseEntity<DocumentDto> create(@RequestBody DocumentDto dto) {
        DocumentDto created = documentService.create(dto);
        return ResponseEntity.ok(created);
    }

    // 🔹 Mettre à jour un document
    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentDto> update(@PathVariable Long id, @RequestBody DocumentDto dto) {
        DocumentDto updated = documentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Supprimer un document
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Récupérer un document par ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<DocumentDto> getById(@PathVariable Long id) {
        DocumentDto document = documentService.getById(id);
        return ResponseEntity.ok(document);
    }

    // 🔹 Récupérer tous les documents
    @GetMapping("all")
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

    @GetMapping("/personnes/{id}/documents")
    public List<DocumentDto> getDocumentsByPersonne(@PathVariable Long id) {
        return documentService.getByPersonne(id);
    }

    @PostMapping(value = "/{documentId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadPhoto(
            @PathVariable Long documentId,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Fichier photo vide");
        }

        String path = documentService.uploadPhoto(documentId, file);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Photo uploadée avec succès",
                        "path", path));
    }

    @GetMapping("/photo/{documentId}")
    public ResponseEntity<Resource> getPhoto(@PathVariable Long documentId) throws IOException {

        Resource photo = documentService.getPhoto(documentId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // photo.png
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo.png\"")
                .body(photo);
    }

}
