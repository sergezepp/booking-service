package com.epam.booking.service;

import com.epam.booking.exception.UserNameTakenException;
import com.epam.booking.exception.UserNotFoundException;
import com.epam.booking.model.User;
import com.epam.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User createUser(User user) {
        if (userRepository.findByUserIdentifier(user.getUserIdentifier()).isPresent()) {
            throw new UserNameTakenException();
        }
        return userRepository.save(user);
    }


    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getUsersByGivenName(String firstName, String lastName) {
        return CompletableFuture.supplyAsync(() -> userRepository.findByFirstName(firstName))
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> userRepository.findByLastName(lastName))
                        , List::of).join().stream().flatMap(List::stream)
                .toList();

        /**
         return Stream.of( CompletableFuture.supplyAsync( () -> userRepository.findByFirstName(firstName)),
         CompletableFuture.supplyAsync( () -> userRepository.findByLastName(lastName)))
         .parallel()
         .map(CompletableFuture::join)
         .flatMap(List::stream)
         .toList(); **/
    }


    public List<User> getUsersByGivenNameSync(String firstName, String lastName) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userRepository.findByFirstName(firstName));
        userList.addAll(userRepository.findByLastName(lastName));
        return userList;
    }

    public Optional<User> getUserByUserIdentifier(String userName) {
        return userRepository.findByUserIdentifier(userName);
    }

    public User updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteUser(long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException();
        }
    }

}
