package com.wuri.demowuri.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wuri.demowuri.model.QrCode;
import com.wuri.demowuri.enums.EtatQrCode;

import java.util.List;
import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

    List<QrCode> findByPersonneId(Long personneId);

    List<QrCode> findByPersonneIdAndEtat( Long personneId, EtatQrCode etat);

    Optional<QrCode> findByToken(String code);
}

