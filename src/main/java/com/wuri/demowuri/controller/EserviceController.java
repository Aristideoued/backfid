package com.wuri.demowuri.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wuri.demowuri.dto.EserviceDto;
import com.wuri.demowuri.services.EserviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eservices")
@RequiredArgsConstructor
public class EserviceController {

    private final EserviceService service;

    @PostMapping
    public ResponseEntity<EserviceDto> create(@RequestBody EserviceDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EserviceDto> update(@PathVariable Long id, @RequestBody EserviceDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EserviceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EserviceDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

