package com.streamflix.repository;

import com.streamflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Movie entity database operations.
 * Provides search, filtering, and trending/popular queries.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Search by title (case-insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);

    // Filter by genre
    List<Movie> findByGenreIgnoreCase(String genre);

    // Filter by language
    List<Movie> findByLanguageIgnoreCase(String language);

    // Latest releases (ordered by creation date)
    List<Movie> findTop10ByOrderByCreatedAtDesc();

    // Popular movies (ordered by rating)
    List<Movie> findTop10ByOrderByRatingDesc();

    // Trending (ordered by release year descending, then rating)
    List<Movie> findTop10ByOrderByReleaseYearDescRatingDesc();

    // Combined search query
    @Query("SELECT m FROM Movie m WHERE " +
           "LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.language) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchMovies(@Param("keyword") String keyword);

    // Filter by genre and language
    List<Movie> findByGenreIgnoreCaseAndLanguageIgnoreCase(String genre, String language);

    // Get distinct genres
    @Query("SELECT DISTINCT m.genre FROM Movie m WHERE m.genre IS NOT NULL ORDER BY m.genre")
    List<String> findAllGenres();

    // Get distinct languages
    @Query("SELECT DISTINCT m.language FROM Movie m WHERE m.language IS NOT NULL ORDER BY m.language")
    List<String> findAllLanguages();
}
