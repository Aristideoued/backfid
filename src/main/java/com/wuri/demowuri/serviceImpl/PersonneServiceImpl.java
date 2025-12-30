package com.wuri.demowuri.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.enums.EtatPersonne;
import com.wuri.demowuri.mapper.PersonneMapper;
import com.wuri.demowuri.model.Personne;
import com.wuri.demowuri.repository.PersonneRepository;
import com.wuri.demowuri.services.PersonneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements PersonneService {

    private final PersonneRepository personneRepository;
    private final PersonneMapper personneMapper;

    @Value("${app.photos.dir:photos}")
    private String photosBaseDir;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public PersonneDto createPersonne(PersonneDto userDto) {

        // IU toujours généré côté backend
        String iu = generateUniqueIU();
        userDto.setIu(iu);
        

       // userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Personne user = personneMapper.toEntity(userDto);
        user.setEtat(EtatPersonne.ACTIF);
        return personneMapper.toDto(personneRepository.save(user));
    }

    private String generateUniqueIU() {
        String iu;
        do {
            iu = generate12DigitNumber();
        } while (personneRepository.existsByIu(iu));

        return iu;
    }

    @Override
    public PersonneDto authentifier(String iu, String password) {

        PersonneDto personne = personneRepository.findByIu(iu)
                .orElseThrow(() -> new RuntimeException("IU incorrect"));

        if (!passwordEncoder.matches(password, personne.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return personne;
    }

    private String generate12DigitNumber() {
        long number = (long) (Math.random() * 1_000_000_000_000L);
        return String.format("%012d", number);
    }

    @Override
    public PersonneDto getPersonneById(Long id) {
        return personneRepository.findById(id)
                .map(personneMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @Override
    public PersonneDto getPersonneByIu(String iu) {
        return personneRepository.findByIu(iu)

                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @Override
    public List<PersonneDto> getAllPersonnes() {
        return personneRepository.findAll()
                .stream()
                .map(personneMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonneDto updatePersonne(Long id, PersonneDto userDto) {
        Personne user = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setNom(userDto.getNom());
        user.setPrenom(userDto.getPrenom());
        user.setDateNaissance(userDto.getDateNaissance());
        user.setSexe(userDto.getSexe());
        user.setNationalite(userDto.getNationalite());
        user.setPhoto(userDto.getPhoto());

         if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }


        return personneMapper.toDto(personneRepository.save(user));
    }

    @Override
    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }

    @Override
    public String uploadPhoto(String iu, MultipartFile file) throws IOException {

        PersonneDto personne = personneRepository.findByIu(iu)
                .orElseThrow(() -> new RuntimeException("Personne introuvable"));

        // 📁 photos/{iu}
        Path personneDir = Paths.get(photosBaseDir, iu);

        // Créer le dossier s'il n'existe pas
        if (!Files.exists(personneDir)) {
            Files.createDirectories(personneDir);
        }

        // Nom fixe → remplacement automatique
        Path photoPath = personneDir.resolve("photo.png");

        // Remplacer si existe
        Files.copy(
                file.getInputStream(),
                photoPath,
                StandardCopyOption.REPLACE_EXISTING);

        // Enregistrer le chemin en base
        String relativePath = photosBaseDir + "/" + iu + "/photo.png";
        personne.setPhoto(relativePath);
        personneRepository.save(personneMapper.toEntity(personne));

        return relativePath;
    }


    @Override
    public Resource getPhoto(String iu) throws IOException {

        PersonneDto personne = personneRepository.findByIu(iu)
                .orElseThrow(() -> new RuntimeException("Personne introuvable"));

        if (personne.getPhoto() == null) {
            throw new RuntimeException("Aucune photo trouvée");
        }

        Path photoPath = Paths.get(personne.getPhoto());

        Resource resource = new UrlResource(photoPath.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("Fichier photo introuvable");
        }

        return resource;
    }

    @Override
    public PersonneDto activerPersonne(Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne introuvable"));

        personne.setEtat(EtatPersonne.ACTIF);

        return personneMapper.toDto(personneRepository.save(personne));
    }

    @Override
    public PersonneDto desactiverPersonne(Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne introuvable"));

        personne.setEtat(EtatPersonne.INACTIF);

        return personneMapper.toDto(personneRepository.save(personne));
    }
}
