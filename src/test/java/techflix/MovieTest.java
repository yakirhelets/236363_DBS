package techflix;

import org.junit.Test;
import techflix.business.Movie;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

import static org.junit.Assert.assertEquals;
import static techflix.business.ReturnValue.ALREADY_EXISTS;
import static techflix.business.ReturnValue.BAD_PARAMS;
import static techflix.business.ReturnValue.OK;

public class MovieTest extends AbstractTest {

    @Test
    public void simpleTestCreateMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);
    }

    @Test
    public void simpleTestCreateExistedMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("c");
        movie.setDescription("d");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(ALREADY_EXISTS, actual);
    }

    @Test
    public void simpleTestCreateMovieIllegalID() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(BAD_PARAMS, actual);
    }

    @Test
    public void simpleTestCreateMovieIllegalNameAndDescription() {
        Movie movie = new Movie();
        movie.setId(3);
        movie.setName(null);
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(BAD_PARAMS, actual);

        movie.setName("a");
        movie.setDescription(null);
        actual = Solution.createMovie(movie);
        assertEquals(BAD_PARAMS, actual);
    }

}
