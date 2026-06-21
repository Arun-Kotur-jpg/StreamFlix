package com.streamflix.controller;

import com.streamflix.dto.MovieDto;
import com.streamflix.entity.Movie;
import com.streamflix.service.MovieService;
import com.streamflix.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Admin Controller - Dashboard and movie management for administrators.
 * All endpoints require ADMIN role.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    /**
     * Admin dashboard showing statistics.
     */
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalMovies", movieService.getTotalMovies());
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/dashboard";
    }

    /**
     * Show add movie form.
     */
    @GetMapping("/movies/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        model.addAttribute("isEdit", false);
        return "admin/movie-form";
    }

    /**
     * Process add movie form.
     */
    @PostMapping("/movies/add")
    public String addMovie(@Valid @ModelAttribute MovieDto movieDto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/movie-form";
        }

        movieService.addMovie(movieDto);
        redirectAttributes.addFlashAttribute("success", "Movie added successfully!");
        return "redirect:/admin";
    }

    /**
     * Show edit movie form.
     */
    @GetMapping("/movies/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);

        MovieDto dto = MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .thumbnailUrl(movie.getThumbnailUrl())
                .coverUrl(movie.getCoverUrl())
                .videoUrl(movie.getVideoUrl())
                .build();

        model.addAttribute("movieDto", dto);
        model.addAttribute("isEdit", true);
        return "admin/movie-form";
    }

    /**
     * Process edit movie form.
     */
    @PostMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable Long id,
                             @Valid @ModelAttribute MovieDto movieDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "admin/movie-form";
        }

        movieService.updateMovie(id, movieDto);
        redirectAttributes.addFlashAttribute("success", "Movie updated successfully!");
        return "redirect:/admin";
    }

    /**
     * Delete a movie.
     */
    @PostMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        movieService.deleteMovie(id);
        redirectAttributes.addFlashAttribute("success", "Movie deleted successfully!");
        return "redirect:/admin";
    }
}
