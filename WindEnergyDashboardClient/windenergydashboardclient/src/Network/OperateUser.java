package Network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Data.WindData;

public class OperateUser {
    private static String USERNAMR = "C##WINDUSER";
    private static String PASSWORD = "qwerty";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@129.150.193.122:1521:ORCL";


    Connection connection = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;


    public List<WindData> SelectData(double latitude1, double longitude1) {
        System.out.println("Request for " + latitude1 + "," + longitude1);
        List<WindData> data = new ArrayList<>();
        connection = getConnection();
        String sql = "select * from SYS.WINDSAM where LATITUDE="+latitude1+" and LONGITUDE="+longitude1+" ORDER BY YEAR ASC, MONTH ASC";
        try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("year");
                int month = rs.getInt("month");
                Double speed = rs.getDouble("speed");
                WindData windData = new WindData(year,month,latitude1,longitude1,speed);
                data.add(windData);
                System.out.println(windData);
                System.out.println(year + "\t" + month + "\t" + speed);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
        return data;

    }

    private Connection getConnection() {
        try {
            Class.forName(DRVIER);
            connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("CONNECTION ESTABLISHED");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return connection;
    }


    private void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
}