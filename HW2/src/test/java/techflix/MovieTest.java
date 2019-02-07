package techflix;

import org.junit.Test;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

import static org.junit.Assert.assertEquals;
import static techflix.business.ReturnValue.*;

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
        movie.setId(1);
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
        assertEquals(-1, resultMovie.getId());
        assertEquals(null, resultMovie.getName());
        assertEquals(null, resultMovie.getDescription());

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
        assertEquals("c", resultMovie.getDescription());

    }

    @Test
    public void simpleTestGetMovieViewCount() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        Viewer secondViewer = new Viewer();
        secondViewer.setId(2);
        secondViewer.setName("b");
        actual = Solution.createViewer(secondViewer);
        assertEquals(OK, actual);

        Viewer thirdViewer = new Viewer();
        thirdViewer.setId(3);
        thirdViewer.setName("c");
        actual = Solution.createViewer(thirdViewer);
        assertEquals(OK, actual);

        Solution.addView(1, 1);
        Solution.addView(2, 1);

        int res = (Integer)Solution.getMovieViewCount(1);
        assertEquals(2, res);

    }

    @Test
    public void simpleTestAddMovieRating() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
        // adding a rating without adding the view first
        assertEquals(NOT_EXISTS, actual);

        Solution.addView(1, 1);
        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
        assertEquals(OK, actual);

        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
        assertEquals(OK, actual);

        // TODO: check if it has changed to dislike?

    }

    @Test
    public void simpleTestRemoveMovieRating() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        actual = Solution.removeMovieRating(1, 1);
        // removing a rating without adding the view first
        assertEquals(NOT_EXISTS, actual);

        Solution.addView(1, 1);
        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
        assertEquals(OK, actual);

        actual = Solution.removeMovieRating(1, 1);
        assertEquals(OK, actual);

        // TODO: check if it has changed to null?

    }

    @Test
    public void simpleTestGetMovieLikesCount() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        Viewer secondViewer = new Viewer();
        secondViewer.setId(2);
        secondViewer.setName("a");
        actual = Solution.createViewer(secondViewer);
        assertEquals(OK, actual);

        Viewer thirdViewer = new Viewer();
        thirdViewer.setId(3);
        thirdViewer.setName("a");
        actual = Solution.createViewer(thirdViewer);
        assertEquals(OK, actual);

        Solution.addView(1, 1);
        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
        assertEquals(OK, actual);

        Solution.addView(2, 1);
        actual = Solution.addMovieRating(2, 1, MovieRating.LIKE);
        assertEquals(OK, actual);

        Solution.addView(3, 1);
        actual = Solution.addMovieRating(3, 1, MovieRating.DISLIKE);
        assertEquals(OK, actual);

        // Only two viewers liked the movie, the third one disliked it
        int res = Solution.getMovieLikesCount(1);
        assertEquals(2, res);

    }

    @Test
    public void simpleTestGetMovieDislikesCount() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        Viewer secondViewer = new Viewer();
        secondViewer.setId(2);
        secondViewer.setName("a");
        actual = Solution.createViewer(secondViewer);
        assertEquals(OK, actual);

        Viewer thirdViewer = new Viewer();
        thirdViewer.setId(3);
        thirdViewer.setName("a");
        actual = Solution.createViewer(thirdViewer);
        assertEquals(OK, actual);

        Solution.addView(1, 1);
        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
        assertEquals(OK, actual);

        Solution.addView(2, 1);
        actual = Solution.addMovieRating(2, 1, MovieRating.DISLIKE);
        assertEquals(OK, actual);

        Solution.addView(3, 1);
        actual = Solution.addMovieRating(3, 1, MovieRating.DISLIKE);
        assertEquals(OK, actual);

        // All three viewers disliked the movie
        int res = Solution.getMovieDislikesCount(1);
        assertEquals(3, res);

    }
}
