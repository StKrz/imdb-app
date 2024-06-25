package gr.gt.cf.imdbapp.service;

import gr.gt.cf.imdbapp.model.Movie;

import javax.persistence.EntityNotFoundException;

public interface IMovieService {
    Movie getByTitle(String title) throws EntityNotFoundException;
}
