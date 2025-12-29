package com.wuri.demowuri.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wuri.demowuri.dto.QrCodeDto;
import com.wuri.demowuri.dto.QrVerificationResponse;
import com.wuri.demowuri.enums.EtatDocument;
import com.wuri.demowuri.model.Personne;
import com.wuri.demowuri.repository.DocumentRepository;
import com.wuri.demowuri.repository.PersonneRepository;
import com.wuri.demowuri.securite.QrJwtService;
import com.wuri.demowuri.services.QrCodeService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/qrcodes")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService service;

    private final QrJwtService qrJwtService;

    private final PersonneRepository personneRepository;
    private final DocumentRepository documentRepository;

    @PostMapping
    public ResponseEntity<QrCodeDto> create(@RequestBody QrCodeDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QrCodeDto> update(@PathVariable Long id, @RequestBody QrCodeDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QrCodeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<QrCodeDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/verify")
    public ResponseEntity<QrVerificationResponse> verify(@RequestParam String token) {

        Claims claims = qrJwtService.parseToken(token);

        Long personneId = claims.get("personneId", Long.class);

        Personne personne = personneRepository.findById(personneId)
                .orElseThrow(() -> new RuntimeException("Identité inconnue"));

        List<String> documents = documentRepository
                .findByPersonneIdAndEtat(personneId, EtatDocument.VALIDE)
                .stream()
                .map(d -> d.getType().getLibelle())
                .toList();

        return ResponseEntity.ok(
                QrVerificationResponse.builder()
                        .statut("VALIDE")
                        .iu(personne.getIu())
                        .nom(personne.getNom())
                        .prenom(personne.getPrenom())
                        .documentsValides(documents)
                        .verificationTime(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/personne/{personneId}")
    public ResponseEntity<List<QrCodeDto>> findByPersonne(@PathVariable Long personneId) {
        return ResponseEntity.ok(service.findByPersonne(personneId));
    }

    @GetMapping("/personne/{personneId}/actifs")
    public ResponseEntity<List<QrCodeDto>> findActifs(@PathVariable Long personneId) {
        return ResponseEntity.ok(service.findActifsByPersonne(personneId));
    }

    @GetMapping("/scan/{code}")
    public ResponseEntity<QrCodeDto> scanQrCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }
}
