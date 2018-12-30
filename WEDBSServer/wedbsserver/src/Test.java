import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) {
        /**
         * 增删改查完成,但是有一定局限性
         * 1.增  问题不大
         * 2.删  要给出一个值去删除(可能值不存在-->没有处理机制,值不唯一怎么处理?)
         * 3.改  同删的问题
         * 4.查  问题不大
         */
      //String sql = "select count(*) from WINDSAM where 1 = 1";
    	
      String sqlStr = "insert into WINDSAM values(?,?,?,?,?)";
      // 创建一个数据库连接
      Connection connection = null;
      // 创建预编译语句对象，一般都是用这个而不用Statement
      PreparedStatement pstm = null;
      //PreparedStatement pstm1 = null;
      // 创建一个结果集对象
      ResultSet rs = null;
      //ResultSet rs1 = null;
      
      connection = OperateOracle.getConnection(connection);
      pstm = OperateOracle.getpstm(connection, sqlStr, pstm);
      rs = OperateOracle.getrs(pstm, rs);
      try{
      for(int i = 0; i<1000; i++) {  
      
              pstm.setInt(1, i);
              pstm.setInt(2, i);
              pstm.setDouble(3, i);
              pstm.setDouble(4, i);
              pstm.setDouble(5, 125);
              pstm.addBatch();
              if(i%10==0) OperateOracle.executeChange(pstm);
        
          }
      }catch (SQLException e) {
      	e.printStackTrace();}
      OperateOracle.ReleaseResource(connection, pstm, rs);
        //创建OperateOracle对象
       // OperateOracle oo=new OperateOracle();
        //测试增加数据操作
        //oo.AddData(2011,7,-10.5,15.5,6);
        //测试删除数据操作
        //oo.DeleteData("孙中山");
        //测试更新数据操作
        //oo.UpdateData(2011,6,-10.5,15.5,222);
        //测试查询数据操作
        //oo.SelectData(2011,6,-10.5,15.5);
        
        //测试ResultSetMetaData类
        //oo.SelectData2();
        System.out.println("草泥马");
    }
    

}