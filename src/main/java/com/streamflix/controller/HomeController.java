package com.streamflix.controller;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.service.MovieService;
import com.streamflix.service.UserService;
import com.streamflix.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

/**
 * Home Controller - Renders the main landing page.
 * Displays trending, latest, popular, and recently watched movies.
 */
@Controller
public class HomeController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchHistoryService watchHistoryService;

    @GetMapping({"/", "/home"})
    public String homePage(Model model, Authentication authentication) {
        // Get categorized movies
        List<Movie> trending = movieService.getTrendingMovies();
        List<Movie> latest = movieService.getLatestMovies();
        List<Movie> popular = movieService.getPopularMovies();

        model.addAttribute("trendingMovies", trending);
        model.addAttribute("latestMovies", latest);
        model.addAttribute("popularMovies", popular);

        // Get recently watched if user is logged in
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            userService.findByEmail(email).ifPresent(user -> {
                List<Movie> recentlyWatched = watchHistoryService.getRecentlyWatched(user);
                model.addAttribute("recentlyWatched", recentlyWatched);
                model.addAttribute("currentUser", user);
            });
        } else {
            model.addAttribute("recentlyWatched", Collections.emptyList());
        }

        // Featured movie (first trending)
        if (!trending.isEmpty()) {
            model.addAttribute("featuredMovie", trending.get(0));
        }

        return "index";
    }
}
