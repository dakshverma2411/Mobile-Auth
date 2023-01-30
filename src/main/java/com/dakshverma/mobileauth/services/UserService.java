/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.services;

import com.dakshverma.mobileauth.exceptions.UserAlreadyExistException;
import com.dakshverma.mobileauth.models.User;
import com.dakshverma.mobileauth.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daksh
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public void checkIfUserExists(String phoneNumber) throws UserAlreadyExistException{
        User existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if(existingUser!=null){
            throw new UserAlreadyExistException();
        }
    }
    
    public void saveUser(String name, String password, String phoneNumber) throws UserAlreadyExistException{
        
        // code to check existing user
        checkIfUserExists(phoneNumber);
        
        User user = User.builder()
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
        
        userRepository.save(user);
    }
}
