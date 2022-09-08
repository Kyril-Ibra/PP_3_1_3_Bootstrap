package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UserController {

    private final UserService userService;

    //constructor injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("listRoles", userService.getRoles());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("user", userService.loadUserByUsername(email));
        return "/admin";
    }

    @GetMapping("/user")
    public String getSingleUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("user", userService.loadUserByUsername(email));
        return "/user";
    }


    @PostMapping ("admin/createNewUser" )
    public String saveUser (@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}/updateUser")
    public String updateUserById(@PathVariable int id, @ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping ("/admin/{id}/delete")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }
}