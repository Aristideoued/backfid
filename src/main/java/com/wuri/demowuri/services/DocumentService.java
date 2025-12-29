package com.wuri.demowuri.services;

import java.util.List;

import com.wuri.demowuri.dto.DocumentDto;

public interface DocumentService {
     DocumentDto create(DocumentDto dto);
    List<DocumentDto> findAll();
    DocumentDto update(Long id, DocumentDto documentDto);

    void delete(Long id);

    DocumentDto getById(Long id);

    List<DocumentDto> getByType(Long typeId);

    List<DocumentDto> getByAutorite(Long autoriteId);

    List<DocumentDto> getByTypeLibelleAndPersonneId(String typeLibelle, Long personneId);

    List<DocumentDto> getByPersonne(Long personneId);
}
