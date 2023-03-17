package com.epam.booking.repository;

import com.epam.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdentifier(String userName);

    Optional<User> findByEmail(String email);


    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);


}