package com.streamflix.service;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.entity.Watchlist;
import com.streamflix.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Watchlist operations.
 * Handles adding/removing movies from user watchlists.
 */
@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    /**
     * Add a movie to user's watchlist.
     */
    public Watchlist addToWatchlist(User user, Movie movie) {
        // Check if already in watchlist
        if (watchlistRepository.existsByUserAndMovie(user, movie)) {
            throw new RuntimeException("Movie already in watchlist");
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .movie(movie)
                .build();

        return watchlistRepository.save(watchlist);
    }

    /**
     * Remove a movie from user's watchlist.
     */
    @Transactional
    public void removeFromWatchlist(User user, Movie movie) {
        watchlistRepository.deleteByUserAndMovie(user, movie);
    }

    /**
     * Get all movies in user's watchlist.
     */
    public List<Movie> getWatchlist(User user) {
        return watchlistRepository.findByUserOrderByAddedAtDesc(user)
                .stream()
                .map(Watchlist::getMovie)
                .collect(Collectors.toList());
    }

    /**
     * Check if a movie is in user's watchlist.
     */
    public boolean isInWatchlist(User user, Movie movie) {
        return watchlistRepository.existsByUserAndMovie(user, movie);
    }

    /**
     * Get watchlist count for user.
     */
    public long getWatchlistCount(User user) {
        return watchlistRepository.countByUser(user);
    }
}
