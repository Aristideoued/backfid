package com.wuri.demowuri.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wuri.demowuri.dto.LoginDto;
import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.dto.PersonneVM;
import com.wuri.demowuri.services.PersonneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/personnes")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService personneService;

    @PostMapping("creer")
    public PersonneDto create(@RequestBody PersonneDto personneDto) {
        return personneService.createPersonne(personneDto);
    }

    @GetMapping("/getById/{id}")
    public PersonneDto getById(@PathVariable Long id) {
        return personneService.getPersonneById(id);
    }

    @GetMapping("/iu/{iu}")
    public PersonneDto getByIu(@PathVariable String iu) {
        return personneService.getPersonneByIu(iu);
    }

    @GetMapping("all")
    public List<PersonneDto> getAll() {
        return personneService.getAllPersonnes();
    }

    @PutMapping("/update/{id}")
    public PersonneDto update(@PathVariable Long id, @RequestBody PersonneDto personneDto) {
        System.out.println("DTO=======> "+personneDto.toString());
        return personneService.updatePersonne(id, personneDto);
    }

     @PutMapping("/update/iu/{iu}")
    public PersonneDto updateByIu(@PathVariable String iu, @RequestBody PersonneVM personneDto) {


        return personneService.updatePersonneByIu(iu, personneDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        personneService.deletePersonne(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {

        PersonneDto personne = personneService.authentifier(
                request.getIu(),
                request.getPassword());

        return ResponseEntity.ok(personne);
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<PersonneDto> activer(@PathVariable Long id) {
        return ResponseEntity.ok(personneService.activerPersonne(id));
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<PersonneDto> desactiver(@PathVariable Long id) {
        return ResponseEntity.ok(personneService.desactiverPersonne(id));
    }

    @PostMapping(value = "/{iu}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadPhoto(
            @PathVariable String iu,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Fichier photo vide");
        }

        String path = personneService.uploadPhoto(iu, file);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Photo uploadée avec succès",
                        "path", path));
    }

    @GetMapping("/photo/{iu}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String iu) throws IOException {

        Resource photo = personneService.getPhoto(iu);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // photo.png
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo.png\"")
                .body(photo);
    }
}
