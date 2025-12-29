package com.wuri.demowuri.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.enums.EtatDocument;
import com.wuri.demowuri.mapper.DocumentMapper;
import com.wuri.demowuri.model.AutoriteDelivrance;
import com.wuri.demowuri.model.Document;
import com.wuri.demowuri.model.TypeDocument;
import com.wuri.demowuri.repository.AutoriteRepository;
import com.wuri.demowuri.repository.DocumentRepository;
import com.wuri.demowuri.repository.TypeDocumentRepository;
import com.wuri.demowuri.services.DocumentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final TypeDocumentRepository typeRepository;
    private final AutoriteRepository autoriteRepository;

    private final DocumentMapper mapper;

    private final TypeDocumentRepository typeDocumentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentDto create(DocumentDto dto) {

        Document document = mapper.toEntity(dto);
        document.setEtat(EtatDocument.VALIDE);

        return mapper.toDto(documentRepository.save(document));
    }

    @Override
    public DocumentDto update(Long id, DocumentDto documentDto) {
        Document existing = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé avec id : " + id));

        existing.setNumero(documentDto.getNumero());
        existing.setDateDelivrance(documentDto.getDateDelivrance());
        existing.setDateExpiration(documentDto.getDateExpiration());
        existing.setData(documentDto.getData());

        if (documentDto.getTypeDocument() != null) {
            TypeDocument type = typeDocumentRepository.findById(documentDto.getTypeDocument().getId())
                    .orElseThrow(() -> new RuntimeException("TypeDocument non trouvé"));
            existing.setType(type);
        }

        if (documentDto.getAutorite() != null) {
            AutoriteDelivrance autorite = autoriteRepository.findById(documentDto.getAutorite().getId())
                    .orElseThrow(() -> new RuntimeException("Autorité non trouvée"));
            existing.setAutorite(autorite);
        }

        Document updated = documentRepository.save(existing);
        return documentMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new RuntimeException("Document non trouvé avec id : " + id);
        }
        documentRepository.deleteById(id);
    }

    @Override
    public DocumentDto getById(Long id) {
        return documentRepository.findById(id)
                .map(documentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Document non trouvé avec id : " + id));
    }

    @Override
    public List<DocumentDto> getByType(Long typeId) {
        return documentRepository.findByTypeId(typeId).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getByAutorite(Long autoriteId) {
        return documentRepository.findByAutoriteId(autoriteId).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDto> findAll() {
        return documentRepository.findAll().stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDto> getByTypeLibelleAndPersonneId(String typeLibelle, Long personneId) {
        List<Document> documents = documentRepository.findByTypeLibelleAndPersonneId(typeLibelle, personneId);
        return documents.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDto> getByPersonne(Long personneId) {

        return documentRepository.findByPersonneId(personneId)
                .stream()
                .map(documentMapper::toDto)
                .toList();
    }

}
