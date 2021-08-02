package ru.maslov.boot.spring_boot_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maslov.boot.spring_boot_app.model.User;
import ru.maslov.boot.spring_boot_app.service.UserService;
import java.security.Principal;


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

}
