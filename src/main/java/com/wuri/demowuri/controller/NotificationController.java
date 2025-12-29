package com.wuri.demowuri.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wuri.demowuri.dto.NotificationDto;
import com.wuri.demowuri.services.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationDto> create(@RequestBody NotificationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> update(@PathVariable Long id, @RequestBody NotificationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/personne/{personneId}")
    public ResponseEntity<List<NotificationDto>> findByPersonne(@PathVariable Long personneId) {
        return ResponseEntity.ok(service.findByPersonne(personneId));
    }

    @GetMapping("/personne/{personneId}/unread")
    public ResponseEntity<List<NotificationDto>> findUnreadByPersonne(@PathVariable Long personneId) {
        return ResponseEntity.ok(service.findUnreadByPersonne(personneId));
    }
}

