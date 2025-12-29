package com.wuri.demowuri.mapper;

import org.springframework.stereotype.Component;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.model.Document;
import com.wuri.demowuri.model.Personne;

@Component
public class DocumentMapper {
    private final TypeDocumentMapper typeDocumentMapper;
    private final AutoriteMapper autoriteMapper;


    public DocumentMapper(TypeDocumentMapper typeDocumentMapper, AutoriteMapper autoriteMapper) {
        this.typeDocumentMapper = typeDocumentMapper;
        this.autoriteMapper = autoriteMapper;
    }

    public DocumentDto toDto(Document document) {
        if (document == null)
            return null;

        PersonneDto personneDto = null;
        if (document.getPersonne() != null) {
            personneDto = PersonneDto.builder()
                    .id(document.getPersonne().getId())
                    .iu(document.getPersonne().getIu())
                    .build();
        }

        return DocumentDto.builder()
                .id(document.getId())
                .numero(document.getNumero())
                .dateDelivrance(document.getDateDelivrance())
                .dateExpiration(document.getDateExpiration())
                .data(document.getData())
                .typeDocument(typeDocumentMapper.toDto(document.getType()))
                .autorite(autoriteMapper.toDto(document.getAutorite()))
                .personneId(
                        document.getPersonne() != null ? document.getPersonne().getId() : null)
                .etat(document.getEtat()) // Enum EtatDocument
                .build();
    }

    public Document toEntity(DocumentDto dto) {
        if (dto == null)
            return null;

        Document document = Document.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .dateDelivrance(dto.getDateDelivrance())
                .dateExpiration(dto.getDateExpiration())
                .data(dto.getData())
                .type(dto.getTypeDocument() != null ? typeDocumentMapper.toEntity(dto.getTypeDocument()) : null)
                .autorite(dto.getAutorite() != null ? autoriteMapper.toEntity(dto.getAutorite()) : null)
                .etat(dto.getEtat()) // Enum EtatDocument
                .build();

        if (dto.getPersonneId() != null) {
            document.setPersonne(Personne.builder().id(dto.getPersonneId()).build());
        }

        return document;
    }
}
