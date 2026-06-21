package com.streamflix.controller;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.service.MovieService;
import com.streamflix.service.UserService;
import com.streamflix.service.WatchHistoryService;
import com.streamflix.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Movie Controller - Handles movie browsing, details, search, and streaming pages.
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private WatchHistoryService watchHistoryService;

    /**
     * Movie details page.
     */
    @GetMapping("/movies/{id}")
    public String movieDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        // Check if in user's watchlist
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            userService.findByEmail(email).ifPresent(user -> {
                boolean inWatchlist = watchlistService.isInWatchlist(user, movie);
                model.addAttribute("inWatchlist", inWatchlist);
            });
        }

        return "movie/details";
    }

    /**
     * Video streaming/watch page.
     */
    @GetMapping("/movies/watch/{id}")
    public String watchMovie(@PathVariable Long id, Model model, Authentication authentication) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        // Record watch history if user is logged in
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            userService.findByEmail(email).ifPresent(user ->
                watchHistoryService.recordWatch(user, movie)
            );
        }

        return "movie/watch";
    }

    /**
     * Search movies page.
     */
    @GetMapping("/search")
    public String searchMovies(@RequestParam(value = "q", required = false) String query,
                                @RequestParam(value = "genre", required = false) String genre,
                                @RequestParam(value = "language", required = false) String language,
                                Model model) {
        List<Movie> results;

        if (query != null && !query.trim().isEmpty()) {
            results = movieService.searchMovies(query);
            model.addAttribute("searchQuery", query);
        } else if (genre != null && !genre.trim().isEmpty()) {
            results = movieService.getMoviesByGenre(genre);
            model.addAttribute("selectedGenre", genre);
        } else if (language != null && !language.trim().isEmpty()) {
            results = movieService.getMoviesByLanguage(language);
            model.addAttribute("selectedLanguage", language);
        } else {
            results = movieService.getAllMovies();
        }

        model.addAttribute("movies", results);
        model.addAttribute("genres", movieService.getAllGenres());
        model.addAttribute("languages", movieService.getAllLanguages());

        return "movie/search";
    }
}
