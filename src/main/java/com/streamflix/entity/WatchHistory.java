package com.streamflix.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * WatchHistory Entity - Tracks which movies a user has watched.
 * Stores the last watched timestamp for displaying recently watched section.
 */
@Entity
@Table(name = "watch_history", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "movie_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "last_watched")
    @Builder.Default
    private LocalDateTime lastWatched = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (lastWatched == null) {
            lastWatched = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastWatched = LocalDateTime.now();
    }
}
