package ru.maslov.boot.spring_boot_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maslov.boot.spring_boot_app.model.Role;
import ru.maslov.boot.spring_boot_app.model.User;
import ru.maslov.boot.spring_boot_app.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    private UserService userService;



    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("userList")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.listOfUser());
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("userInfo", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "all-users";
    }

    @PostMapping(value = "saveUser")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam(value = "role", required = false) String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleById(Long.parseLong(roles)));
        }
        user.setRoles(roleSet);
        if (user.getId() == 0) {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userService.addUser(user);
        }
        userService.addUser(user);
        return "redirect:/admin/userList";
    }

    @PostMapping(value = "updateUser")
    public String updateUser(@ModelAttribute User userEdit, @RequestParam(value = "role", required = false) String[] role,
                             @RequestParam(value = "id", required = false) int id,
                             @RequestParam(value = "lastname", required = false) String lastname,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "age", required = false) Integer age,
                             @RequestParam(value = "firstname", required = false) String firstname) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleById(Long.parseLong(roles)));
        }
        userEdit.setRoles(roleSet);
        userEdit.setId(id);
        userEdit.setName(firstname);
        userEdit.setSurName(lastname);
        userEdit.setPassword(password);
        userEdit.setAge(age);
        userEdit.setUsername(email);
        if (userEdit.getId() == 0) {
            userEdit.setPassword(passwordEncoder().encode(userEdit.getPassword()));
            userService.addUser(userEdit);
        }
        userService.addUser(userEdit);
        return "redirect:/admin/userList";
    }

    @PostMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin/userList";
    }

}
