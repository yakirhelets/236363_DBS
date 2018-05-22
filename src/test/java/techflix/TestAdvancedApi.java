//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import techflix.TestAdvancedApi.ViewerInformation;
import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.Viewer;

public class TestAdvancedApi extends AbstractTest {
    public TestAdvancedApi() {
    }

    @Test
    public void testSimilarUsers() {
        Set<Movie> movies = this.generateMovies();
        Set<Viewer> viewers = this.generateViewers();
        Map<Integer, Set<Integer>> views = this.generateViews(viewers, movies);
        Iterator var4 = views.keySet().iterator();

        while(var4.hasNext()) {
            Integer viewerId = (Integer)var4.next();
            ArrayList<Integer> similarUsers = this.getSimilarUsers(views, viewerId);
            ArrayList<Integer> actual = Solution.getSimilarViewers(viewerId);
            Assert.assertEquals(similarUsers, actual);
        }

    }

    @Test
    public void testInfluencingViewers() {
        Set<Movie> movies = this.generateMovies();
        Set<Viewer> viewers = this.generateViewers();
        Map<Integer, Set<Integer>> views = this.generateViews(viewers, movies);
        Map<Integer, Map<MovieRating, Set<Integer>>> ratings = this.generateRatings(views);
        Map<Integer, Set<Integer>> viewersReating = this.createViewerRatings(ratings);
        List<Integer> mostInfluencing = this.getMostInfluencingViewers(views, viewersReating);
        Assert.assertEquals(mostInfluencing, Solution.mostInfluencingViewers());
    }

    private List<Integer> getMostInfluencingViewers(Map<Integer, Set<Integer>> views, Map<Integer, Set<Integer>> viewersReating) {
        new ArrayList();
        Map<Integer, ViewerInformation> informationMap = new HashMap();
        Iterator var5 = views.keySet().iterator();

        Integer viewerId;
        ViewerInformation information;
        while(var5.hasNext()) {
            viewerId = (Integer)var5.next();
            information = new ViewerInformation(this);
            information.setViewerId(viewerId);
            information.setViewsCount(Integer.valueOf(((Set)views.get(viewerId)).size()));
            informationMap.put(viewerId, information);
        }

        var5 = viewersReating.keySet().iterator();

        while(var5.hasNext()) {
            viewerId = (Integer)var5.next();
            information = (ViewerInformation)informationMap.get(viewerId);
            information.setRatingsCount(Integer.valueOf(((Set)viewersReating.get(viewerId)).size()));
        }

        return (List)informationMap.values().stream().sorted().mapToInt((value) -> {
            return value.viewerId.intValue();
        }).limit(10L).boxed().collect(Collectors.toList());
    }

    @Test
    public void testMoviesRecommendations() {
        Set<Movie> movies = this.generateMovies();
        Set<Viewer> viewers = this.generateViewers();
        Map<Integer, Set<Integer>> views = this.generateViews(viewers, movies);
        Map<Integer, Map<MovieRating, Set<Integer>>> ratings = this.generateRatings(views);
        Iterator var5 = viewers.iterator();

        while(var5.hasNext()) {
            Viewer viewer = (Viewer)var5.next();
            Integer viewerId = Integer.valueOf(viewer.getId());
            ArrayList<Integer> similarViewers = this.getSimilarUsers(views, viewerId);
            List<Integer> recommendations = this.getRecommendations(viewerId, views, similarViewers, ratings);
            Assert.assertEquals(recommendations, Solution.getMoviesRecommendations(viewerId));
        }

    }

    @Test
    public void testConditionalRecommendations() {
        Set<Movie> movies = this.generateMovies();
        Set<Viewer> viewers = this.generateViewers();
        Map<Integer, Set<Integer>> views = this.generateViews(viewers, movies);
        Map<Integer, Map<MovieRating, Set<Integer>>> ratings = this.generateRatings(views);
        Iterator var5 = viewers.iterator();

        while(var5.hasNext()) {
            Viewer viewer = (Viewer)var5.next();
            Iterator var7 = ratings.keySet().iterator();

            while(var7.hasNext()) {
                Integer movieId = (Integer)var7.next();
                Integer viewerId = Integer.valueOf(viewer.getId());
                ArrayList<Integer> similarViewers = this.getSimilarUsers(views, viewerId);
                List<Integer> recommendations = this.getRecommendations(viewerId, movieId, views, similarViewers, ratings);
                Assert.assertEquals(recommendations, Solution.getConditionalRecommendations(viewerId, movieId.intValue()));
            }
        }

    }

    private Boolean wasViewed(Collection<Integer> relevantViewers, Map<Integer, Set<Integer>> views, Integer movie) {
        if(views.isEmpty()) {
            return Boolean.valueOf(false);
        } else {
            Set<Integer> movieViewers = (Set)views.entrySet().stream().filter((e) -> {
                return ((Set)e.getValue()).contains(movie);
            }).mapToInt((e) -> {
                return ((Integer)e.getKey()).intValue();
            }).boxed().collect(Collectors.toSet());
            if(movieViewers.isEmpty()) {
                return Boolean.valueOf(false);
            } else {
                Iterator var5 = relevantViewers.iterator();

                Integer viewer;
                do {
                    if(!var5.hasNext()) {
                        return Boolean.valueOf(false);
                    }

                    viewer = (Integer)var5.next();
                } while(!movieViewers.contains(viewer));

                return Boolean.valueOf(true);
            }
        }
    }

    private List<Integer> getRecommendations(Integer viewerId, Integer movie, Map<Integer, Set<Integer>> views, ArrayList<Integer> similarViewers, Map<Integer, Map<MovieRating, Set<Integer>>> ratings) {
        Set<Integer> currentViewerViews = (Set)views.get(viewerId);
        Map<Integer, Integer> likesCounter = new HashMap();
        Set<Integer> rankers = ((Set)((Map)ratings.get(movie)).get(MovieRating.LIKE)).contains(viewerId)?new HashSet((Collection)((Map)ratings.get(movie)).get(MovieRating.LIKE)):(((Set)((Map)ratings.get(movie)).get(MovieRating.DISLIKE)).contains(viewerId)?new HashSet((Collection)((Map)ratings.get(movie)).get(MovieRating.DISLIKE)):new HashSet());
        rankers.retainAll(similarViewers);
        if(rankers.isEmpty()) {
            return Collections.emptyList();
        } else {
            Iterator var9 = ratings.keySet().iterator();

            while(var9.hasNext()) {
                Integer movieId = (Integer)var9.next();
                Set<Integer> likedViewers = (Set)((Map)ratings.get(movieId)).get(MovieRating.LIKE);
                if(!likesCounter.containsKey(movieId) && this.wasViewed(rankers, views, movieId).booleanValue()) {
                    likesCounter.put(movieId, Integer.valueOf(0));
                }

                Iterator var12 = likedViewers.iterator();

                while(var12.hasNext()) {
                    Integer likedViewer = (Integer)var12.next();
                    if(rankers.contains(likedViewer)) {
                        likesCounter.put(movieId, Integer.valueOf(((Integer)likesCounter.get(movieId)).intValue() + 1));
                    }
                }
            }

            return (List)likesCounter.entrySet().stream().sorted((o1, o2) -> {
                return o1.getValue() == o2.getValue()?((Integer)o1.getKey()).compareTo((Integer)o2.getKey()):((Integer)o2.getValue()).compareTo((Integer)o1.getValue());
            }).filter((e) -> {
                return !currentViewerViews.contains(e.getKey());
            }).limit(10L).mapToInt((e) -> {
                return ((Integer)e.getKey()).intValue();
            }).boxed().collect(Collectors.toList());
        }
    }

    private List<Integer> getRecommendations(Integer viewerId, Map<Integer, Set<Integer>> views, ArrayList<Integer> similarViewers, Map<Integer, Map<MovieRating, Set<Integer>>> ratings) {
        Set<Integer> currentViewerViews = (Set)views.get(viewerId);
        Map<Integer, Integer> likesCounter = new HashMap();
        if(similarViewers.isEmpty()) {
            return Collections.emptyList();
        } else {
            Iterator var7 = ratings.keySet().iterator();

            while(var7.hasNext()) {
                Integer movieId = (Integer)var7.next();
                Set<Integer> likedViewers = (Set)((Map)ratings.get(movieId)).get(MovieRating.LIKE);
                if(!likesCounter.containsKey(movieId) && this.wasViewed(similarViewers, views, movieId).booleanValue()) {
                    likesCounter.put(movieId, Integer.valueOf(0));
                }

                Iterator var10 = likedViewers.iterator();

                while(var10.hasNext()) {
                    Integer likedViewer = (Integer)var10.next();
                    if(similarViewers.contains(likedViewer)) {
                        likesCounter.put(movieId, Integer.valueOf(((Integer)likesCounter.get(movieId)).intValue() + 1));
                    }
                }
            }

            return (List)likesCounter.entrySet().stream().sorted((o1, o2) -> {
                return o1.getValue() == o2.getValue()?((Integer)o1.getKey()).compareTo((Integer)o2.getKey()):((Integer)o2.getValue()).compareTo((Integer)o1.getValue());
            }).filter((e) -> {
                return !currentViewerViews.contains(e.getKey());
            }).limit(10L).mapToInt((e) -> {
                return ((Integer)e.getKey()).intValue();
            }).boxed().collect(Collectors.toList());
        }
    }

    Set<Movie> generateMovies() {
        Set<Movie> movies = new HashSet();

        for(int i = 1; i <= 50; ++i) {
            Movie movie = TestUtils.generateMovie(Integer.valueOf(i));
            Solution.createMovie(movie);
            movies.add(movie);
        }

        return movies;
    }

    Set<Viewer> generateViewers() {
        Set<Viewer> viewers = new HashSet();

        for(int i = 1; i <= 20; ++i) {
            Viewer viewer = TestUtils.generateViewer(Integer.valueOf(i));
            Solution.createViewer(viewer);
            viewers.add(viewer);
        }

        return viewers;
    }

    Map<Integer, Set<Integer>> generateViews(Set<Viewer> viewers, Set<Movie> movies) {
        Map<Integer, Set<Integer>> views = new HashMap();
        Iterator var4 = viewers.iterator();

        while(var4.hasNext()) {
            Viewer viewer = (Viewer)var4.next();
            views.put(Integer.valueOf(viewer.getId()), new HashSet());
            Iterator var6 = movies.iterator();

            while(var6.hasNext()) {
                Movie movie = (Movie)var6.next();
                if(Math.random() <= 0.8D) {
                    ((Set)views.get(Integer.valueOf(viewer.getId()))).add(Integer.valueOf(movie.getId()));
                    Solution.addView(Integer.valueOf(viewer.getId()), Integer.valueOf(movie.getId()));
                }
            }
        }

        return views;
    }

    private ArrayList<Integer> getSimilarUsers(Map<Integer, Set<Integer>> views, Integer viewerId) {
        ArrayList<Integer> similarUsers = new ArrayList();
        Set<Integer> currentUserViews = (Set)views.get(viewerId);
        Iterator var5 = views.keySet().iterator();

        while(var5.hasNext()) {
            Integer otherViewer = (Integer)var5.next();
            if(!otherViewer.equals(viewerId)) {
                Set<Integer> otherViewerViews = new HashSet((Collection)views.get(otherViewer));
                otherViewerViews.retainAll(currentUserViews);
                if((double)otherViewerViews.size() >= 0.75D * (double)currentUserViews.size()) {
                    similarUsers.add(otherViewer);
                }
            }
        }

        similarUsers.sort(Comparator.naturalOrder());
        return similarUsers;
    }

    private Map<Integer, Map<MovieRating, Set<Integer>>> generateRatings(Map<Integer, Set<Integer>> views) {
        Map<Integer, Map<MovieRating, Set<Integer>>> ratings = new HashMap();
        Iterator var3 = views.keySet().iterator();

        while(var3.hasNext()) {
            Integer viewerId = (Integer)var3.next();
            Set<Integer> movieIds = (Set)views.get(viewerId);
            Iterator var6 = movieIds.iterator();

            while(var6.hasNext()) {
                Integer movieId = (Integer)var6.next();
                if(!ratings.containsKey(movieId)) {
                    Map<MovieRating, Set<Integer>> ratingsMap = new HashMap();
                    ratingsMap.put(MovieRating.LIKE, new HashSet());
                    ratingsMap.put(MovieRating.DISLIKE, new HashSet());
                    ratings.put(movieId, ratingsMap);
                }

                double rand = Math.random();
                if(rand >= 0.5D) {
                    if(rand < 0.75D) {
                        ((Set)((Map)ratings.get(movieId)).get(MovieRating.LIKE)).add(viewerId);
                        Solution.addMovieRating(viewerId, movieId, MovieRating.LIKE);
                    } else {
                        ((Set)((Map)ratings.get(movieId)).get(MovieRating.DISLIKE)).add(viewerId);
                        Solution.addMovieRating(viewerId, movieId, MovieRating.DISLIKE);
                    }
                }
            }
        }

        return ratings;
    }

    private Map<Integer, Set<Integer>> createViewerRatings(Map<Integer, Map<MovieRating, Set<Integer>>> ratings) {
        Map<Integer, Set<Integer>> viewerRatings = new HashMap();
        Iterator var3 = ratings.keySet().iterator();

        while(var3.hasNext()) {
            Integer movieId = (Integer)var3.next();
            Map<MovieRating, Set<Integer>> ratingsMap = (Map)ratings.get(movieId);
            Iterator var6 = ratingsMap.keySet().iterator();

            while(var6.hasNext()) {
                MovieRating rating = (MovieRating)var6.next();
                Set<Integer> vieweIds = (Set)ratingsMap.get(rating);

                Integer viewerId;
                for(Iterator var9 = vieweIds.iterator(); var9.hasNext(); ((Set)viewerRatings.get(viewerId)).add(movieId)) {
                    viewerId = (Integer)var9.next();
                    if(!viewerRatings.containsKey(viewerId)) {
                        viewerRatings.put(viewerId, new HashSet());
                    }
                }
            }
        }

        return viewerRatings;
    }
}
