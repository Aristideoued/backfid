
package com.wuri.demowuri.services;

import com.wuri.demowuri.dto.UserDto;
import com.wuri.demowuri.model.User;

import java.util.List;

public interface UserService {

    User creerUser(User User);

    User modifierUser(UserDto UserDto, Long id);

    void deleteUser(Long id);

    User showUser(Long id);

    List<User> listeUser();
}
