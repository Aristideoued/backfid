package com.wuri.demowuri.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wuri.demowuri.dto.LoginDto;
import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.services.PersonneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personnes")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService personneService;

    @PostMapping
    public PersonneDto create(@RequestBody PersonneDto personneDto) {
        return personneService.createPersonne(personneDto);
    }

    @GetMapping("/{id}")
    public PersonneDto getById(@PathVariable Long id) {
        return personneService.getPersonneById(id);
    }

    @GetMapping("/iu/{iu}")
    public PersonneDto getByIu(@PathVariable String iu) {
        return personneService.getPersonneByIu(iu);
    }

    @GetMapping
    public List<PersonneDto> getAll() {
        return personneService.getAllPersonnes();
    }

    @PutMapping("/{id}")
    public PersonneDto update(@PathVariable Long id, @RequestBody PersonneDto personneDto) {
        return personneService.updatePersonne(id, personneDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personneService.deletePersonne(id);
    }

     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {

        PersonneDto personne = personneService.authentifier(
                request.getIu(),
                request.getPassword()
        );

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
}

