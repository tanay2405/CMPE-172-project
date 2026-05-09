package com.tutoring.StudentTutoringService.controller;

import com.tutoring.StudentTutoringService.model.UserInfo;
import com.tutoring.StudentTutoringService.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        UserInfo user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("userID", user.getUserID());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());
            if ("TUTOR".equals(user.getRole())) {
                return "redirect:/tutorPage";
            }
            return "redirect:/slots";
        }
        model.addAttribute("error", "Invalid email or password.");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role,
                           Model model) {
        try {
            userService.register(name, email, password, role);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}