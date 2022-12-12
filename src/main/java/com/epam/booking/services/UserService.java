package com.epam.booking.services;

import com.epam.booking.exception.UserNameTakenException;
import com.epam.booking.model.User;
import com.epam.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User createUser(User user){
        if (userRepository.findByUserName(user.getUserName()).isPresent()){
            throw new UserNameTakenException();
        }
        return userRepository.save(user);
    }


}
