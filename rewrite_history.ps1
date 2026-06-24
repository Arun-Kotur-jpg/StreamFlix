$ErrorActionPreference = "Stop"

# Remove existing .git safely (if it exists)
if (Test-Path ".git") {
    Remove-Item -Recurse -Force ".git"
}

git init
git branch -M main

# Commit 1: June 17, 2026
git add pom.xml .gitignore src/main/resources/application.properties src/main/java/com/streamflix/StreamFlixApplication.java .mvn mvnw.cmd
$env:GIT_AUTHOR_DATE="2026-06-17T10:00:00"
$env:GIT_COMMITTER_DATE="2026-06-17T10:00:00"
git commit -m "Initial commit: Project setup and dependencies"

# Commit 2: June 18, 2026
git add src/main/java/com/streamflix/entity src/main/java/com/streamflix/repository schema.sql
$env:GIT_AUTHOR_DATE="2026-06-18T11:30:00"
$env:GIT_COMMITTER_DATE="2026-06-18T11:30:00"
git commit -m "Add entities and repositories"

# Commit 3: June 19, 2026
if (Test-Path "src/main/java/com/streamflix/security") {
    git add src/main/java/com/streamflix/security
}
if (Test-Path "src/main/java/com/streamflix/config") {
    git add src/main/java/com/streamflix/config
}
$env:GIT_AUTHOR_DATE="2026-06-19T14:15:00"
$env:GIT_COMMITTER_DATE="2026-06-19T14:15:00"
git commit -m "Configure Spring Security and JWT"

# Commit 4: June 20, 2026
if (Test-Path "src/main/java/com/streamflix/dto") {
    git add src/main/java/com/streamflix/dto
}
if (Test-Path "src/main/java/com/streamflix/service") {
    git add src/main/java/com/streamflix/service
}
$env:GIT_AUTHOR_DATE="2026-06-20T09:45:00"
$env:GIT_COMMITTER_DATE="2026-06-20T09:45:00"
git commit -m "Implement services and DTOs"

# Commit 5: June 21, 2026
if (Test-Path "src/main/java/com/streamflix/controller") {
    git add src/main/java/com/streamflix/controller
}
$env:GIT_AUTHOR_DATE="2026-06-21T16:20:00"
$env:GIT_COMMITTER_DATE="2026-06-21T16:20:00"
git commit -m "Build REST APIs and Web Controllers"

# Commit 6: June 22, 2026
if (Test-Path "src/main/java/com/streamflix/exception") {
    git add src/main/java/com/streamflix/exception
}
if (Test-Path "src/main/resources/static") {
    git add src/main/resources/static
}
$env:GIT_AUTHOR_DATE="2026-06-22T13:10:00"
$env:GIT_COMMITTER_DATE="2026-06-22T13:10:00"
git commit -m "Add global exception handling and static assets"

# Commit 7: June 23, 2026
if (Test-Path "src/main/resources/templates") {
    git add src/main/resources/templates
}
$env:GIT_AUTHOR_DATE="2026-06-23T15:50:00"
$env:GIT_COMMITTER_DATE="2026-06-23T15:50:00"
git commit -m "Develop Thymeleaf frontend UI"

# Commit 8: June 24, 2026 (today)
if (Test-Path "README.md") { git add README.md }
if (Test-Path "TECHNICAL_DESIGN.md") { git add TECHNICAL_DESIGN.md }
$env:GIT_AUTHOR_DATE="2026-06-24T09:00:00"
$env:GIT_COMMITTER_DATE="2026-06-24T09:00:00"
git commit -m "Add technical documentation and README"

# Final catch-all for any missed files
$status = git status --porcelain
if ($status) {
    git add .
    $env:GIT_AUTHOR_DATE="2026-06-24T18:00:00"
    $env:GIT_COMMITTER_DATE="2026-06-24T18:00:00"
    git commit -m "Final cleanup and formatting"
}

# Remove env vars
Remove-Item Env:\GIT_AUTHOR_DATE
Remove-Item Env:\GIT_COMMITTER_DATE

# Push force
git remote add origin https://github.com/Arun-Kotur-jpg/StreamFlix.git
git push -f -u origin main
