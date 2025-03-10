package com.example.kinoxpbackend.controller;

import com.example.kinoxpbackend.model.Movie;
import com.example.kinoxpbackend.model.Show;
import com.example.kinoxpbackend.model.Theatre;
import com.example.kinoxpbackend.service.MovieService;
import com.example.kinoxpbackend.service.ShowService;
import com.example.kinoxpbackend.service.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;
    private final MovieService movieService;
    private final TheatreService theatreService;

    public ShowController(ShowService showService, MovieService movieService, TheatreService theatreService) {
        this.showService = showService;
        this.movieService = movieService;
        this.theatreService = theatreService;
    }

    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Optional<Show> show = showService.getShowById(id);
        return show.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/theatre/{theatreId}")
    public List<Show> getShowsByTheatre(@PathVariable Long theatreId) {
        return showService.getShowsByTheatre(theatreId);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/time-range")
    public List<Show> getShowsByTimeRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return showService.getShowsByTimeRange(start, end);
    }

    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        // Fetch full Movie and Theatre objects via service layer
        Optional<Movie> movie = movieService.getMovieById(show.getMovie().getMovieId());
        Optional<Theatre> theatre = theatreService.getTheatreById(show.getTheatre().getTheatreId());

        if (movie.isEmpty() || theatre.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Set the retrieved movie and theatre before saving
        show.setMovie(movie.get());
        show.setTheatre(theatre.get());

        Show savedShow = showService.createShow(show);
        return ResponseEntity.ok(savedShow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}

