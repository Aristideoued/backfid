package com.wuri.demowuri.mapper;


import org.springframework.stereotype.Component;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.model.Personne;

@Component
public class PersonneMapper {

    public PersonneDto toDto(Personne user) {
        if (user == null) return null;

        return PersonneDto.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .dateNaissance(user.getDateNaissance())
                .sexe(user.getSexe())
                .nationalite(user.getNationalite())
                .photo(user.getPhoto())
                .iu(user.getIu())
                .build();
    }

    public Personne toEntity(PersonneDto dto) {
        if (dto == null) return null;

        return Personne.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .sexe(dto.getSexe())
                .nationalite(dto.getNationalite())
                .photo(dto.getPhoto())
                .iu(dto.getIu())
                .build();
    }
}

