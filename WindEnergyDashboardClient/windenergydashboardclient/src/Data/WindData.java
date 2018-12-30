package Data;

/**
 * Created by atakan on 27/02/18.
 */
public class WindData {
    public WindData(int y, int m, double lat, double lon, double speed) {
        year = y;
        month = month;
        latitude  = lat;
        longitude = lon;
        windspeed = speed;
    }
    public int year;
    public int month;
    public double latitude;
    public double longitude;
    public double windspeed;
}
