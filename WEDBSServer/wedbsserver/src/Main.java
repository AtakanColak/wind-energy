import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main {

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        execute();
    }

    private static void execute() throws IOException, ClassNotFoundException {
        File directory = new File("files");
        directory.mkdirs();
        File array_directory = new File("files/arrays");
        array_directory.mkdirs();

        DateTime start_date = JSONIO.create_date(2012,5);
        DateTime end_date = DateTime.now();

        while(true) {
            for (int year = start_date.getYear(); year <= end_date.getYear(); year++) {
                int month = 1;
                if (year == start_date.getYear()) month = start_date.getMonthOfYear();
                for (; month <= 12 ; month++) {
                    if (year == end_date.getYear() && month == end_date.getMonthOfYear()) break;
                    if(OperateOracle.countRows(year,month)) {
                        System.out.println(year + " " + month + " already exists in the database.");
                        continue;
                    }
                    ArrayIO.write(year, month);
                    upload_month(year, month);
                }
            }
            System.out.println("Loop end");
        }
    }

    private static void upload_month(int year, int month) throws IOException, ClassNotFoundException{
        String sqlStr = "insert into WINDSAM values(?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        connection = OperateOracle.getConnection(connection);
        pstm = OperateOracle.getpstm(connection, sqlStr, pstm);
        rs = OperateOracle.getrs(pstm, rs);


            if(OperateOracle.checkExist(year, month)) OperateOracle.deleteMonth(year,month);
            double[][] values = ArrayIO.read(year,month);
            for (int n = 0; n < 361; n++) {
                for(int e = 0; e < 720; e++) {
                    double lat = (((double) n) / 2) - 90;
                    double lon = ((double) e) / 2;
                    OperateOracle.AddData(year,month,lat,lon,values[n][e],connection,pstm,rs);
                }
                OperateOracle.executeChange(pstm);
            }
            OperateOracle.ReleaseResource(connection, pstm, rs);

            System.out.println("Uploaded " + year + " " + month + " values.");

            

    }

//    private static void update(DateTime start_date) throws IOException{
//        System.out.println("HEY");
//        for (int year = start_date.getYear(); year <= DateTime.now().getYear(); year++) {
//            int month = 1;
//            if(year == start_date.getYear()) month = start_date.getMonthOfYear();
//            for(; month < 13; month++) {
//                if (year == DateTime.now().getYear() && month == DateTime.now().getMonthOfYear()) continue;
//                DateTime date = create_date(year,month);
//                update_month(date);
//            }
//        }


//    private static void update_month(DateTime date) throws IOException{
//        File file = new File(Filenames.excel_filename(date));
//        if (file.exists()) {
//            print(file.getName() + " exists. Bypassing updating month.");
//            return;
//        }
//        download_month(date);
//        double[][] averages = average_month(date.getYear(),date.getMonthOfYear());
//        file.createNewFile();
//        write_to_excel(averages,date);
//        delete_json(date);
//    }

//    private static void delete_json(DateTime date) {
//        for (int i = 0; i < date.dayOfMonth().getMaximumValue(); i++) {
//            for (int j = 0; j < 4; j++) {
//                if(i == 0 && j == 0) date = date.plusHours(6);
//                File file = new File(Filenames.json_filename(date));
//                if (file.exists()) {
//                    file.delete();
//                    print(file.getName() + " is deleted.");
//                }
//                else {
//                    print(file.getName() + " isn't deleted as it doesn't exist.");
//                }
//                date = date.plusHours(6);
//            }
//        }
//    }

    private static void write_to_excel(double[][] averages, DateTime date) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("data");

        int row_index = 1;

        Row title_row = sheet.createRow(row_index++);
        Cell cell = title_row.createCell(1);
        cell.setCellValue("Latitude");
        cell = title_row.createCell(2);
        cell.setCellValue("Longitude");
        cell = title_row.createCell(3);
        cell.setCellValue("Average");

        for (int i = 0; i < 361; i++) {
            double latitude = (((double) i) / 2) - 90;
            for(int j = 0; j < 720; j++) {
                double longitude = ((double) j) / 2;
                Row row = sheet.createRow(row_index++);
                cell = row.createCell(1);
                cell.setCellValue(latitude);
                cell = row.createCell(2);
                cell.setCellValue(longitude);
                cell = row.createCell(3);
                cell.setCellValue(averages[i][j]);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(Filenames.excel_filename(date));
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    private static double[][] average_month(int year, int month) throws  FileNotFoundException {
//        DateTime date = create_date(year,month);
//        double[][] averages = read_json(date);
//        for (int i = 0; i < date.dayOfMonth().getMaximumValue(); i++) {
//            for (int j = 0; j < 4; j++) {
//                if(i == 0 && j == 0) date = date.plusHours(6);
//                double[][] timeframe = read_json(date);
//                for (int n = 0; n < 361; n++) {
//                    for(int e = 0; e < 720; e++) {
//                        averages[n][e] = (averages[n][e] + timeframe[n][e]) / 2;
//                    }
//                }
//                print(Filenames.ncef_datename(date));
//                date = date.plusHours(6);
//            }
//        }
//        return averages;
//    }
//
//    private static void download_month(DateTime date)     throws IOException {
//        for (int i = 0; i < date.dayOfMonth().getMaximumValue(); i++) {
//            for (int j = 0; j < 4; j++) {
//                download_timeframe(date);
//                print(Filenames.ncef_datename(date));
//                date = date.plusHours(6);
//            }
//        }
//    }
//
//    private static void download_timeframe(DateTime date)   throws IOException {
//        File file = new File(Filenames.json_filename(date));
//        if (file.exists()) {
//            print(file.getName() + " exists. Bypassing downloading timeframe.");
//            return;
//        }
//        file.createNewFile();
//        URL website = new URL(Filenames.ncef_urlname(date));
//        FileUtils.copyURLToFile(website,file);
//    }
//
//    private static double[][] read_json(DateTime date) throws FileNotFoundException {
//        String filename     = Filenames.json_filename(date);
//        FileReader reader   = new FileReader(filename);
//        JSONTokener tokener = new JSONTokener(reader);
//        JSONObject obj      = new JSONObject(tokener);
//        JSONArray array     = obj.getJSONObject("table").getJSONArray("rows");
//        double[][] values   = new double[361][720];
//        for (int i = 0; i < array.length(); i++) {
//            JSONArray line  = array.getJSONArray(i);
//            int north       = (int) ((line.getDouble(1) + 90) * 2);
//            int east        = (int) (line.getDouble(2) * 2);
//            values[north][east] = pythagorean(line.getDouble(3),line.getDouble(4));
//        }
//        return values;
//    }



}
