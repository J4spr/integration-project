# Use the official PostgreSQL image as the base
FROM postgres:latest

# Set environment variables for the default database
ENV POSTGRES_USER=game
ENV POSTGRES_PASSWORD=7sur7
ENV POSTGRES_DB=game

# Copy your SQL file to the special initialization folder
# Any .sql files here are executed when the container first starts
COPY src/main/resources/db.sql ./mockupData.sql /docker-entrypoint-initdb.d/

# 1. docker build -t my-game-db .
# 2. docker run -d --name pg-test -p 5433:5432 my-game-db
