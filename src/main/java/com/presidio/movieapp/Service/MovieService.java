package com.presidio.movieapp.Service;
import java.util.List;

import com.presidio.movieapp.Model.Movie;
import org.springframework.data.domain.Page;


public interface MovieService {

    List<Movie> getAllMovies();
    List<Movie> listAll(String key);

    void saveMovie(Movie movie);
    Movie getMovieById(long id);
    void deleteMovieById(long id);
    List<Movie> listMoviesByLanguage(String language);
    List<String> getAllLanguages();
    public String getAllMoviesAsString();
    Page<Movie> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}