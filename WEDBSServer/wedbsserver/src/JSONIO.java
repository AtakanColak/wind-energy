import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Created by atakan on 24/02/18.
 */
public class JSONIO {

    public static double[][] wind_speed_averages(int year, int month) throws IOException {
        double[][] averages   = new double[361][720];
        DateTime date = create_date(year, month);
        int max_day = date.dayOfMonth().getMaximumValue();
        File subdirectory = new File("files/" +Filenames.json_subdirectoryname(date));
        subdirectory.mkdirs();
        for (int i = 0; i < max_day; i++) {
            for (int j = 0; j < 4; j++) {
                download_json(date);
                if(i == 0 && j == 0) averages = read_json(date);
                else average_two(averages, read_json(date));
                print(Filenames.ncef_datename(date).replace(':','_') + " read and averaged.");
                delete_json(date);
                date = date.plusHours(6);
            }
        }
        return averages;
    }

    private static double[][] read_json(DateTime date) throws FileNotFoundException {
        String filename     = Filenames.json_filename(date);
        FileReader reader   = new FileReader(filename);
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject obj      = new JSONObject(tokener);
        JSONArray array     = obj.getJSONObject("table").getJSONArray("rows");
        double[][] values   = new double[361][720];
        for (int i = 0; i < array.length(); i++) {
            JSONArray line  = array.getJSONArray(i);
            int north       = (int) ((line.getDouble(1) + 90) * 2);
            int east        = (int) (line.getDouble(2) * 2);
            values[north][east] = pythagorean(line.getDouble(3),line.getDouble(4));
        }
        return values;
    }

    private static void download_json(DateTime date) throws IOException {
        File file = new File(Filenames.json_filename(date));
        print(Filenames.json_filename(date));
        if (file.exists()) file.delete();
        file.createNewFile();
        print(file.getName() + " created.");
        URL website = new URL(Filenames.ncef_urlname(date));
        FileUtils.copyURLToFile(website,file);
    }

    private static void delete_json(DateTime date) throws  IOException{
        File file = new File(Filenames.json_filename(date));
        if (!file.exists())
            print(file.getName() + " doesn't exist.");
        else if (file.delete())
            print(file.getName() + " is deleted.");
        else
            print(file.getName() + " isn't deleted.");
    }

    private static void print(String s) {
        System.out.println(s);
    }

    public static DateTime create_date(int year, int month) {
        return new DateTime(year,month,1,0,0,0);
    }

    private static void average_two(double[][] averages, double[][] timeframe) {
        for (int n = 0; n < 361; n++) {
            for(int e = 0; e < 720; e++) {
                averages[n][e] = (averages[n][e] + timeframe[n][e]) / 2;
            }
        }
    }

    private static double pythagorean(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }
}
