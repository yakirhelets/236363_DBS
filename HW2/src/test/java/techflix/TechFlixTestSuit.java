//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.function.ToIntFunction;
//import org.json.JSONObject;
//import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestViewer.class, TestMovie.class, TestBasicApi.class/*, TestAdvancedApi.class*/})
public class TechFlixTestSuit {
    public TechFlixTestSuit() {
    }

//    @AfterClass
//    public static void writeGrades() {
//        JSONObject testGrades = TestUtils.grades.getJSONObject("tests grades");
//        int sum = testGrades.toMap().values().stream().mapToInt((value) -> {
//            return ((Integer)value).intValue();
//        }).sum();
//        TestUtils.grades.put("total wet part grade", sum);
//        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(TestUtils.grades.toString());
//        String prettyJsonString = gson.toJson(je);
//
//        try {
//            FileWriter file = new FileWriter("grades.json");
//            Throwable var7 = null;
//
//            try {
//                file.write(prettyJsonString);
//            } catch (Throwable var17) {
//                var7 = var17;
//                throw var17;
//            } finally {
//                if(file != null) {
//                    if(var7 != null) {
//                        try {
//                            file.close();
//                        } catch (Throwable var16) {
//                            var7.addSuppressed(var16);
//                        }
//                    } else {
//                        file.close();
//                    }
//                }
//
//            }
//        } catch (IOException var19) {
//            var19.printStackTrace();
//        }
//
//    }
}
