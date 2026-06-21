package com.streamflix.controller;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.service.MovieService;
import com.streamflix.service.UserService;
import com.streamflix.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Watchlist Controller - Manages user's movie watchlist.
 */
@Controller
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    /**
     * Show user's watchlist.
     */
    @GetMapping
    public String viewWatchlist(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Movie> watchlist = watchlistService.getWatchlist(user);
        model.addAttribute("watchlist", watchlist);
        model.addAttribute("currentUser", user);

        return "watchlist/list";
    }

    /**
     * Add movie to watchlist.
     */
    @PostMapping("/add/{movieId}")
    public String addToWatchlist(@PathVariable Long movieId,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieService.getMovieById(movieId);

        try {
            watchlistService.addToWatchlist(user, movie);
            redirectAttributes.addFlashAttribute("success", "Added to watchlist!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("info", "Already in your watchlist.");
        }

        return "redirect:/movies/" + movieId;
    }

    /**
     * Remove movie from watchlist.
     */
    @PostMapping("/remove/{movieId}")
    public String removeFromWatchlist(@PathVariable Long movieId,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieService.getMovieById(movieId);

        watchlistService.removeFromWatchlist(user, movie);
        redirectAttributes.addFlashAttribute("success", "Removed from watchlist.");

        return "redirect:/watchlist";
    }
}
