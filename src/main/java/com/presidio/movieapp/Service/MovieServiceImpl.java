package com.presidio.movieapp.Service;

import java.util.List;
import java.util.Optional;
import com.presidio.movieapp.Model.Movie;
import com.presidio.movieapp.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> listAll(String key) {
        return movieRepository.findAll(key);
    }

    @Override
    public void saveMovie(Movie movie) {
        this.movieRepository.save(movie);
    }
    @Override
    public Movie getMovieById(long id) {
        Optional<Movie> optional = movieRepository.findById(id);
        Movie movie = null;
        if (optional.isPresent()) {
            movie = optional.get();
        } else {
            throw new RuntimeException(" Movie not found for id :: " + id);
        }
        return movie;
    }
    @Override
    public void deleteMovieById(long id) {
        this.movieRepository.deleteById(id);
    }
    @Override
    public Page<Movie> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.movieRepository.findAll(pageable);
    }
    @Override
    public List<Movie> listMoviesByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    @Override
    public List<String> getAllLanguages() {
        return movieRepository.findAllLanguages();
    }
    @Override
    public String getAllMoviesAsString() {
        List<Movie> movies = movieRepository.findAll();
        StringBuilder moviesData = new StringBuilder();
        for (Movie movie : movies) {
            moviesData.append("Movie Name: ").append(movie.getName()).append(", ");
            moviesData.append("Director Name: ").append(movie.getdName()).append(", ");
            moviesData.append("Release Year: ").append(movie.getYear()).append(", ");
            moviesData.append("Rating: ").append(movie.getRating()).append(", ");
            moviesData.append("Language: ").append(movie.getLanguage()).append("\n");
        }
        return moviesData.toString();
    }
}

