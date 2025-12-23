package com.wuri.demowuri.serviceImpl;

import com.wuri.demowuri.dto.UserDto;
import com.wuri.demowuri.model.User;
import com.wuri.demowuri.repository.UserRepository;
import com.wuri.demowuri.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User creerUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User modifierUser(UserDto userDto, Long id){
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isPresent()){
            User userEx = userOp.get();
            userEx.setUsername(userDto.getUsername());
                      
            return userRepository.save(userEx);
        }else{
            return null;
        }
    }

    @Override
    public void deleteUser(Long id){
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isPresent()){
            User userEx = userOp.get();
            userRepository.delete(userEx);
        }
    }

    @Override
    public User showUser(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> listeUser(){
        return userRepository.findAll();
    }


}
