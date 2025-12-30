package com.wuri.demowuri.model;

import java.time.LocalDate;

import com.wuri.demowuri.enums.EtatDocument;
import com.wuri.demowuri.enums.EtatPersonne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JSON de numéros
    @Column(columnDefinition = "json")
    private String numero;

    private LocalDate dateDelivrance;
    private LocalDate dateExpiration;

    private String photo; // URL ou chemin du fichier


    // contenu numérique (URL ou chemin fichier)
    @Lob
    private String data;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TypeDocument type;

    @ManyToOne
    @JoinColumn(name = "autorite_id", nullable = false)
    private AutoriteDelivrance autorite;

     @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatDocument etat;

     // 🔹 Relation avec Personne
    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;
}

