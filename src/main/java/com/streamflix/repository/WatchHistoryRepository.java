package com.streamflix.repository;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for WatchHistory entity database operations.
 */
@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    List<WatchHistory> findByUserOrderByLastWatchedDesc(User user);

    List<WatchHistory> findTop10ByUserOrderByLastWatchedDesc(User user);

    Optional<WatchHistory> findByUserAndMovie(User user, Movie movie);

    long countByUser(User user);
}
