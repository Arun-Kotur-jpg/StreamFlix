package com.streamflix.controller.api;

import com.streamflix.dto.MovieDto;
import com.streamflix.entity.Movie;
import com.streamflix.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for Movie operations.
 * Provides CRUD and search endpoints.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    /**
     * GET /api/movies - Get all movies.
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    /**
     * GET /api/movies/{id} - Get movie by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    /**
     * POST /api/movies - Add a new movie (Admin only).
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = movieService.addMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    /**
     * PUT /api/movies/{id} - Update a movie (Admin only).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,
                                              @Valid @RequestBody MovieDto movieDto) {
        Movie movie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(movie);
    }

    /**
     * DELETE /api/movies/{id} - Delete a movie (Admin only).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/movies/search?q=keyword - Search movies.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }
}
