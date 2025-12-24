package com.wuri.demowuri.services;



import java.util.List;

import com.wuri.demowuri.dto.PersonneDto;

public interface PersonneService {

    PersonneDto createUser(PersonneDto userDto);

    PersonneDto getUserById(Long id);

    PersonneDto getUserByIu(String iu);

    List<PersonneDto> getAllUsers();

    PersonneDto updateUser(Long id, PersonneDto userDto);

    void deleteUser(Long id);
}

