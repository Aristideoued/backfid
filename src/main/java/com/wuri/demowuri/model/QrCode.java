package com.wuri.demowuri.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.wuri.demowuri.enums.EtatQrCode;

@Entity
@Table(name = "qr_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔐 Contenu du QR (hash ou lien sécurisé)
    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private LocalDateTime dateExpiration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatQrCode etat;

    // 🔗 QR Code adressé à une personne
    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;
}

