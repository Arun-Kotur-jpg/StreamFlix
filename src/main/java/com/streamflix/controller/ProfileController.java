package com.streamflix.controller;

import com.streamflix.entity.User;
import com.streamflix.service.UserService;
import com.streamflix.service.WatchHistoryService;
import com.streamflix.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Profile Controller - Displays user profile information.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private WatchHistoryService watchHistoryService;

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("watchlistCount", watchlistService.getWatchlistCount(user));
        model.addAttribute("watchedCount", watchHistoryService.getWatchedCount(user));

        return "profile/profile";
    }
}
