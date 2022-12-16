package com.epam.booking.services;

import com.epam.booking.exception.UserNameTakenException;
import com.epam.booking.model.User;
import com.epam.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    public Optional<User> getUserById(long userId){
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public List<User> getUsersByGivenName(String firstName, String lastName){
       return Stream.of( CompletableFuture.supplyAsync( () -> userRepository.findByFirstName(firstName)),
                         CompletableFuture.supplyAsync( () -> userRepository.findByLastName(lastName)))
                .map(CompletableFuture::join)
               .flatMap(List::stream)
               .toList();
    }


    public List<User> getUsersByGivenNameSync(String firstName, String lastName){

        List<User>  userList =  new ArrayList<>();

        userList.addAll(userRepository.findByFirstName(firstName)  );
        userList.addAll( userRepository.findByLastName(lastName) );

        return userList;
    }




}
