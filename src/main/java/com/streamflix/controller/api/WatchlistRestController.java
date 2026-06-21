package com.streamflix.controller.api;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.service.MovieService;
import com.streamflix.service.UserService;
import com.streamflix.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Watchlist operations.
 * All endpoints require JWT authentication.
 */
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistRestController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    /**
     * GET /api/watchlist - Get user's watchlist.
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getWatchlist(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        return ResponseEntity.ok(watchlistService.getWatchlist(user));
    }

    /**
     * POST /api/watchlist/add?movieId=1 - Add movie to watchlist.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addToWatchlist(@RequestParam Long movieId,
                                             Authentication authentication) {
        try {
            User user = getAuthenticatedUser(authentication);
            Movie movie = movieService.getMovieById(movieId);
            watchlistService.addToWatchlist(user, movie);
            return ResponseEntity.ok(Map.of("message", "Added to watchlist"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/watchlist/remove?movieId=1 - Remove movie from watchlist.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromWatchlist(@RequestParam Long movieId,
                                                   Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Movie movie = movieService.getMovieById(movieId);
        watchlistService.removeFromWatchlist(user, movie);
        return ResponseEntity.ok(Map.of("message", "Removed from watchlist"));
    }

    private User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
