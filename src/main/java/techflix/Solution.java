package techflix;

import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.data.DBConnector;
import techflix.data.PostgresSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    "    rating RatingType,\n" +
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
        //YAKIR
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
        //YAKIR
        return null;
    }

    public static ReturnValue deleteViewer(Viewer viewer)
    {
        //YAKIR
        return null;
    }

    public static ReturnValue updateViewer(Viewer viewer)
    {
        //YAKIR
        return null;
    }

    public static Viewer getViewer(Integer viewerId)
    {
        //YAKIR
        return null;
    }


    public static ReturnValue createMovie(Movie movie)
    {
        if (movie.getId() < 1 || movie.getName() == null || movie.getDescription() == null) return BAD_PARAMS;

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) AS res FROM Movie WHERE (id = ?)");
            pstmt.setInt(1, movie.getId());
            ResultSet results = pstmt.executeQuery();
            if (results.getInt("res") > 0) return ALREADY_EXISTS;
            results.close();

        } catch (SQLException e) {
            return ERROR;
        }

        pstmt = null;
        try {

            pstmt = connection.prepareStatement("INSERT INTO Movie" +
                    "VALUES (?, ?, ?)");
            pstmt.setInt(1, movie.getId());
            pstmt.setString(1, movie.getName());
            pstmt.setString(1, movie.getDescription());
            pstmt.execute();

        } catch (SQLException e) {
            return ERROR;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
            }
        }
        return OK;
    }

    public static ReturnValue deleteMovie(Movie movie)
    {
        //GAL
        return null;
    }

    public static ReturnValue updateMovie(Movie movie)
    {
        //GAL
        return null;
    }

    public static Movie getMovie(Integer movieId)
    {
        //GAL
        return null;
    }



    public static ReturnValue addView(Integer viewerId, Integer movieId)
    {
        //YAKIR
        return null;
    }

    public static ReturnValue removeView(Integer viewerId, Integer movieId)
    {
        //YAKIR
        return null;
    }

    public static Integer getMovieViewCount(Integer movieId)
    {
        //GAL
        return null;
    }


    public static ReturnValue addMovieRating(Integer viewerId, Integer movieId, MovieRating rating)
    {
        //GAL
        return null;
    }

    public static ReturnValue removeMovieRating(Integer viewerId, Integer movieId)
    {
        //GAL
        return null;
    }

    public static int getMovieLikesCount(int movieId)
    {
        //GAL
        return -1;
    }

    public static int getMovieDislikesCount(int movieId)
    {
        //GAL
        return -1;
    }

    public static ArrayList<Integer> getSimilarViewers(Integer viewerId)
    {

        return null;
    }


    public static ArrayList<Integer> mostInfluencingViewers()
    {

        return null;
    }


    public static ArrayList<Integer> getMoviesRecommendations(Integer viewerId)
    {

            return null;
    }


    public static ArrayList<Integer> getConditionalRecommendations(Integer viewerId, int movieId)
    {

        return null;
    }

}

