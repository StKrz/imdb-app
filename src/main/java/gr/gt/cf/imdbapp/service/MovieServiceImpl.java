package gr.gt.cf.imdbapp.service;

import gr.gt.cf.imdbapp.model.Movie;
import gr.gt.cf.imdbapp.repository.MovieRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityNotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {

    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;

    @Value("${imdb.api.key}")
    private String apiKey;

    @Value("${imdb.api.host}")
    private String apiHost;

    @Value("${imdb.api.url}")
    private String apiUrl;

    public MovieServiceImpl(RestTemplateBuilder restTemplateBuilder, MovieRepository movieRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getByTitle(String title) throws EntityNotFoundException {
        List<Movie> movies = movieRepository.findByTitle(title);
        if (!movies.isEmpty()) {
            return movies.get(0);
        }

        String url = apiUrl + title;
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", apiHost);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode moviesObj = mapper.readTree(response.getBody());

            // Check if there are results
            if (moviesObj.path("results").isArray() && moviesObj.path("results").size() > 0) {
                JsonNode firstMovie = moviesObj.path("results").get(0);

                String movieTitle = firstMovie.path("title").asText();
                List<Movie> existingMovie = movieRepository.findByTitle(movieTitle);
                if (!existingMovie.isEmpty()) {
                    return existingMovie.get(0);
                }

                Movie movie = new Movie();
                movie.setTitle(movieTitle);
                movie.setYear(firstMovie.path("year").asInt());
                movie.setRunningTimeInMinutes(firstMovie.path("runningTimeInMinutes").asInt());

                // Here we check the path for lead actor
                if (firstMovie.has("principals") && firstMovie.path("principals").isArray()) {
                    JsonNode principalsArray = firstMovie.path("principals");
                    if (principalsArray.size() > 0) {
                        JsonNode leadActorNode = principalsArray.get(0);
                        movie.setLeadActor(leadActorNode.path("name").asText());
                    } else {
                        movie.setLeadActor("N/A");
                    }
                } else {
                    movie.setLeadActor("N/A");
                }

                // Save in Database
                movieRepository.save(movie);
                return movie;

            } else {
                System.out.println("Movie with title {" + title + "} wasn't found.");
                throw new EntityNotFoundException("Movie with title {" + title + "} wasn't found.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }
}
