package gr.gt.cf.imdbapp.controller;

import gr.gt.cf.imdbapp.mapper.Mapper;
import gr.gt.cf.imdbapp.dto.MovieDTO;
import gr.gt.cf.imdbapp.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private final MovieServiceImpl movieService;
    @Autowired
    private final Mapper mapper;

    public MovieController(MovieServiceImpl movieService, Mapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

    @GetMapping("/title/find")
    public MovieDTO getMovie(@RequestParam String title) {
        return mapper.map(movieService.getByTitle(title));
    }
}
