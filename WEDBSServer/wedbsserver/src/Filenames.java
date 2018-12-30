import org.joda.time.DateTime;

/**
 * Created by atakan on 24/02/18.
 */
public class Filenames {

    public static String excel_filename(DateTime date) {
        StringBuilder sb = new StringBuilder();
        sb.append("files/");
        sb.append(date.getYear());
        sb.append("-");

        int month   = date.getMonthOfYear();
        if (month   < 10)   sb.append("0");
        sb.append(month);
        sb.append(".xlsx");
        return sb.toString();
    }

    public static String json_filename(DateTime date) {
        int year    = date.getYear();
        int month   = date.getMonthOfYear();
        int day     = date.getDayOfMonth();
        int hour    = date.getHourOfDay();

        StringBuilder sb = new StringBuilder();
        sb.append("files/");

        //Subdirectory name
        sb.append(json_subdirectoryname(date));

        sb.append("/");
        //File name
        sb.append(year);
        sb.append("_");

        if (month   < 10)   sb.append("0");
        sb.append(month);

        sb.append("_");

        if (day     < 10)   sb.append("0");
        sb.append(day);

        sb.append("T");

        if (hour    < 10)   sb.append("0");
        sb.append(hour);

        sb.append("_00_00Z");

        sb.append(".json");

        return sb.toString();

    }

    public static String ncef_urlname(DateTime date) {
        String datename = ncef_datename(date);
        StringBuilder sb = new StringBuilder();
        sb.append("http://oos.soest.hawaii.edu/erddap/griddap/NCEP_Global_Best.json?ugrd10m%5B(");
        sb.append(datename);
        sb.append(")%5D%5B(-90.0):(90.0)%5D%5B(0.0):(359.5)%5D,vgrd10m%5B(");
        sb.append(datename);
        sb.append(")%5D%5B(-90.0):(90.0)%5D%5B(0.0):(359.5)%5D&.draw=vectors&.vars=longitude%7Clatitude%7Cugrd10m");
        return sb.toString();
    }

    public static String ncef_datename(DateTime date) {
        StringBuilder sb = new StringBuilder();

        sb.append(date.getYear());
        sb.append("-");

        int month   = date.getMonthOfYear();
        if (month   < 10)   sb.append("0");
        sb.append(month);

        sb.append("-");
        int day     = date.getDayOfMonth();
        if (day     < 10)   sb.append("0");
        sb.append(day);

        sb.append("T");
        int hour    = date.getHourOfDay();
        if (hour    < 10)   sb.append("0");
        sb.append(hour);

        sb.append(":00:00Z");

        return sb.toString();
    }

    public static String json_subdirectoryname(DateTime date) {
        StringBuilder sb = new StringBuilder();
        int year = date.getYear();
        int month   = date.getMonthOfYear();

        sb.append(year);
        sb.append("_");
        if (month   < 10)   sb.append("0");
        sb.append(month);
        return sb.toString();
    }
}
