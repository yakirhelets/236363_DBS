//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

//import org.json.JSONObject;
import techflix.business.Movie;
import techflix.business.Viewer;

public class TestUtils {
//    static JSONObject grades = new JSONObject();

    public TestUtils() {
    }

//    public static void updateGrade(String testName, int grade) {
//        if(!grades.keySet().contains("tests grades")) {
//            grades.put("tests grades", new JSONObject());
//        }
//
//        JSONObject testGrades = grades.getJSONObject("tests grades");
//        testGrades.put(testName, grade);
//    }

    public static Viewer generateViewer(Integer id) {
        Viewer viewer = new Viewer();
        viewer.setId(id.intValue());
        viewer.setName("viewer" + id);
        return viewer;
    }

    public static Movie generateMovie(Integer id) {
        Movie movie = new Movie();
        movie.setId(id.intValue());
        movie.setName("movie" + id);
        movie.setDescription("description" + id);
        return movie;
    }
}
