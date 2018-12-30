import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.naming.Context;
import javax.xml.stream.Location;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Handler;

public class FindCountry {

    private static String Countries = "files/worldcitiespop.txt";
    private static String CountryCodes = "files/countryCodes.txt";

    public static String findCountry(double lats, double lons) {
        int nearest = 0;
        double totalDiff = 10000;
        String line,lines, place = "", lon = "", lat = "";
        String[] columns,codes;
        try {
            BufferedReader br = new BufferedReader(new FileReader(Countries));

            br.readLine();
            int i = 0;
            while ((line = br.readLine()) != null) {
                columns = line.split(",");
                i++;
                double cdistance1 = Math.abs(Double.parseDouble(columns[5]) - lats);
                double cdistance2 = Math.abs(Double.parseDouble(columns[6]) - lons);
                if ((cdistance1 + cdistance2) < totalDiff) {
                    totalDiff = cdistance1 + cdistance2;
                    place = columns[0];
                    lat = columns[5];
                    lon = columns[6];
                }
            }
            BufferedReader brs = new BufferedReader(new FileReader(CountryCodes));
            brs.readLine();
            place = place.toUpperCase();
            while ((lines = brs.readLine()) != null) {
                codes = lines.split(",");
                if (codes[1].equals(place)) {
                    place = codes[0];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return place;
    }
}
