package ru.maslov.boot.spring_boot_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.maslov.boot.spring_boot_app.model.User;
import ru.maslov.boot.spring_boot_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class RESTAdminController {
    @Autowired
    private UserService userService;


    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.listOfUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);

    }
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userService.addUser(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user) {
        if(user.getPassword().length() !=60){
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        }
        userService.addUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable ("id") Integer id){
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable ("id" ) int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
