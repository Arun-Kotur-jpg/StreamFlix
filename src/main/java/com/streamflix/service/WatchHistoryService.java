package com.streamflix.service;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.entity.WatchHistory;
import com.streamflix.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for Watch History tracking.
 * Records and retrieves user viewing activity.
 */
@Service
public class WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    /**
     * Record that a user watched a movie.
     * Updates lastWatched if already exists, otherwise creates new entry.
     */
    public WatchHistory recordWatch(User user, Movie movie) {
        Optional<WatchHistory> existing = watchHistoryRepository.findByUserAndMovie(user, movie);

        if (existing.isPresent()) {
            WatchHistory history = existing.get();
            history.setLastWatched(LocalDateTime.now());
            return watchHistoryRepository.save(history);
        }

        WatchHistory history = WatchHistory.builder()
                .user(user)
                .movie(movie)
                .build();

        return watchHistoryRepository.save(history);
    }

    /**
     * Get recently watched movies for a user (top 10).
     */
    public List<Movie> getRecentlyWatched(User user) {
        return watchHistoryRepository.findTop10ByUserOrderByLastWatchedDesc(user)
                .stream()
                .map(WatchHistory::getMovie)
                .collect(Collectors.toList());
    }

    /**
     * Get full watch history for a user.
     */
    public List<WatchHistory> getFullHistory(User user) {
        return watchHistoryRepository.findByUserOrderByLastWatchedDesc(user);
    }

    /**
     * Get total watched count for user.
     */
    public long getWatchedCount(User user) {
        return watchHistoryRepository.countByUser(user);
    }
}
