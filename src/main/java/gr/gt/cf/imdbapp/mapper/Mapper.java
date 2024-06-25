package gr.gt.cf.imdbapp.mapper;

import gr.gt.cf.imdbapp.dto.MovieDTO;
import gr.gt.cf.imdbapp.model.Movie;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class Mapper {
    public MovieDTO map(Movie movie) throws EntityNotFoundException {
        if (movie == null) {
            return null;
        }
        return new MovieDTO(
                movie.getTitle(),
                movie.getYear(),
                movie.getRunningTimeInMinutes(),
                movie.getLeadActor());
    }
}
