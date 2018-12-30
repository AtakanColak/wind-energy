import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class OperateOracle {


    private static String USERNAMR = "sys as sysdba";
    private static String PASSWORD = "cb1989CB1989#";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@129.150.193.122:1521:ORCL";


    public static void AddData(int year2, int month2, double latitude2, double longitude2, double speed2, Connection connection, PreparedStatement pstm, ResultSet rs) {
                
    	try {
            pstm.setInt(1, year2);
            pstm.setInt(2, month2);
            pstm.setDouble(3, latitude2);
            pstm.setDouble(4, longitude2);
            pstm.setDouble(5, speed2);
            pstm.addBatch();
        } catch (SQLException e) {
            e.printStackTrace();}

       // System.out.println("uploaded");
    }
    public static boolean countRows(int year2, int month2) {
    	boolean check = false;
    	//ture -> values all in this month
    	//false -> missing some values
    	Connection connection = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	connection = getConnection(connection);
    	String sql = "select count(SPEED) from WINDSAM where YEAR="+year2+" and MONTH="+month2;
    	int i = 0;
    	try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            if(rs.next()) {
            	i=rs.getInt(1);
            	}
            if(i==361*720)
            	check=true;
            connection.close();
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    	return check;
    }
    public static void deleteAll() {
    	Connection connection = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	connection = getConnection(connection);
    	String sql = "delete from WINDSAM where 1=1";
    	try {
            pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource(connection,pstm,rs);
        }
    }
    public static void executeChange(PreparedStatement pstm) {
    	try {pstm.executeBatch();}
    	catch (SQLException e) {
            e.printStackTrace();}
    }
    
    public static boolean checkExist(int year2, int month2) {
    	Connection connection = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	boolean check = true;
    	//true -> doesn't exist in database  
    	//false -> exist
    	connection = getConnection(connection);
    	String sql = "select * from WINDSAM where YEAR="+year2+" and MONTH="+month2;
    	try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
            	check = false;
                System.out.println(year2 + "\t" + month2 + " already exist in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource(connection,pstm,rs);
        }
    	return check;
    }
    public static void deleteMonth(int year2, int month2) {
    	Connection connection = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	connection = getConnection(connection);
    	String sql = "delete from WINDSAM where YEAR="+year2+" and MONTH="+month2;
    	try {
            pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource(connection,pstm,rs);
        }
    }
    
    public static Connection getConnection(Connection connection) {
        try {
            Class.forName(DRVIER);
            connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("CONNECTION SUCCESSFULLY CREATED");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return connection;
    }
    public static PreparedStatement getpstm(Connection connection, String sqlStr, PreparedStatement pstm) {
    	try{
    		pstm = connection.prepareStatement(sqlStr);
    	} catch (SQLException e) {
           // e.printStackTrace();
            }
    	return pstm;
    }
    public static ResultSet getrs(PreparedStatement pstm, ResultSet rs) {
    	try{
    		 rs = pstm.executeQuery();
    	} catch (SQLException e) {
         //e.printStackTrace();
    	}
    	return rs;
    }

    public static void ReleaseResource(Connection connection, PreparedStatement pstm, ResultSet rs) {
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
