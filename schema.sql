-- ========================================
-- StreamFlix Database Schema & Sample Data
-- MySQL 8.x
-- ========================================

-- Create Database
CREATE DATABASE IF NOT EXISTS streamflix_db;
USE streamflix_db;

-- ========================================
-- Note: Tables are auto-created by Hibernate (ddl-auto=update)
-- This script is for manual setup or reference.
-- ========================================

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Movies Table
CREATE TABLE IF NOT EXISTS movies (
    movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(50),
    language VARCHAR(50),
    release_year INT,
    duration VARCHAR(20),
    rating DOUBLE,
    thumbnail_url VARCHAR(500),
    video_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Watchlists Table
CREATE TABLE IF NOT EXISTS watchlists (
    watchlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_movie (user_id, movie_id)
);

-- Watch History Table
CREATE TABLE IF NOT EXISTS watch_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    last_watched TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_movie_history (user_id, movie_id)
);

-- ========================================
-- Sample Data
-- ========================================

-- Admin User (password: admin123)
-- BCrypt hash of 'admin123'
INSERT IGNORE INTO users (name, email, password, role, created_at) VALUES
('Admin User', 'admin@streamflix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NOW());

-- Regular User (password: password123)
-- BCrypt hash of 'password123'
INSERT IGNORE INTO users (name, email, password, role, created_at) VALUES
('Demo User', 'user@streamflix.com', '$2a$10$EqKcp1WFKAr1dqJSuCqwOeDAXyHIJBBsagnbMV3ipWp9sTNpYSPwi', 'USER', NOW());

-- Sample Movies (20 movies across genres)
INSERT IGNORE INTO movies (title, description, genre, language, release_year, duration, rating, thumbnail_url, video_url, created_at) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 'Sci-Fi', 'English', 2010, '2h 28m', 8.8, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Inception', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanitys survival.', 'Sci-Fi', 'English', 2014, '2h 49m', 8.7, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Interstellar', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'Action', 'English', 2008, '2h 32m', 9.0, 'https://placehold.co/300x450/1a1d29/00b8d9?text=The+Dark+Knight', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Avengers: Endgame', 'After the devastating events of Infinity War, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.', 'Action', 'English', 2019, '3h 1m', 8.4, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Avengers:+Endgame', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Parasite', 'Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.', 'Thriller', 'Korean', 2019, '2h 12m', 8.5, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Parasite', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('3 Idiots', 'Two friends are searching for their long lost companion. They revisit their college days and recall the memories of their friend who inspired them to think differently.', 'Comedy', 'Hindi', 2009, '2h 50m', 8.4, 'https://placehold.co/300x450/1a1d29/00b8d9?text=3+Idiots', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('RRR', 'A fictional story about two legendary revolutionaries and their journey away from home before they started fighting for their country in the 1920s.', 'Action', 'Telugu', 2022, '3h 7m', 7.8, 'https://placehold.co/300x450/1a1d29/00b8d9?text=RRR', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('KGF: Chapter 2', 'In the blood-soaked Kolar Gold Fields, Rocky strives for unchallenged supremacy. Facing powerful adversaries, he must cement his legacy.', 'Action', 'Kannada', 2022, '2h 48m', 7.5, 'https://placehold.co/300x450/1a1d29/00b8d9?text=KGF:+Chapter+2', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Dangal', 'Former wrestler Mahavir Singh Phogat and his two wrestler daughters struggle towards glory at the Commonwealth Games.', 'Drama', 'Hindi', 2016, '2h 41m', 8.3, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Dangal', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Spirited Away', 'During her familys move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits.', 'Animation', 'Japanese', 2001, '2h 5m', 8.6, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Spirited+Away', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'Drama', 'English', 1994, '2h 22m', 9.3, 'https://placehold.co/300x450/1a1d29/00b8d9?text=The+Shawshank+Redemption', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Dune: Part Two', 'Paul Atreides unites with Chani and the Fremen while on a warpath of revenge against the conspirators who destroyed his family.', 'Sci-Fi', 'English', 2024, '2h 46m', 8.5, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Dune:+Part+Two', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Oppenheimer', 'The story of J. Robert Oppenheimer and his role in the development of the atomic bomb.', 'Drama', 'English', 2023, '3h 0m', 8.3, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Oppenheimer', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Jawan', 'A man is driven by a personal vendetta to rectify the wrongs in society, while keeping a promise made years ago.', 'Action', 'Hindi', 2023, '2h 49m', 6.7, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Jawan', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Vikram', 'A special agent investigates a case of serial killings. As he digs deeper, he uncovers a conspiracy that connects the murders to a powerful drug lord.', 'Thriller', 'Tamil', 2022, '2h 54m', 7.4, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Vikram', 'https://www.w3schools.com/html/mov_bbb.mp4', NOW()),

('Your Name', 'Two teenagers share a profound, magical connection upon discovering they are swapping bodies. Things get more complex when they try to meet in person.', 'Animation', 'Japanese', 2016, '1h 46m', 8.4, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Your+Name', 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4', NOW()),

('Money Heist', 'A criminal mastermind who goes by The Professor has a plan to pull off the biggest heist in recorded history.', 'Thriller', 'Spanish', 2017, '1h 10m', 8.2, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Money+Heist', 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4', NOW()),

('The Conjuring', 'Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.', 'Horror', 'English', 2013, '1h 52m', 7.5, 'https://placehold.co/300x450/1a1d29/00b8d9?text=The+Conjuring', 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4', NOW()),

('Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 'Romance', 'English', 1997, '3h 14m', 7.9, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Titanic', 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4', NOW()),

('Planet Earth II', 'David Attenborough returns with a new wildlife documentary that shows life in a variety of habitats around the world.', 'Documentary', 'English', 2016, '4h 58m', 9.5, 'https://placehold.co/300x450/1a1d29/00b8d9?text=Planet+Earth+II', 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4', NOW());

-- ========================================
-- Verify Data
-- ========================================
SELECT 'Users created:' AS info, COUNT(*) AS count FROM users;
SELECT 'Movies created:' AS info, COUNT(*) AS count FROM movies;
