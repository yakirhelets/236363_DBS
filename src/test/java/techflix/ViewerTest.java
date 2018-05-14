package techflix;

import org.junit.Test;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

import static org.junit.Assert.assertEquals;
import static techflix.business.ReturnValue.*;

public class ViewerTest extends AbstractTest {

    @Test
    public void simpleTestCreateMovie() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);
    }

    @Test
    public void simpleTestCreateExistedViewer() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        ReturnValue actual = Solution.createViewer(viewer);

        Viewer anotherViewer = new Viewer();
        anotherViewer.setId(1);
        anotherViewer.setName("baaa");
        actual = Solution.createViewer(anotherViewer);

        assertEquals(ALREADY_EXISTS, actual);
    }

    @Test
    public void simpleTestCreateViewerIllegalID() {
        Viewer viewer = new Viewer();
        viewer.setId(0);
        viewer.setName("a");
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(BAD_PARAMS, actual);

        viewer = new Viewer();
        viewer.setId(-1);
        viewer.setName("b");
        actual = Solution.createViewer(viewer);
        assertEquals(BAD_PARAMS, actual);
    }

    @Test
    public void simpleTestCreateViewerIllegalName() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName(null);
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(BAD_PARAMS, actual);
    }

    @Test
    public void simpleTestDeleteViewer() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        actual = Solution.deleteViewer(viewer);
        assertEquals(OK, actual);

    }

    @Test
    public void simpleTestGetViewer() {
        Viewer resultViewer = Solution.getViewer(10000);
        assertEquals(resultViewer.getId(), -1);
        assertEquals(resultViewer.getName(), null);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("abcd");
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        resultViewer = Solution.getViewer(1);
        assertEquals(resultViewer.getId(), 1);
        assertEquals(resultViewer.getName(), "abcd");
    }

    @Test
    public void simpleTestUpdateViewer() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        viewer.setName("c");
        actual = Solution.updateViewer(viewer);
        assertEquals(OK, actual);

        Viewer resultViewer = Solution.getViewer(1);
        assertEquals(resultViewer.getName(), "c");
        //TODO: case updating invalid name
        //test
    }

//    @Test
//    public void simpleTestGetMovieViewCount() {
//        Movie movie = new Movie();
//        movie.setId(1);
//        movie.setName("a");
//        movie.setDescription("b");
//        ReturnValue actual = Solution.createMovie(movie);
//        assertEquals(OK, actual);
//
//        Viewer viewer = new Viewer();
//        viewer.setId(1);
//        viewer.setName("a");
//        actual = Solution.createViewer(viewer);
//        assertEquals(OK, actual);
//
//        Viewer secondViewer = new Viewer();
//        secondViewer.setId(2);
//        secondViewer.setName("b");
//        actual = Solution.createViewer(secondViewer);
//        assertEquals(OK, actual);
//
//        Viewer thirdViewer = new Viewer();
//        thirdViewer.setId(3);
//        thirdViewer.setName("c");
//        actual = Solution.createViewer(thirdViewer);
//        assertEquals(OK, actual);
//
//        Solution.addView(1, 1);
//        Solution.addView(2, 1);
//
//        int res = (Integer)Solution.getMovieViewCount(1);
//        assertEquals(res, 2);
//
//    }
//
//    @Test
//    public void simpleTestAddMovieRating() {
//        Movie movie = new Movie();
//        movie.setId(1);
//        movie.setName("a");
//        movie.setDescription("b");
//        ReturnValue actual = Solution.createMovie(movie);
//        assertEquals(OK, actual);
//
//        Viewer viewer = new Viewer();
//        viewer.setId(1);
//        viewer.setName("a");
//        actual = Solution.createViewer(viewer);
//        assertEquals(OK, actual);
//
//        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
//        // adding a rating without adding the view first
//        assertEquals(actual, NOT_EXISTS);
//
//        Solution.addView(1, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
//        assertEquals(actual, OK);
//
//        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
//        assertEquals(actual, OK);
//
//        // TODO: check if it has changed to dislike?
//
//    }
//
//    @Test
//    public void simpleTestRemoveMovieRating() {
//        Movie movie = new Movie();
//        movie.setId(1);
//        movie.setName("a");
//        movie.setDescription("b");
//        ReturnValue actual = Solution.createMovie(movie);
//        assertEquals(OK, actual);
//
//        Viewer viewer = new Viewer();
//        viewer.setId(1);
//        viewer.setName("a");
//        actual = Solution.createViewer(viewer);
//        assertEquals(OK, actual);
//
//        actual = Solution.removeMovieRating(1, 1);
//        // removing a rating without adding the view first
//        assertEquals(actual, NOT_EXISTS);
//
//        Solution.addView(1, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
//        assertEquals(actual, OK);
//
//        actual = Solution.removeMovieRating(1, 1);
//        assertEquals(actual, OK);
//
//        // TODO: check if it has changed to null?
//
//    }
//
//    @Test
//    public void simpleTestGetMovieLikesCount() {
//        Movie movie = new Movie();
//        movie.setId(1);
//        movie.setName("a");
//        movie.setDescription("b");
//        ReturnValue actual = Solution.createMovie(movie);
//        assertEquals(OK, actual);
//
//        Viewer viewer = new Viewer();
//        viewer.setId(1);
//        viewer.setName("a");
//        actual = Solution.createViewer(viewer);
//        assertEquals(OK, actual);
//
//        Viewer secondViewer = new Viewer();
//        secondViewer.setId(2);
//        secondViewer.setName("a");
//        actual = Solution.createViewer(secondViewer);
//        assertEquals(OK, actual);
//
//        Viewer thirdViewer = new Viewer();
//        thirdViewer.setId(3);
//        thirdViewer.setName("a");
//        actual = Solution.createViewer(thirdViewer);
//        assertEquals(OK, actual);
//
//        Solution.addView(1, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
//        assertEquals(actual, OK);
//
//        Solution.addView(2, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.LIKE);
//        assertEquals(actual, OK);
//
//        Solution.addView(3, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
//        assertEquals(actual, OK);
//
//        // Only two viewers liked the movie, the third one disliked it
//        int res = Solution.getMovieLikesCount(1);
//        assertEquals(res, 2);
//
//    }
//
//    @Test
//    public void simpleTestGetMovieDislikesCount() {
//        Movie movie = new Movie();
//        movie.setId(1);
//        movie.setName("a");
//        movie.setDescription("b");
//        ReturnValue actual = Solution.createMovie(movie);
//        assertEquals(OK, actual);
//
//        Viewer viewer = new Viewer();
//        viewer.setId(1);
//        viewer.setName("a");
//        actual = Solution.createViewer(viewer);
//        assertEquals(OK, actual);
//
//        Viewer secondViewer = new Viewer();
//        secondViewer.setId(2);
//        secondViewer.setName("a");
//        actual = Solution.createViewer(secondViewer);
//        assertEquals(OK, actual);
//
//        Viewer thirdViewer = new Viewer();
//        thirdViewer.setId(3);
//        thirdViewer.setName("a");
//        actual = Solution.createViewer(thirdViewer);
//        assertEquals(OK, actual);
//
//        Solution.addView(1, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
//        assertEquals(actual, OK);
//
//        Solution.addView(2, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
//        assertEquals(actual, OK);
//
//        Solution.addView(3, 1);
//        actual = Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
//        assertEquals(actual, OK);
//
//        // All three viewers disliked the movie
//        int res = Solution.getMovieDislikesCount(1);
//        assertEquals(res, 3);
//
//    }
}
