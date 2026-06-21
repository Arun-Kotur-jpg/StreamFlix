package com.streamflix.controller;

import com.streamflix.dto.RegisterRequest;
import com.streamflix.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Authentication Controller - Handles login and registration pages.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Show login page.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "auth/login";
    }

    /**
     * Show registration page.
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    /**
     * Process registration form.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "auth/register";
        }

        // Check if passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match.");
            return "auth/register";
        }

        // Check if email already exists
        if (userService.emailExists(registerRequest.getEmail())) {
            model.addAttribute("emailError", "Email is already registered.");
            return "auth/register";
        }

        try {
            userService.registerUser(registerRequest);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/register";
        }
    }
}
