package ru.maslov.boot.spring_boot_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maslov.boot.spring_boot_app.model.User;
import ru.maslov.boot.spring_boot_app.service.UserService;


import java.security.Principal;

@Controller
@RequestMapping("/")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String UserHome(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "homepage";
    }


}
