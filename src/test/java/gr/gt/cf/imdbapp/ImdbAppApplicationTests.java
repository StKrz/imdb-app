package gr.gt.cf.imdbapp;

import gr.gt.cf.imdbapp.model.Movie;
import gr.gt.cf.imdbapp.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImdbAppApplicationTests {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Transactional
    @Rollback
    void testLiquibaseMigrations() {
        // Create a new movie entity
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setYear(2023);
        movie.setRunningTimeInMinutes(120);
        movie.setLeadActor("Test Actor");

        // Save the movie entity
        movie = movieRepository.save(movie);

        // Retrieve the movie entity
        List<Movie> retrievedMovie = movieRepository.findByTitle(movie.getTitle());

        // Verify the movie entity
        assertTrue(!retrievedMovie.isEmpty());
        assertEquals("Test Movie", retrievedMovie.get(0).getTitle());
        assertEquals(2023, retrievedMovie.get(0).getYear());
        assertEquals(120, retrievedMovie.get(0).getRunningTimeInMinutes());
        assertEquals("Test Actor", retrievedMovie.get(0).getLeadActor());
    }
}
