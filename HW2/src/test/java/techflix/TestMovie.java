//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

import org.junit.Assert;
import org.junit.Test;
import techflix.business.Movie;
import techflix.business.ReturnValue;

public class TestMovie extends AbstractTest {
    public TestMovie() {
    }

    @Test
    public void testCreateMovie() {
        Movie badMovie = Movie.badMovie();
        ReturnValue actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setId(1);
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setName("movie1");
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setName((String)null);
        badMovie.setDescription("description1");
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setId(-1);
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setName("movie1");
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badMovie.setDescription((String)null);
        actual = Solution.createMovie(badMovie);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        actual = Solution.createMovie(movie1);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.createMovie(movie1);
        Assert.assertEquals(ReturnValue.ALREADY_EXISTS, actual);
    }

    @Test
    public void testGetMovie() {
        Movie actualMovie = Solution.getMovie(Integer.valueOf(1));
        Assert.assertEquals(Movie.badMovie(), actualMovie);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        actualMovie = Solution.getMovie(Integer.valueOf(1));
        Assert.assertEquals(movie1, actualMovie);
        actualMovie = Solution.getMovie(Integer.valueOf(-1));
        Assert.assertEquals(Movie.badMovie(), actualMovie);
    }

    @Test
    public void testDeleteMovie() {
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Movie movie2 = TestUtils.generateMovie(Integer.valueOf(2));
        ReturnValue actual = Solution.deleteMovie(movie1);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createMovie(movie1);
        Solution.createMovie(movie2);
        actual = Solution.deleteMovie(movie1);
        Assert.assertEquals(ReturnValue.OK, actual);
        Movie actualMovie = Solution.getMovie(Integer.valueOf(2));
        Assert.assertEquals(movie2, actualMovie);
        actual = Solution.deleteMovie(Movie.badMovie());
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
    }

    @Test
    public void testUpdateMovie() {
        Movie badMovie = Movie.badMovie();
        ReturnValue actual = Solution.updateMovie(badMovie);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        badMovie.setDescription("bad");
        actual = Solution.updateMovie(badMovie);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        badMovie.setId(1);
        badMovie.setDescription((String)null);
        actual = Solution.updateMovie(badMovie);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        actual = Solution.updateMovie(movie1);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createMovie(movie1);
        actual = Solution.updateMovie(movie1);
        Assert.assertEquals(ReturnValue.OK, actual);
        movie1.setDescription((String)null);
        actual = Solution.updateMovie(movie1);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        movie1.setDescription("description2");
        actual = Solution.updateMovie(movie1);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.updateMovie(movie1);
        Assert.assertEquals(ReturnValue.OK, actual);
        Assert.assertEquals(movie1, Solution.getMovie(Integer.valueOf(1)));
    }
}
