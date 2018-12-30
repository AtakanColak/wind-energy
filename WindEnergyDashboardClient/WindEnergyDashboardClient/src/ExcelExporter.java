import Data.*;
import Network.OperateUser;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import Data.*;
import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class ExcelExporter extends JFrame {
    public static String lloyds = "files/Lloyd'sRegister.xlsm";
    private static String[] quarter1 = { "January", "February", "March"};
    private static String[] quarter2 = { "April", "May", "June"};
    private static String[] quarter3 = { "July", "August", "September"};
    private static String[] quarter4 = { "October", "November", "December"};
    private static String[] months = {"January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"};

    public void export(List<WindData> wind,String filePath, int start, int end) {

        List<WindData> winds = new ArrayList<WindData>();
        int difference = end -start;

        winds = averages(wind,start,end);
        lloydsFormat(winds,filePath,start, end);
    }
    private List<WindData> averages(List<WindData> Data, int start, int end) {
        List<WindData> data12 = new ArrayList<>();

        int difference = end -start;
        int year = start;
        int i = 0,l;
        double average = 0;
        int wyear = 0,wmonth =0;
        double wlat =0,wlon = 0;
        List<ArrayList<WindData>> datas = new ArrayList<ArrayList<WindData>>();
        while (i <= difference) {
            l = 0;
            average = 0;
            for (int p = 0; p < Data.size(); p++) {
                    if (Data.get(p).year == year) {
                        average = average + Data.get(p).windspeed;
                        if (i == 0) {
                            Data.get(p).longitude = wlon;
                            Data.get(p).latitude = wlat;
                        }
                        l = l+1;
                    }

            }
            average = average/l;
            data12.add(new WindData(year,0,wlat,wlon,average));
            year++;
            i++;
        }
        return data12;
    }

    public void chooseLocation(String s) {
        try {
                File filed = new File(s);
                File lloyd = new File(lloyds);
                Files.copy(lloyd.toPath(),filed.toPath());
            } catch (IOException e1) {
	        e1.printStackTrace();
        }
    }

	public String returnLocation() {
		String files = "";
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String file = fileChooser.getSelectedFile().getPath();
			if (!file.contains(".xlsm")) {
                files = file + ".xlsm";
            } else {
			    files = file;
            }
            File filed = new File(files);
            if (filed.exists()) {
                return "Please Select a new File as the selected one exists";
            }
		}
		return files;
	}

    public static double rounds(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void lloydsFormat(List<WindData> wind,String filePath,int start, int end) {
        OperateUser operators = new OperateUser();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            String date;
            //This sets the row and column start
            int columnCells = 3;
            int rowCells = 11;
            Row row9 = sheet.createRow(9);
            Cell cell92 = row9.createCell(2);
            cell92.setCellValue("Number of Data points");
            Cell cell94 = row9.createCell(4);
            cell94.setCellValue(wind.size());
            //This populates the excel file
            int k = 0,t=0;
            double averageWindspeed = -1;
            double windspeedend = 0;
            double meand = 0;
            for (int p = 0; p < wind.size(); p++) {
                k++;
                if (k == wind.size()) {
                    averageWindspeed = wind.get(p).windspeed / meand * 100;
                    averageWindspeed = rounds(averageWindspeed, 2);
                    sheet.createRow(3).createCell(3).setCellValue(averageWindspeed);
                    sheet.createRow(6).createCell(1).setCellValue(averageWindspeed + "%");
                    double lat = wind.get(p).latitude;
                    double lon = wind.get(p).longitude;
                    if (lon < 0 ) lon += 360;
                    String countries = FindCountry.findCountry(lat,lon);
                    sheet.createRow(0).createCell(1).setCellValue("Windspeeds For " + countries);
                    sheet.createRow(1).createCell(1).setCellValue("Windspeeds For " + countries + " Over The Last Year");
                    if (averageWindspeed < 100) {
                        sheet.createRow(2).createCell(1).setCellValue("For The Past Year " + countries + " Has Experience Below Average WindSpeed");
                    } else {
                        sheet.createRow(2).createCell(1).setCellValue("For The Past Year " + countries + " Has Experience Above Average WindSpeed");
                    }
                }
                if (wind.get(p).year >= start && wind.get(p).year <= end ) {
                    if (t == 0) {
                        double lat = wind.get(p).latitude;
                        double lon = wind.get(p).longitude;
                        if (lon < 0) lon += 360;
                        //List<WindData> mean = operators.SelectData(lat,lon);

                        for (WindData means :wind) {
                            meand = means.windspeed + meand;
                        }
                        meand = meand/wind.size();
                    }
                    t++;
                    if (wind.get(p).year == end) {
                        windspeedend+=wind.get(p).windspeed;
                    }
                    date = Integer.toString(wind.get(p).year) + "-" + Integer.toString(wind.get(p).month);
                    Row row = sheet.createRow(rowCells);
                    for (int i = 2; i < 4; i++) {
                        Cell cell = row.createCell(i);
                        if (i == 2) {
                            cell.setCellValue(date);
                        } else {
                            cell.setCellValue(wind.get(p).windspeed - meand);
                        }
                    }
                    rowCells++;
                }
            }
            HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            FileOutputStream out = new FileOutputStream(filePath);
            wb.write(out);
            out.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
