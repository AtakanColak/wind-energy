import java.io.*;

/**
 * Created by atakan on 24/02/18.
 */
public class ArrayIO {
    public static double[][] read(int year, int month) throws IOException,ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename(year,month)));
        return (double[][]) inputStream.readObject();
    }

    public static void write(int year, int month) throws  IOException{
        File file = new File(filename(year,month));
        if (file.exists()) {
            System.out.println(file.getName() + " already exists.");
        }
        else {
            double[][] averages = JSONIO.wind_speed_averages(year,month);
            file.createNewFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(averages);
            outputStream.close();
            System.out.println(file.getName() + " saved.");
        }
    }

    private static String filename(int year, int month) {
        return "files/arrays/" + year + "_"+ month + "_.arraystream";
    }
}
