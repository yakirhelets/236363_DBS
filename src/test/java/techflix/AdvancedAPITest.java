package techflix;

import org.junit.Test;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

import java.util.ArrayList;

import static org.junit.Assert.*;
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
        assertEquals(OK, actual);


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

    @Test
    public void simpleTestgetMoviesRecommendations() {
        //case viewer id does not exist
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");
        ArrayList<Integer> results = Solution.getMoviesRecommendations(1);
        assertTrue(results.isEmpty());

        //case no similar viewers
        ReturnValue actual = Solution.createViewer(viewer);
        assertEquals(OK, actual);

        Movie[] movies = new Movie[12];
        for (int i=0 ; i<12 ; i++) {//Creating 12 movies
            movies[i] = new Movie();
            movies[i].setId(i+1);//id>0
            movies[i].setName("a");
            movies[i].setDescription("b");
            actual = Solution.createMovie(movies[i]);
            assertEquals(OK, actual);
        }

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

        for (int i=0 ; i<2 ; i++) {//Add 2 views to viewer 1
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertTrue(results.isEmpty());

        for (int i=9 ; i<11 ; i++) {//Add 2 views to viewer 2 + ratings
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertTrue(results.isEmpty());

        //case array is full
        for (int i=0 ; i<8 ; i++) {//Add 9 views to viewer 2 + ratings
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertFalse(results.isEmpty());

        for (int i=0 ; i<10 ; i++) {//similar amount of likes. should be in ascending order by id: [2,...,12]
            int id = results.get(i);
            assertEquals(i+3, id);
        }

        for (int i=0 ; i<12 ; i++) {//Add 9 views to viewer 2 + ratings
            actual = Solution.addView(3, i+1);
            assertEquals(OK, actual);
        }
        actual = Solution.addMovieRating(3,12, MovieRating.LIKE);
        assertEquals(OK, actual);

        results = Solution.getMoviesRecommendations(1);
        assertFalse(results.isEmpty());

        int id = results.get(0);//now movie #12 should be on top
        assertEquals(12, id);

        for (int i=1 ; i<10 ; i++) {//the rest are sorted by id
            id = results.get(i);
            assertEquals(i+2, id);
        }

        //case array is partly full
        for (int i=2 ; i<7 ; i++) {//Add 4 views to viewer 1
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertFalse(results.isEmpty());

        id = results.get(0);//now movie #12 should be on top
        assertEquals(12, id);

        assertEquals(6, results.size());//should be of size 6 because viewer 1 watched 6 movies out of 12

        for (int i=1 ; i<results.size() ; i++) {//the rest are sorted by id: [12,6,...,11]
            id = results.get(i);
            assertEquals(i+5, id);
        }

        //case viewer watched all the movies on the list
        for (int i=7 ; i<12 ; i++) {//Add 4 views to viewer 1
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertTrue(results.isEmpty());
    }

    public void simpleTestgetConditionalRecommendations() {
        //case viewer id does not exist
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");

        ReturnValue actual;
        Movie[] movies = new Movie[12];
        for (int i=0 ; i<12 ; i++) {//Creating 12 movies
            movies[i] = new Movie();
            movies[i].setId(i+1);//id>0
            movies[i].setName("b");
            movies[i].setDescription("b");
            actual = Solution.createMovie(movies[i]);
            assertEquals(OK, actual);
        }

        ArrayList<Integer> results = Solution.getConditionalRecommendations(1, 1);
        assertTrue(results.isEmpty());

        actual = Solution.addView(1,1);
        assertEquals(OK, actual);
        actual = Solution.addMovieRating(1,1, MovieRating.LIKE);
        assertEquals(OK, actual);

        results = Solution.getConditionalRecommendations(1, 1);
        assertTrue(results.isEmpty());

        //case no similar rankers
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

        for (int i=0 ; i<12 ; i++) {//Add all views to viewers 2,3
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addView(3, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1, 1);
        assertTrue(results.isEmpty());

        //case array is full
        for (int i=0 ; i<12 ; i++) {//Add all ranks to viewer 2
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);
        assertFalse(results.isEmpty());

        for (int i=0 ; i<10 ; i++) {//similar amount of likes. should be in ascending order by id: [2,...,12]
            int id = results.get(i);
            assertEquals(i+2, id);
        }

        actual = Solution.addMovieRating(3,12, MovieRating.LIKE);
        assertEquals(OK, actual);

        results = Solution.getConditionalRecommendations(1, 1);
        assertFalse(results.isEmpty());

        int id = results.get(0);//now movie #12 should be on top
        assertEquals(12, id);

        for (int i=1 ; i<10 ; i++) {//the rest are sorted by id
            id = results.get(i);
            assertEquals(i+2, id);
        }

        //case array is partly full
        for (int i=2 ; i<7 ; i++) {//Add 4 views to viewer 1
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);
        assertFalse(results.isEmpty());

        id = results.get(0);//now movie #12 should be on top
        assertEquals(12, id);

        assertEquals(6, results.size());//should be of size 6 because viewer 1 watched 6 movies out of 12

        for (int i=1 ; i<results.size() ; i++) {//the rest are sorted by id: [12,6,...,11]
            id = results.get(i);
            assertEquals(i+5, id);
        }

        //case viewer watched all the movies on the list
        for (int i=7 ; i<12 ; i++) {//Add 4 views to viewer 1
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);
        assertTrue(results.isEmpty());
    }
}
