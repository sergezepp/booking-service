package com.epam.booking.controller.json;

import com.epam.booking.exception.RestExceptionHandler;
import com.epam.booking.model.dto.UserDto;
import com.epam.booking.service.BookingFacade;
import com.epam.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class UserRestController extends RestExceptionHandler {

    @Autowired
    BookingFacade bookingService;

    @GetMapping("/getUserByUserIdentifier/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {

        UserDto response = bookingService.getUserByUserName(id);

        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        UserDto response = bookingService.createUser(userDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {

        UserDto response = bookingService.updateUser(userDto);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        bookingService.deleteUser(id);

        return ResponseEntity.ok().build();
    }


}
