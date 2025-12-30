package com.wuri.demowuri.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

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

    Resource getPhoto(Long documendId) throws IOException;

    String uploadPhoto(Long documendId, MultipartFile file) throws IOException;

}
