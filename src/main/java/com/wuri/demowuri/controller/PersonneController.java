package com.wuri.demowuri.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wuri.demowuri.dto.PersonneDto;
import com.wuri.demowuri.services.PersonneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService userService;

    @PostMapping
    public PersonneDto create(@RequestBody PersonneDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public PersonneDto getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/iu/{iu}")
    public PersonneDto getByIu(@PathVariable String iu) {
        return userService.getUserByIu(iu);
    }

    @GetMapping
    public List<PersonneDto> getAll() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public PersonneDto update(@PathVariable Long id, @RequestBody PersonneDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

