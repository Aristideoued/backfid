package com.wuri.demowuri.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wuri.demowuri.dto.AutoriteDto;
import com.wuri.demowuri.services.AutoriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/autorites")
@RequiredArgsConstructor
public class AutoriteController {

    private final AutoriteService service;

    @PostMapping
    public ResponseEntity<AutoriteDto> create(@RequestBody AutoriteDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoriteDto> update(@PathVariable Long id, @RequestBody AutoriteDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoriteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AutoriteDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

