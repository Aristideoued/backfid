package com.wuri.demowuri.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "personnes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private LocalDate dateNaissance;

    @Column(length = 10)
    private String sexe; // M / F

    private String nationalite;

    private String photo; // URL ou chemin du fichier

    @Column(nullable = false, unique = true, length = 12)
    private String iu; // Identifiant Unique
}
