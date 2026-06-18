package com.streamflix.repository;

import com.streamflix.entity.Movie;
import com.streamflix.entity.User;
import com.streamflix.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Watchlist entity database operations.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    List<Watchlist> findByUser(User user);

    List<Watchlist> findByUserOrderByAddedAtDesc(User user);

    Optional<Watchlist> findByUserAndMovie(User user, Movie movie);

    boolean existsByUserAndMovie(User user, Movie movie);

    void deleteByUserAndMovie(User user, Movie movie);

    long countByUser(User user);
}
