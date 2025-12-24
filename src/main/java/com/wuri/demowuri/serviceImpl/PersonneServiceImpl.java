package com.wuri.demowuri.serviceImpl;




import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.mapper.PersonneMapper;
import com.wuri.demowuri.model.Personne;
import com.wuri.demowuri.repository.PersonneRepository;
import com.wuri.demowuri.services.PersonneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements PersonneService {

    private final PersonneRepository userRepository;
    private final PersonneMapper userMapper;


    @Override
    public PersonneDto createUser(PersonneDto userDto) {

        // IU toujours généré côté backend
        String iu = generateUniqueIU();
        userDto.setIu(iu);

        Personne user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }
  
    private String generateUniqueIU() {
        String iu;
        do {
            iu = generate12DigitNumber();
        } while (userRepository.existsByIu(iu));

        return iu;
    }
  
  
  private String generate12DigitNumber() {
        long number = (long) (Math.random() * 1_000_000_000_000L);
        return String.format("%012d", number);
    }

    @Override
    public PersonneDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @Override
    public PersonneDto getUserByIu(String iu) {
        return userRepository.findByIu(iu)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @Override
    public List<PersonneDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonneDto updateUser(Long id, PersonneDto userDto) {
        Personne user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setNom(userDto.getNom());
        user.setPrenom(userDto.getPrenom());
        user.setDateNaissance(userDto.getDateNaissance());
        user.setSexe(userDto.getSexe());
        user.setNationalite(userDto.getNationalite());
        user.setPhoto(userDto.getPhoto());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // IU de 12 caractères
    private String generateIU() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}

