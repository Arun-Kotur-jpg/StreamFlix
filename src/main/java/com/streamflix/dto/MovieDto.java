package com.streamflix.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for Movie add/edit forms.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {

    private Long movieId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String genre;

    private String language;

    private Integer releaseYear;

    private String duration;

    private Double rating;

    private String thumbnailUrl;

    private String coverUrl;

    private String videoUrl;
}
