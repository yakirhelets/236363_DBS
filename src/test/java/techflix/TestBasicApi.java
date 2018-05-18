//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

import org.junit.Assert;
import org.junit.Test;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

public class TestBasicApi extends AbstractTest {
    public TestBasicApi() {
    }

    @Test
    public void testAddView() {
        ReturnValue actual = Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        actual = Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        actual = Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.ALREADY_EXISTS, actual);
        actual = Solution.addView(Integer.valueOf(-1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        actual = Solution.addView(Integer.valueOf(1), Integer.valueOf(-1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
    }

    @Test
    public void testRemoveView() {
        ReturnValue actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        actual = Solution.removeView(Integer.valueOf(-1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        actual = Solution.removeView(Integer.valueOf(1), Integer.valueOf(-1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
    }

    @Test
    public void testMovieViewCount() {
        int actual = Solution.getMovieViewCount(Integer.valueOf(1)).intValue();
        Assert.assertEquals(0L, (long)actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        Viewer viewer2 = TestUtils.generateViewer(Integer.valueOf(2));
        Solution.createViewer(viewer2);
        Viewer viewer3 = TestUtils.generateViewer(Integer.valueOf(3));
        Solution.createViewer(viewer3);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(2), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieViewCount(Integer.valueOf(1)).intValue();
        Assert.assertEquals(3L, (long)actual);
        Solution.removeView(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieViewCount(Integer.valueOf(1)).intValue();
        Assert.assertEquals(2L, (long)actual);
        Solution.deleteViewer(viewer1);
        actual = Solution.getMovieViewCount(Integer.valueOf(1)).intValue();
        Assert.assertEquals(1L, (long)actual);
        Solution.deleteMovie(movie1);
        actual = Solution.getMovieViewCount(Integer.valueOf(1)).intValue();
        Assert.assertEquals(0L, (long)actual);
    }

    @Test
    public void testAddMovieRating() {
        ReturnValue actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Assert.assertEquals(ReturnValue.OK, actual);
    }

    @Test
    public void testRemoveMovieRating() {
        ReturnValue actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Solution.removeView(Integer.valueOf(1), Integer.valueOf(1));
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Solution.deleteViewer(viewer1);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createViewer(viewer1);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Solution.deleteViewer(viewer1);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createViewer(viewer1);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Solution.deleteMovie(movie1);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createMovie(movie1);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Solution.deleteMovie(movie1);
        actual = Solution.removeMovieRating(Integer.valueOf(1), Integer.valueOf(1));
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
    }

    @Test
    public void testMovieLikesCount() {
        int actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(0L, (long)actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        Viewer viewer2 = TestUtils.generateViewer(Integer.valueOf(2));
        Solution.createViewer(viewer2);
        Viewer viewer3 = TestUtils.generateViewer(Integer.valueOf(3));
        Solution.createViewer(viewer3);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(2), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(3), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.LIKE);
        Solution.addMovieRating(Integer.valueOf(2), Integer.valueOf(1), MovieRating.DISLIKE);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.LIKE);
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.DISLIKE);
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.removeMovieRating(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.LIKE);
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.removeView(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.addView(Integer.valueOf(3), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.LIKE);
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.deleteViewer(viewer3);
        actual = Solution.getMovieLikesCount(1);
        Assert.assertEquals(1L, (long)actual);
    }

    @Test
    public void testMovieDislikeCount() {
        int actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(0L, (long)actual);
        Movie movie1 = TestUtils.generateMovie(Integer.valueOf(1));
        Solution.createMovie(movie1);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        Viewer viewer2 = TestUtils.generateViewer(Integer.valueOf(2));
        Solution.createViewer(viewer2);
        Viewer viewer3 = TestUtils.generateViewer(Integer.valueOf(3));
        Solution.createViewer(viewer3);
        Solution.addView(Integer.valueOf(1), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(2), Integer.valueOf(1));
        Solution.addView(Integer.valueOf(3), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(1), Integer.valueOf(1), MovieRating.DISLIKE);
        Solution.addMovieRating(Integer.valueOf(2), Integer.valueOf(1), MovieRating.LIKE);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.DISLIKE);
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.LIKE);
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.removeMovieRating(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.DISLIKE);
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.removeView(Integer.valueOf(3), Integer.valueOf(1));
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(1L, (long)actual);
        Solution.addView(Integer.valueOf(3), Integer.valueOf(1));
        Solution.addMovieRating(Integer.valueOf(3), Integer.valueOf(1), MovieRating.DISLIKE);
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(2L, (long)actual);
        Solution.deleteViewer(viewer3);
        actual = Solution.getMovieDislikesCount(1);
        Assert.assertEquals(1L, (long)actual);
    }
}
