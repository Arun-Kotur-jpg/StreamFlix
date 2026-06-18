package com.streamflix.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Movie Entity - Represents a movie/show available on StreamFlix.
 * Stores metadata, thumbnail URL, and video stream URL.
 */
@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String genre;

    @Column(length = 50)
    private String language;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(length = 20)
    private String duration;

    private Double rating;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "cover_url", length = 1000)
    private String coverUrl;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
