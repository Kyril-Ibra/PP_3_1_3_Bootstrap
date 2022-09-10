package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = (User) userService.loadUserByUsername(email);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("listRoles", userService.getRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        return "/admin";
    }

    @PostMapping ("admin/createNewUser" )
    public String saveUser (User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}/updateUser")
    public String updateUserById(User user, String role) {
        user.setRoles(userService.findRolesByName(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping ("/admin/{id}/delete")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getSingleUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("user", userService.loadUserByUsername(email));
        return "/user";
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