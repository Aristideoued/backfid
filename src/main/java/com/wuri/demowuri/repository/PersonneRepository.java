package com.wuri.demowuri.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.model.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Optional<PersonneDto> findByIu(String iu);

    boolean existsByIu(String iu);
}

