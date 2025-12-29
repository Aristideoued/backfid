package com.wuri.demowuri.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrVerificationResponse {

    private String statut; // VALIDE / EXPIRE / INVALIDE

    private String iu;
    private String nom;
    private String prenom;

    private List<String> documentsValides;

    private LocalDateTime verificationTime;
}
