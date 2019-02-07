package techflix;

import org.junit.Test;
import techflix.business.Movie;
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
    public void simpleTestDeleteNonExistViewer() {
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");

        ReturnValue actual = Solution.deleteViewer(viewer);
        assertEquals(NOT_EXISTS, actual);
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
        viewer.setName(null);
        ReturnValue actual = Solution.updateViewer(viewer);
        assertEquals(NOT_EXISTS, actual);

        viewer.setName("a");
        actual = Solution.updateViewer(viewer);
        assertEquals(NOT_EXISTS, actual);

        viewer.setName("a");
        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        viewer.setName(null);
        actual = Solution.updateViewer(viewer);
        assertEquals(BAD_PARAMS, actual);

        viewer.setName("c");
        actual = Solution.updateViewer(viewer);
        assertEquals(OK, actual);

        Viewer resultViewer = Solution.getViewer(1);
        assertEquals(resultViewer.getName(), "c");
    }

    @Test
    public void simpleTestAddView() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");

        // adding a view absent movie or viewer
        ReturnValue actual = Solution.addView(1, 1);
        assertEquals(NOT_EXISTS, actual);

        actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");

        // adding a view without adding the viewer first
        actual = Solution.addView(1, 1);
        assertEquals(actual, NOT_EXISTS);

        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        actual = Solution.addView(1, 1);
        assertEquals(OK, actual);

        Movie anotherMovie = new Movie();
        anotherMovie.setId(3);
        anotherMovie.setName("bab");
        anotherMovie.setDescription("bab");

        // adding a view without adding the viewer first
        actual = Solution.addView(1, 2);
        assertEquals(NOT_EXISTS, actual);

        //already added view
        actual = Solution.addView(1, 1);
        assertEquals(ALREADY_EXISTS, actual);

        actual = Solution.createMovie(anotherMovie);
        assertEquals(OK, actual);
        actual = Solution.addView(1, 3);
        assertEquals(OK, actual);

        //already added view
        actual = Solution.addView(1, 3);
        assertEquals(ALREADY_EXISTS, actual);
    }

    @Test
    public void simpleTestRemoveView() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");

        // removing a view without adding the viewer or the movie
        ReturnValue actual = Solution.removeView(1, 1);
        assertEquals(NOT_EXISTS, actual);

        actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");

        // removing a view without adding the viewer
        actual = Solution.removeView(1, 1);
        assertEquals(NOT_EXISTS, actual);

        actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        // removing a view without adding the view itself
        actual = Solution.removeView(1, 1);
        assertEquals(NOT_EXISTS, actual);

        actual = Solution.addView(1, 1);
        assertEquals(OK, actual);
        actual = Solution.removeView(1, 1);
        assertEquals(OK, actual);

        //trying to remove a removed view
        actual = Solution.removeMovieRating(1, 1);
        assertEquals(NOT_EXISTS, actual);
    }
}
