package com.wuri.demowuri.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.dto.DocumentVM;
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
    private final AutoriteRepository autoriteRepository;

    private final DocumentMapper mapper;

    private final TypeDocumentRepository typeDocumentRepository;
    private final DocumentMapper documentMapper;

    @Value("${app.photos.dir:photos}")
    private String photosBaseDir;

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
        existing.setTaille(documentDto.getTaille());
        existing.setContenu(documentDto.getContenu());
        existing.setLieuEtablissement(documentDto.getLieuEtablissement());
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
    @Transactional(readOnly = true)
    public DocumentVM getByTypeLibelleAndPersonneIu(String typeLibelle, String iu) {
        Document documents = documentRepository.findByTypeLibelleAndPersonneIu(typeLibelle, iu);
        return documentMapper.toDto2(documents);
    }

    @Override
    public List<DocumentDto> getByPersonne(Long personneId) {

        return documentRepository.findByPersonneId(personneId)
                .stream()
                .map(documentMapper::toDto)
                .toList();
    }

    @Override
    public String uploadPhoto(Long documentId, MultipartFile file) throws IOException {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        // 📁 photos/{iu}
        Path documentDir = Paths.get(photosBaseDir, document.getId().toString());

        // Créer le dossier s'il n'existe pas
        if (!Files.exists(documentDir)) {
            Files.createDirectories(documentDir);
        }

        // Nom fixe → remplacement automatique
        Path photoPath = documentDir.resolve("photo.png");

        // Remplacer si existe
        Files.copy(
                file.getInputStream(),
                photoPath,
                StandardCopyOption.REPLACE_EXISTING);

        // Enregistrer le chemin en base
        String relativePath = photosBaseDir + "/" + document.getId() + "/photo.png";
        document.setPhoto(relativePath);
        documentRepository.save(document);

        return relativePath;
    }

    @Override
    public Resource getPhoto(Long documentId) throws IOException {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        if (document.getPhoto() == null) {
            throw new RuntimeException("Aucune photo trouvée");
        }

        Path photoPath = Paths.get(document.getPhoto());

        Resource resource = new UrlResource(photoPath.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("Fichier photo introuvable");
        }

        return resource;
    }
}
