package com.streamflix.service;

import com.streamflix.dto.MovieDto;
import com.streamflix.entity.Movie;
import com.streamflix.exception.ResourceNotFoundException;
import com.streamflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Movie-related business logic.
 * Handles CRUD operations, search, and content categorization.
 */
@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Get all movies.
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Get movie by ID.
     */
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    /**
     * Add a new movie.
     */
    public Movie addMovie(MovieDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .language(dto.getLanguage())
                .releaseYear(dto.getReleaseYear())
                .duration(dto.getDuration())
                .rating(dto.getRating())
                .thumbnailUrl(dto.getThumbnailUrl())
                .coverUrl(dto.getCoverUrl())
                .videoUrl(dto.getVideoUrl())
                .build();
        return movieRepository.save(movie);
    }

    /**
     * Update an existing movie.
     */
    public Movie updateMovie(Long id, MovieDto dto) {
        Movie movie = getMovieById(id);
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setGenre(dto.getGenre());
        movie.setLanguage(dto.getLanguage());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        movie.setRating(dto.getRating());
        movie.setThumbnailUrl(dto.getThumbnailUrl());
        movie.setCoverUrl(dto.getCoverUrl());
        movie.setVideoUrl(dto.getVideoUrl());
        return movieRepository.save(movie);
    }

    /**
     * Delete a movie.
     */
    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    /**
     * Search movies by keyword (title, genre, or language).
     */
    public List<Movie> searchMovies(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return movieRepository.findAll();
        }
        return movieRepository.searchMovies(keyword.trim());
    }

    /**
     * Filter movies by genre.
     */
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre);
    }

    /**
     * Filter movies by language.
     */
    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguageIgnoreCase(language);
    }

    /**
     * Get trending movies.
     */
    public List<Movie> getTrendingMovies() {
        return movieRepository.findTop10ByOrderByReleaseYearDescRatingDesc();
    }

    /**
     * Get latest releases.
     */
    public List<Movie> getLatestMovies() {
        return movieRepository.findTop10ByOrderByCreatedAtDesc();
    }

    /**
     * Get popular movies (by rating).
     */
    public List<Movie> getPopularMovies() {
        return movieRepository.findTop10ByOrderByRatingDesc();
    }

    /**
     * Get total movie count.
     */
    public long getTotalMovies() {
        return movieRepository.count();
    }

    /**
     * Get all distinct genres.
     */
    public List<String> getAllGenres() {
        return movieRepository.findAllGenres();
    }

    /**
     * Get all distinct languages.
     */
    public List<String> getAllLanguages() {
        return movieRepository.findAllLanguages();
    }
}
