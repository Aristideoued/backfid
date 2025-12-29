package com.wuri.demowuri.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wuri.demowuri.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByTypeId(Long typeId);

    // Récupérer tous les documents par AutoriteDelivrance
    List<Document> findByAutoriteId(Long autoriteId);

    // Optionnel : recherche par numéro exact
    List<Document> findByNumero(String numero);

    // Optionnel : recherche par date de délivrance
    List<Document> findByDateDelivrance(LocalDate dateDelivrance);

    // Optionnel : recherche par date d'expiration
    List<Document> findByDateExpiration(LocalDate dateExpiration);

    // Optionnel : recherche par type et autorité
    List<Document> findByTypeIdAndAutoriteId(Long typeId, Long autoriteId);

    List<Document> findByTypeLibelleAndPersonneId(String libelle, Long personneId);
}
