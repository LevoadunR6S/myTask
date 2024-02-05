package com.pet.taskmanager.services;

import com.pet.taskmanager.models.ROLE;
import com.pet.taskmanager.models.User;
import com.pet.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService{

    @Autowired
    private UserRepository userRepository;

    public String addNewUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());


        if (userFromDb != null) {
            return null;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(ROLE.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
