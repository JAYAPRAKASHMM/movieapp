package com.presidio.movieapp.Controller;
import java.util.List;

import com.presidio.movieapp.Model.Movie;
import com.presidio.movieapp.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movies")
    public String viewMoviesPage(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/movies/showNewMovieForm")
    public String showNewMovieForm(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "new_movie";
    }

    @PostMapping("/movies/saveMovie")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {
        movieService.saveMovie(movie);
        return "saved";
    }

    @RequestMapping("/filterMovies")
    public String filter(Model model, @RequestParam("keyword") String key) {
        List<Movie> r;
        if (key != null && !key.isEmpty()) {
            r = movieService.listAll(key);
        } else {
            r = movieService.getAllMovies();
        }
        model.addAttribute("listMovies", r);
    return "filter";
}

    @GetMapping("/movies/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        Movie movie = movieService.getMovieById(id);

        model.addAttribute("movie", movie);
        return "update_movie";
    }

    @GetMapping("/movies/deleteMovie/{id}")
    public String deleteMovie(@PathVariable (value = "id") long id) {

        this.movieService.deleteMovieById(id);
        return "saved";
    }

    @GetMapping("/movies/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Movie> page = movieService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Movie> listMovies = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listMovies", listMovies);
        return "manage";
    }
    @GetMapping("/viewByLanguage")
    public String viewMoviesByLanguage(Model model, @RequestParam(name = "language", required = false) String language) {
        if (language != null && !language.isEmpty()) {
            List<Movie> moviesByLanguage = movieService.listMoviesByLanguage(language);
            model.addAttribute("moviesByLanguage", moviesByLanguage);
        }

        List<String> availableLanguages = movieService.getAllLanguages();
        model.addAttribute("languages", availableLanguages);

        return "findlang";
    }
    @GetMapping("/downloadMoviesData")
    public ResponseEntity<byte[]> downloadMoviesData() {
        // Logic to fetch all movies data and convert it to a text format
        String moviesData = movieService.getAllMoviesAsString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "movies_data.txt");

        return new ResponseEntity<>(moviesData.getBytes(), headers, 200);
    }
}

