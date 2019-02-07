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
        ReturnValue actual = Solution.createViewer(viewer);//first viewer created
        assertEquals(OK, actual);

        Movie[] movies = new Movie[12];
        for (int i=0 ; i<12 ; i++) {//Creating 12 movies. id's [1,...,12]
            movies[i] = new Movie();
            movies[i].setId(i+1);//id>0
            movies[i].setName("a");
            movies[i].setDescription("b");
            actual = Solution.createMovie(movies[i]);
            assertEquals(OK, actual);
        }

        Viewer secondViewer = new Viewer();//second viewer created
        secondViewer.setId(2);
        secondViewer.setName("a");
        actual = Solution.createViewer(secondViewer);
        assertEquals(OK, actual);

        Viewer thirdViewer = new Viewer();//third viewer created
        thirdViewer.setId(3);
        thirdViewer.setName("a");
        actual = Solution.createViewer(thirdViewer);
        assertEquals(OK, actual);

        for (int i=0 ; i<2 ; i++) {//viewer 1 watched movies [1,2]
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);//viewer 2,3 didn't watch any movie
        assertTrue(results.isEmpty());

        for (int i=9 ; i<12 ; i++) {//viewer 2 watched movies [10,11,12] and liked them.
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);
        assertTrue(results.isEmpty());

        //case array is full
        for (int i=0 ; i<9 ; i++) {//viewer 2 watched movies [1,2,3,4,5,6,7,8,9,10,11,12] and liked them.
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);//list should contain: [3,4,5,6,7,8,9,10,11,12]
        assertFalse(results.isEmpty());
        assertEquals(10,results.size());

        for (int i=0 ; i<10 ; i++) {//similar amount of likes. should be in ascending order by id: [3,...,12]
            int id = results.get(i);
            assertEquals(i+3, id);
        }

        for (int i=0 ; i<12 ; i++) {//viewer 3 watched all movies without rating
            actual = Solution.addView(3, i+1);
            assertEquals(OK, actual);
        }
        actual = Solution.addMovieRating(3,12, MovieRating.LIKE);//viewer 3 liked movie #12. now #12 have 2 likes.
        assertEquals(OK, actual);

        results = Solution.getMoviesRecommendations(1);//list should contain: [12,3,4,5,6,7,8,9,10,11] in that order
        assertFalse(results.isEmpty());
        assertEquals(10,results.size());

        int id = results.get(0);//now movie #12 should be on top: [12,...]
        assertEquals(12, id);

        for (int i=1 ; i<10 ; i++) {//the rest are sorted by id: [...,3,4,5,6,7,8,9,10,11]
            id = results.get(i);
            assertEquals(i+2, id);
        }

        //case array is partly full
        for (int i=2 ; i<7 ; i++) {//viewer 1 watched [1,2,3,4,5,6,7]
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);//list should contain: [12,8,9,10,11] in that order
        assertFalse(results.isEmpty());
        assertEquals(5,results.size());

        id = results.get(0);//now movie #12 should be on top
        assertEquals(12, id);

        for (int i=1 ; i<results.size() ; i++) {//the rest are sorted by id: [12,6,...,11]
            id = results.get(i);
            assertEquals(i+7, id);
        }

        //case viewer watched all the movies on the list
        for (int i=7 ; i<12 ; i++) {//viewer 1 watched [1,2,3,4,5,6,7,8,9,10,11,12] i.e watched everything.
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getMoviesRecommendations(1);//list should be empty - no movies left to watch
        assertTrue(results.isEmpty());
    }

    @Test
    public void simpleTestgetConditionalRecommendations() {
        //case viewer id does not exist
        Viewer viewer = new Viewer();
        viewer.setId(1);
        viewer.setName("a");

        ReturnValue actual;
        Movie[] movies = new Movie[12];
        for (int i=0 ; i<12 ; i++) {//Creating 12 movies. id's [1,...,12]
            movies[i] = new Movie();
            movies[i].setId(i+1);//id>0
            movies[i].setName("b");
            movies[i].setDescription("b");
            actual = Solution.createMovie(movies[i]);
            assertEquals(OK, actual);
        }

        actual = Solution.createViewer(viewer);//viewer 1 created
        assertEquals(OK, actual);

        ArrayList<Integer> results = Solution.getConditionalRecommendations(1, 1);
        assertTrue(results.isEmpty());

        actual = Solution.addView(1,1);//viewer 1 watched [1] and liked it
        assertEquals(OK, actual);
        actual = Solution.addMovieRating(1,1, MovieRating.LIKE);
        assertEquals(OK, actual);

        results = Solution.getConditionalRecommendations(1, 1);
        assertTrue(results.isEmpty());

        //case no similar rankers

        Viewer secondViewer = new Viewer();//viewer 2 created
        secondViewer.setId(2);
        secondViewer.setName("a");
        actual = Solution.createViewer(secondViewer);
        assertEquals(OK, actual);

        Viewer thirdViewer = new Viewer();//viewer 3 created
        thirdViewer.setId(3);
        thirdViewer.setName("a");
        actual = Solution.createViewer(thirdViewer);
        assertEquals(OK, actual);

        for (int i=0 ; i<12 ; i++) {//viewers 2,3 watched movies [1,...,12] i.e all of them
            actual = Solution.addView(2, i+1);
            assertEquals(OK, actual);
            actual = Solution.addView(3, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1, 1);//should be empty because there are no likes from viewers 2,3
        assertTrue(results.isEmpty());

        //case array is full
        for (int i=0 ; i<12 ; i++) {//viewer 2 liked all [1,...,12] movies
            actual = Solution.addMovieRating(2,i+1, MovieRating.LIKE);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);//list should contain [2,...,11] (max 10 movies)
        assertFalse(results.isEmpty());
        assertEquals(10,results.size());

        for (int i=0 ; i<10 ; i++) {//similar amount of likes. should be in ascending order by id: [2,...,11]
            int id = results.get(i);
            assertEquals(i+2, id);
        }

        actual = Solution.addMovieRating(3,12, MovieRating.LIKE);//viewer 3 liked movie #12
        assertEquals(OK, actual);
        actual = Solution.addMovieRating(3,1, MovieRating.LIKE);//viewer 3 liked movie #1
        assertEquals(OK, actual);

        results = Solution.getConditionalRecommendations(1, 1);//list should contain [12,2,...,10] (max 10 movies)
        assertFalse(results.isEmpty());
        assertEquals(10,results.size());


        int id = results.get(0);//now movie #12 should be on top: [12,...]
        assertEquals(12, id);

        for (int i=1 ; i<10 ; i++) {//the rest are sorted by id: [...,2,...,10]
            id = results.get(i);
            assertEquals(i+1, id);
        }

        //case array is partly full
        for (int i=1 ; i<7 ; i++) {//viewer 1 watched [1,2,3,4,5,6,7] - like [1]
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);////list should contain [12,8,9,10,11] in that order
        assertFalse(results.isEmpty());
        assertEquals(5,results.size());


        id = results.get(0);//movie #12 should still be on top
        assertEquals(12, id);

        for (int i=1 ; i<results.size() ; i++) {//the rest are sorted by id: [...,8,9,10,11]
            id = results.get(i);
            assertEquals(i+7, id);
        }

        //case viewer watched all the movies on the list
        for (int i=7 ; i<12 ; i++) {//viewer 1 watched [1,2,3,4,5,6,7,8,9,10,11,12] - like [1]
            actual = Solution.addView(1, i+1);
            assertEquals(OK, actual);
        }

        results = Solution.getConditionalRecommendations(1,1);//no movies left to watch
        assertTrue(results.isEmpty());
    }
}
