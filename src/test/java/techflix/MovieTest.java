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
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);

        Movie secondMovie = new Movie();
        secondMovie.setId(1);
        secondMovie.setName("c");
        secondMovie.setDescription("d");
        actual = Solution.createMovie(secondMovie);

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

    @Test
    public void simpleTestDeleteMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        actual = Solution.deleteMovie(movie);
        assertEquals(OK, actual);

    }

    @Test
    public void simpleTestGetMovie() {
        Movie resultMovie = Solution.getMovie(10000);
        assertEquals(resultMovie.getId(), -1);
        assertEquals(resultMovie.getName(), null);
        assertEquals(resultMovie.getDescription(), null);

    }

    @Test
    public void simpleTestUpdateMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        movie.setDescription("c");
        actual = Solution.updateMovie(movie);
        assertEquals(OK, actual);

        Movie resultMovie = Solution.getMovie(1);
        assertEquals(resultMovie.getDescription(), "c");

    }
}
