package com.wuri.demowuri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

import com.wuri.demowuri.enums.EtatDocument;

@Data
@AllArgsConstructor
public class DocumentSimpleDto {
    private String libelle;
    private LocalDate dateDelivrance;
    private LocalDate dateExpiration;
    private String nip;
     private String reference;
    private EtatDocument etat;
}

