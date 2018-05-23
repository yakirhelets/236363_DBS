package techflix;

import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.data.DBConnector;
import techflix.data.PostgresSQLErrorCodes;

import java.sql.*;
import java.util.ArrayList;
import static techflix.business.ReturnValue.*;

public class Solution {

    public static void createTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("CREATE TABLE Viewer\n" +
                    "(\n" +
                    "    id integer,\n" +
                    "    name text NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        try {

            pstmt = connection.prepareStatement("CREATE TABLE Movie\n" +
                    "(\n" +
                    "    id integer,\n" +
                    "    name text NOT NULL,\n" +
                    "    description text NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        try {

            pstmt = connection.prepareStatement("CREATE TYPE RatingType AS ENUM('LIKE','DISLIKE')\n");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        try {

            pstmt = connection.prepareStatement("CREATE TABLE ViewedBy\n" +
                    "(\n" +
                    "    viewerId integer,\n" +
                    "    movieId integer,\n" +
                    "    rating RatingType ,\n" +
                    "    FOREIGN KEY (viewerId) REFERENCES Viewer(id),\n" +
                    "    FOREIGN KEY (movieId) REFERENCES Movie(id),\n" +
                    "    PRIMARY KEY (viewerId,movieId)\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static void clearTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("DELETE FROM ViewedBy");
            pstmt.execute();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        try {

            pstmt = connection.prepareStatement("DELETE FROM Movie");
            pstmt.execute();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        try {

            pstmt = connection.prepareStatement("DELETE FROM Viewer");
            pstmt.execute();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static void dropTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS ViewedBy");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        try {

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Movie");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        try {

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Viewer");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static ReturnValue createViewer(Viewer viewer)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Viewer " +
                    "VALUES (?, ?)");
            pstmt.setInt(1, viewer.getId());
            pstmt.setString(2, viewer.getName());
            pstmt.execute();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ALREADY_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue deleteViewer(Viewer viewer)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Viewer WHERE id = ?");
            pstmt.setInt(1, viewer.getId());
            ResultSet results = pstmt.executeQuery();
            results.next();
            int rowsToDelete = results.getInt("COUNT");
            results.close();

            if (rowsToDelete == 0) {
                return NOT_EXISTS;
            } else {
                pstmt = connection.prepareStatement("DELETE FROM ViewedBy " +
                        "WHERE viewerId = ?");
                pstmt.setInt(1, viewer.getId());
                pstmt.execute();

                pstmt = connection.prepareStatement("DELETE FROM Viewer " +
                        "WHERE id = ? AND name = ? ");
                pstmt.setInt(1, viewer.getId());
                pstmt.setString(2, viewer.getName());
                pstmt.execute();
            }
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue updateViewer(Viewer viewer)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Viewer WHERE id = ?");
            pstmt.setInt(1, viewer.getId());
            ResultSet results = pstmt.executeQuery();
            results.next();
            int rowsToChange = results.getInt("COUNT");
            results.close();

            if (rowsToChange == 0) return NOT_EXISTS;

            pstmt = connection.prepareStatement("UPDATE Viewer " +
                    "SET name = ? " +
                    "WHERE id = ?");
            pstmt.setString(1, viewer.getName());
            pstmt.setInt(2, viewer.getId());
            pstmt.execute();

        } catch (SQLException e) {

            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return BAD_PARAMS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Viewer getViewer(Integer viewerId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM Viewer WHERE id = ?");
            pstmt.setInt(1, viewerId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            Viewer resultViewer = new Viewer();
            resultViewer.setId(results.getInt(1));
            resultViewer.setName(results.getString(2));

            results.close();
            return resultViewer;

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return Viewer.badViewer();
            }
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return Viewer.badViewer();
    }


    public static ReturnValue createMovie(Movie movie)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Movie " +
                    "VALUES (?, ?, ?)");
            pstmt.setInt(1, movie.getId());
            pstmt.setString(2, movie.getName());
            pstmt.setString(3, movie.getDescription());
            pstmt.execute();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ALREADY_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue deleteMovie(Movie movie)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Movie WHERE id = ?");
            pstmt.setInt(1, movie.getId());
            ResultSet results = pstmt.executeQuery();
            results.next();
            int rowsToDelete = results.getInt("COUNT");
            results.close();

            if (rowsToDelete == 0) {
                return NOT_EXISTS;
            } else {
                pstmt = connection.prepareStatement("DELETE FROM ViewedBy " +
                        "WHERE movieId = ?");
                pstmt.setInt(1, movie.getId());
                pstmt.execute();

                pstmt = connection.prepareStatement("DELETE FROM Movie " +
                        "WHERE id = ? AND name = ? AND description = ?");
                pstmt.setInt(1, movie.getId());
                pstmt.setString(2, movie.getName());
                pstmt.setString(3, movie.getDescription());
                pstmt.execute();
            }
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue updateMovie(Movie movie)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Movie WHERE id = ?");
            pstmt.setInt(1, movie.getId());
            ResultSet results = pstmt.executeQuery();
            results.next();
            int rowsToChange = results.getInt("COUNT");
            results.close();

            if (rowsToChange == 0) return NOT_EXISTS;

            pstmt = connection.prepareStatement("UPDATE Movie " +
                    "SET description = ? " +
                    "WHERE id = ?");
            pstmt.setString(1, movie.getDescription());
            pstmt.setInt(2, movie.getId());
            pstmt.execute();

        } catch (SQLException e) {

            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ALREADY_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue() ||
                    Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue() ) {
                return BAD_PARAMS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Movie getMovie(Integer movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM Movie WHERE id = ?");
            pstmt.setInt(1, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            Movie resultMovie = new Movie();
            resultMovie.setId(results.getInt(1));
            resultMovie.setName(results.getString(2));
            resultMovie.setDescription(results.getString(3));

            results.close();
            return resultMovie;

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return Movie.badMovie();
            }
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return Movie.badMovie();
    }



    public static ReturnValue addView(Integer viewerId, Integer movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO ViewedBy " +
                    "VALUES (?, ?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            pstmt.execute();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ALREADY_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue removeView(Integer viewerId, Integer movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy " +
            "WHERE viewerId = ? AND movieId = ?");

            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();
            int rowsToDelete = results.getInt("COUNT");
            results.close();

            if (rowsToDelete == 0) return NOT_EXISTS;

            pstmt = connection.prepareStatement("DELETE FROM ViewedBy " +
                    "WHERE viewerId = ? AND movieId = ?");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            pstmt.execute();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Integer getMovieViewCount(Integer movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy WHERE movieId = ?");
            pstmt.setInt(1, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();
            int result = results.getInt("COUNT");
            results.close();
            return result;

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()
        || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return 0;
            }
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return 0;
    }


    public static ReturnValue addMovieRating(Integer viewerId, Integer movieId, MovieRating rating)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy "+
                    " WHERE viewerId = ? AND movieId = ?");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            if (results.getInt("COUNT") == 1) {
                pstmt = connection.prepareStatement("UPDATE ViewedBy " +
                        "SET rating = CAST(? AS RatingType) " +
                        "WHERE viewerId = ? AND movieId = ?");
                pstmt.setString(1, rating.toString());
                pstmt.setInt(2, viewerId);
                pstmt.setInt(3, movieId);
                pstmt.execute();
            } else {
                return NOT_EXISTS;
            }
            results.close();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue removeMovieRating(Integer viewerId, Integer movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy "+
                    " WHERE viewerId = ? AND movieId = ?");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            if (results.getInt("COUNT") == 1) {
                pstmt = connection.prepareStatement("SELECT rating FROM ViewedBy "+
                        " WHERE viewerId = ? AND movieId = ?");
                pstmt.setInt(1, viewerId);
                pstmt.setInt(2, movieId);
                ResultSet resultRating = pstmt.executeQuery();
                resultRating.next();
                if (resultRating.getObject("rating") == null) {
                    return NOT_EXISTS;
                } else {
                    pstmt = connection.prepareStatement("UPDATE ViewedBy " +
                            "SET rating = CAST(? AS RatingType) " +
                            "WHERE viewerId = ? AND movieId = ?");
                    pstmt.setObject(1, null);
                    pstmt.setInt(2, viewerId);
                    pstmt.setInt(3, movieId);
                    pstmt.execute();
                }
            } else {
                return NOT_EXISTS;
            }
            results.close();

        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()
                    || Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
                return NOT_EXISTS;
            }
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static int getMovieLikesCount(int movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy "+
                    " WHERE movieId = ? AND rating = 'LIKE' ");
            pstmt.setInt(1, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            int result = results.getInt("COUNT");
            results.close();
            return result;

        } catch (SQLException e) {
            return 0;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static int getMovieDislikesCount(int movieId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM ViewedBy "+
                    " WHERE movieId = ? AND rating = 'DISLIKE' ");
            pstmt.setInt(1, movieId);
            ResultSet results = pstmt.executeQuery();
            results.next();

            int result = results.getInt("COUNT");
            results.close();
            return result;

        } catch (SQLException e) {
            return 0;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ArrayList<Integer> getSimilarViewers(Integer viewerId)
    {

        // TODO: check if need to set null to a different value
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement( "SELECT t1.viewerId FROM\n" +
                    "(SELECT COUNT(movieId), viewerId FROM viewedBy\n" +
                    "WHERE movieId IN (SELECT movieId FROM viewedBy WHERE viewerId = ?) AND viewerId <> ?\n" +
                    "GROUP BY viewerId\n" +
                    "HAVING COUNT(movieId)>=0.75*(SELECT COUNT(movieID) FROM viewedBy WHERE viewerId = ?)\n" +
                    ") AS t1 \n" +
                    "ORDER BY t1.viewerId ASC");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, viewerId);
            pstmt.setInt(3, viewerId);
            pstmt.execute();

            ResultSet queryResults = pstmt.executeQuery();
            ArrayList<Integer> results = new ArrayList<>();
            while (queryResults.next()) {
                results.add(queryResults.getInt("viewerId"));
            }
            queryResults.close();
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return new ArrayList<>();    }


    public static ArrayList<Integer> mostInfluencingViewers()
    {
        // TODO: check if need to set null to a different value
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT viewerId FROM viewedBy " +
                    "GROUP BY viewerId ORDER BY COUNT(viewerId) DESC, COUNT(rating) DESC, viewerId ASC LIMIT 10");
            ResultSet queryResults = pstmt.executeQuery();
            ArrayList<Integer> results = new ArrayList<>();
            while (queryResults.next()) {
                results.add(queryResults.getInt("viewerId"));
            }
            queryResults.close();
            return results;

        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return new ArrayList<>();
    }


    public static ArrayList<Integer> getMoviesRecommendations(Integer viewerId)
    {
        ArrayList<Integer> similarViewers = getSimilarViewers(viewerId);
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("CREATE TABLE similarViewers (viewerId integer)");
            pstmt.execute();
            for (int i : similarViewers) {
                pstmt = connection.prepareStatement("INSERT INTO similarViewers VALUES (?)");
                pstmt.setInt(1, i);
                pstmt.execute();
            }
            pstmt = connection.prepareStatement("SELECT movieId, SUM(likesCount) AS likesCountSum, SUM(filter) AS rank FROM\n" +
                    "((SELECT movieId, COUNT(rating) AS likesCount, 1 AS filter FROM viewedBy WHERE ((viewerId IN (SELECT * from similarViewers)) AND (movieId NOT IN (SELECT movieId FROM viewedBy WHERE viewerId = ?)) AND (rating = 'LIKE'))\n" +
                    "GROUP BY movieId ORDER BY COUNT(rating) DESC, movieId ASC LIMIT 10)\n" +
                    "UNION ALL\n" +
                    "(SELECT movieId, 0 AS likesCount, 0 AS filter FROM viewedBy WHERE ((viewerId IN (SELECT * from similarViewers)) AND (movieId NOT IN (SELECT movieId FROM viewedBy WHERE viewerId = ?)))\n" +
                    "GROUP BY movieId ORDER BY movieId ASC)) t1\n" +
                    "GROUP BY movieId ORDER BY rank DESC, likesCountSum DESC, movieId ASC LIMIT 10");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, viewerId);
            ResultSet queryResults = pstmt.executeQuery();

            ArrayList<Integer> results = new ArrayList<>();
            while (queryResults.next()) {
                results.add(queryResults.getInt("movieId"));
            }
            queryResults.close();
            return results;

        } catch (SQLException e) {
//            e.printStackTrace()();
        }
        finally {
            try {
                pstmt = connection.prepareStatement("DROP TABLE similarViewers");
                pstmt.execute();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return new ArrayList<>();
    }


    public static ArrayList<Integer> getConditionalRecommendations(Integer viewerId, int movieId)
    {
        ArrayList<Integer> similarViewers = getSimilarViewers(viewerId);
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("CREATE TABLE similarRankers (viewerId integer)");
            pstmt.execute();

            pstmt = connection.prepareStatement("SELECT rating FROM viewedBy WHERE viewerId = ? AND movieId = ?");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet res3 = pstmt.executeQuery();

            if (res3.next()) {
                if (res3.getObject("rating") == null) {
                    res3.close();
                    return new ArrayList<>();
                }

                for (int i : similarViewers) {

                    pstmt = connection.prepareStatement("SELECT COUNT(*) FROM viewedBy WHERE viewerId = ? AND movieId = ?");
                    pstmt.setInt(1, i);
                    pstmt.setInt(2, movieId);
                    ResultSet tmpRes = pstmt.executeQuery();
                    tmpRes.next();
                    if (tmpRes.getInt("COUNT") == 1) {
                        pstmt = connection.prepareStatement("SELECT rating FROM viewedBy WHERE viewerId = ? AND movieId = ?");
                        pstmt.setInt(1, i);
                        pstmt.setInt(2, movieId);
                        ResultSet res4 = pstmt.executeQuery();
                        res4.next();

                        if (res4.getObject("rating") != null && res3.getObject("rating").equals(res4.getObject("rating"))) {
                            pstmt = connection.prepareStatement("INSERT INTO similarRankers VALUES (?)");
                            pstmt.setInt(1, i);
                            pstmt.execute();
                        }
                        res4.close();
                    }
                }
            }
            res3.close();

            pstmt = connection.prepareStatement("SELECT movieId, SUM(likesCount) AS likesCountSum, SUM(filter) AS rank FROM\n" +
                    "((SELECT movieId, COUNT(rating) AS likesCount, 1 AS filter FROM viewedBy WHERE ((viewerId IN (SELECT * from similarRankers)) AND (movieId NOT IN (SELECT movieId FROM viewedBy WHERE viewerId = ?)) AND (rating = 'LIKE'))\n" +
                    "GROUP BY movieId ORDER BY COUNT(rating) DESC, movieId ASC LIMIT 10)\n" +
                    "UNION ALL\n" +
                    "(SELECT movieId, 0 AS likesCount, 0 AS filter FROM viewedBy WHERE ((viewerId IN (SELECT * from similarRankers)) AND (movieId NOT IN (SELECT movieId FROM viewedBy WHERE viewerId = ?)))\n" +
                    "GROUP BY movieId ORDER BY movieId ASC)) t1\n" +
                    "GROUP BY movieId ORDER BY rank DESC, likesCountSum DESC, movieId ASC LIMIT 10");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, viewerId);
            ResultSet queryResults = pstmt.executeQuery();

            ArrayList<Integer> results = new ArrayList<>();
            while (queryResults.next()) {
                results.add(queryResults.getInt("movieId"));
            }
            queryResults.close();
            return results;

        } catch (SQLException e) {
//            e.printStackTrace()();
        }
        finally {
            try {
                pstmt = connection.prepareStatement("DROP TABLE similarRankers");
                pstmt.execute();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return new ArrayList<>();
    }

}


