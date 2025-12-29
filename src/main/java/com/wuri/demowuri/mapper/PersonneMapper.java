package com.wuri.demowuri.mapper;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.model.Document;
import com.wuri.demowuri.model.Personne;
import java.util.Collections;

@Component
public class PersonneMapper {

private final DocumentMapper documentMapper;

public PersonneMapper(DocumentMapper documentMapper){
 this.documentMapper=documentMapper;
}

public PersonneDto toDto(Personne personne) {
        if (personne == null) return null;

        return PersonneDto.builder()
                .id(personne.getId())
                .nom(personne.getNom())
                .prenom(personne.getPrenom())
                .dateNaissance(personne.getDateNaissance())
                .sexe(personne.getSexe())
                .nationalite(personne.getNationalite())
                .telephone(personne.getTelephone())
                .email(personne.getEmail())
                .adresse(personne.getAdresse())
                .photo(personne.getPhoto())
                .iu(personne.getIu())
                .password(personne.getPassword())
                .etat(personne.getEtat())
                .documents(personne.getDocuments() != null ?
                        personne.getDocuments().stream()
                                .map(documentMapper::toDto)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public Personne toEntity(PersonneDto dto) {
        if (dto == null) return null;

        Personne personne = Personne.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .sexe(dto.getSexe())
                .nationalite(dto.getNationalite())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .photo(dto.getPhoto())
                .iu(dto.getIu())
                .password(dto.getPassword())
                .etat(dto.getEtat())
                .build();

        if (dto.getDocuments() != null) {
            List<Document> documents = dto.getDocuments().stream()
                    .map(documentMapper::toEntity)
                    .peek(d -> d.setPersonne(personne)) // lier chaque document à la personne
                    .collect(Collectors.toList());
            personne.setDocuments(documents);
        }

        return personne;
    }
}

