package com.presidio.movieapp.Repository;
import com.presidio.movieapp.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

    @Query("SELECT m FROM Movie m WHERE m.name LIKE %?1% OR m.dName LIKE %?1% OR CONCAT(m.year, '') LIKE %?1% OR CONCAT(m.rating, '') LIKE %?1% OR m.language LIKE %?1%")
    List<Movie> findAll(String keyword);
    @Query("SELECT DISTINCT m.language FROM Movie m")
    List<String> findAllLanguages();
    @Query("SELECT m FROM Movie m WHERE m.language = :language")
    List<Movie> findByLanguage(String language);

}
