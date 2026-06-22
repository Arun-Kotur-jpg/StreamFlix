/**
 * StreamFlix - Client-side JavaScript
 * Handles UI interactions, toast notifications, and smooth scrolling.
 */

document.addEventListener('DOMContentLoaded', function () {

    // ========== Navbar Scroll Effect ==========
    const navbar = document.querySelector('.sf-navbar');
    if (navbar) {
        window.addEventListener('scroll', function () {
            if (window.scrollY > 50) {
                navbar.style.background = 'rgba(15, 16, 20, 0.98)';
                navbar.style.boxShadow = '0 4px 20px rgba(0, 0, 0, 0.3)';
            } else {
                navbar.style.background = 'rgba(15, 16, 20, 0.85)';
                navbar.style.boxShadow = 'none';
            }
        });
    }

    // ========== Auto-dismiss Alerts ==========
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) {
                bsAlert.close();
            }
        }, 5000);
    });

    // ========== Smooth Scroll for Movie Rows ==========
    const movieRows = document.querySelectorAll('.sf-movie-row');
    movieRows.forEach(function (row) {
        let isDown = false;
        let startX;
        let scrollLeft;

        row.addEventListener('mousedown', function (e) {
            isDown = true;
            row.style.cursor = 'grabbing';
            startX = e.pageX - row.offsetLeft;
            scrollLeft = row.scrollLeft;
        });

        row.addEventListener('mouseleave', function () {
            isDown = false;
            row.style.cursor = 'grab';
        });

        row.addEventListener('mouseup', function () {
            isDown = false;
            row.style.cursor = 'grab';
        });

        row.addEventListener('mousemove', function (e) {
            if (!isDown) return;
            e.preventDefault();
            const x = e.pageX - row.offsetLeft;
            const walk = (x - startX) * 2;
            row.scrollLeft = scrollLeft - walk;
        });

        // Set initial cursor
        row.style.cursor = 'grab';
    });

    // ========== Movie Card Stagger Animation ==========
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                const cards = entry.target.querySelectorAll('.sf-movie-card');
                cards.forEach(function (card, index) {
                    card.style.opacity = '0';
                    card.style.transform = 'translateY(20px)';
                    setTimeout(function () {
                        card.style.transition = 'all 0.4s ease-out';
                        card.style.opacity = '1';
                        card.style.transform = 'translateY(0)';
                    }, index * 50);
                });
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    document.querySelectorAll('.sf-section').forEach(function (section) {
        observer.observe(section);
    });

    // ========== Search Input Focus Effect ==========
    const searchInput = document.querySelector('.sf-search-input');
    if (searchInput) {
        searchInput.addEventListener('focus', function () {
            this.parentElement.style.boxShadow = '0 0 0 3px rgba(0, 184, 217, 0.2)';
        });
        searchInput.addEventListener('blur', function () {
            this.parentElement.style.boxShadow = 'none';
        });
    }

    // ========== Delete Confirmation Enhancement ==========
    const deleteForms = document.querySelectorAll('form[onsubmit*="confirm"]');
    deleteForms.forEach(function (form) {
        form.addEventListener('submit', function (e) {
            // Confirmation is handled by inline onsubmit
        });
    });

    // ========== Video Player Enhancements ==========
    const videoPlayer = document.getElementById('videoPlayer');
    if (videoPlayer) {
        // Keyboard shortcuts
        document.addEventListener('keydown', function (e) {
            if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;

            switch (e.key) {
                case ' ':
                case 'k':
                    e.preventDefault();
                    if (videoPlayer.paused) {
                        videoPlayer.play();
                    } else {
                        videoPlayer.pause();
                    }
                    break;
                case 'f':
                    if (document.fullscreenElement) {
                        document.exitFullscreen();
                    } else {
                        videoPlayer.requestFullscreen();
                    }
                    break;
                case 'ArrowRight':
                    videoPlayer.currentTime += 10;
                    break;
                case 'ArrowLeft':
                    videoPlayer.currentTime -= 10;
                    break;
                case 'ArrowUp':
                    e.preventDefault();
                    videoPlayer.volume = Math.min(1, videoPlayer.volume + 0.1);
                    break;
                case 'ArrowDown':
                    e.preventDefault();
                    videoPlayer.volume = Math.max(0, videoPlayer.volume - 0.1);
                    break;
                case 'm':
                    videoPlayer.muted = !videoPlayer.muted;
                    break;
            }
        });
    }

    console.log('🎬 StreamFlix loaded successfully!');
});
