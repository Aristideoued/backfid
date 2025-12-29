package com.wuri.demowuri.mapper;

import org.springframework.stereotype.Component;

import com.wuri.demowuri.dto.DocumentDto;
import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.model.Document;

@Component
public class DocumentMapper {
    private final TypeDocumentMapper typeDocumentMapper;
    private final AutoriteMapper autoriteMapper;

    private final PersonneMapper personneMapper;


    public DocumentMapper(TypeDocumentMapper typeDocumentMapper, AutoriteMapper autoriteMapper,PersonneMapper personneMapper) {
        this.typeDocumentMapper = typeDocumentMapper;
        this.autoriteMapper = autoriteMapper;
        this.personneMapper=personneMapper;
    }
  

   public DocumentDto toDto(Document document) {
        if (document == null) return null;

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
                .personne(personneDto)
                .etat(document.getEtat()) // Enum EtatDocument
                .build();
    }

       public Document toEntity(DocumentDto dto) {
        if (dto == null) return null;

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

        if (dto.getPersonne() != null && dto.getPersonne().getId() != null) {
            document.setPersonne(
                    com.wuri.demowuri.model.Personne.builder()
                            .id(dto.getPersonne().getId())
                            .build()
            );
        }

        return document;
    }
}
