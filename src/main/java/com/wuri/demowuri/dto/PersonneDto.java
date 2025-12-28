package com.wuri.demowuri.dto;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonneDto {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String nationalite;
    private String telephone;
    private String email;
    private String adresse;
    private String photo;
    private String iu;
    private String password;
}

