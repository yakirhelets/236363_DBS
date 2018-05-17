package techflix;

import org.junit.Test;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static techflix.business.ReturnValue.*;

public class AdvancedAPITest extends AbstractTest {

    @Test
    public void simpleTestMostInfluencing() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Movie secondMovie = new Movie();
        secondMovie.setId(2);
        secondMovie.setName("a");
        secondMovie.setDescription("b");
        actual = Solution.createMovie(secondMovie);
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

        Solution.addView(3, 1);
        Solution.addView(3, 2);

        Solution.addView(2, 1);
        Solution.addView(1, 2);
        actual = Solution.addMovieRating(1, 2, MovieRating.LIKE);

        ArrayList<Integer> results = Solution.mostInfluencingViewers();

        int first = results.get(0);
        int second = results.get(1);
        int third = results.get(2);
        assertEquals(3, first);
        assertEquals(1, second);
        assertEquals(2, third);

    }

    @Test
    public void simpleTestGetSimilarViewers() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("a");
        movie.setDescription("b");
        ReturnValue actual = Solution.createMovie(movie);
        assertEquals(OK, actual);

        Movie secondMovie = new Movie();
        secondMovie.setId(2);
        secondMovie.setName("a");
        secondMovie.setDescription("b");
        actual = Solution.createMovie(secondMovie);
        assertEquals(OK, actual);

        Movie thirdMovie = new Movie();
        thirdMovie.setId(3);
        thirdMovie.setName("a");
        thirdMovie.setDescription("b");
        actual = Solution.createMovie(thirdMovie);
        assertEquals(OK, actual);

        Movie fourthMovie = new Movie();
        fourthMovie.setId(4);
        fourthMovie.setName("a");
        fourthMovie.setDescription("b");
        actual = Solution.createMovie(fourthMovie);
        assertEquals(OK, actual);

        Movie fifthMovie = new Movie();
        fifthMovie.setId(5);
        fifthMovie.setName("a");
        fifthMovie.setDescription("b");
        actual = Solution.createMovie(fifthMovie);
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
        Solution.addView(1, 2);
        Solution.addView(1, 3);
        Solution.addView(1, 4);

        Solution.addView(2, 1);
        Solution.addView(2, 2);
        Solution.addView(2, 3);

        Solution.addView(3, 1);
        Solution.addView(3, 2);
        Solution.addView(3, 5);

        ArrayList<Integer> results = Solution.getSimilarViewers(1);

        int first = results.get(0);

        assertEquals(2, first);

    }
}
