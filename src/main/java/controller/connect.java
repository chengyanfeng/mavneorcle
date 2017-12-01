package controller;
import java.sql.*;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;

public class connect {
   public static void con(){
      try{
          String username="OUTLN";
          String password="123";
        OracleDataSource ods = new OracleDataSource();
        ods.setDriverType("thin"); // type of driver
        ods.setNetworkProtocol("tcp"); // tcp is the default anyway
        ods.setServerName("127.0.0.1"); // database server name
        ods.setDatabaseName("orcl"); // Oracle SID
        ods.setPortNumber(1521); // listener port number
        ods.setUser(username); // username
        ods.setPassword(password); // password
        Connection conn = ods.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from TABLE1");
        ResultSet rs = ps.executeQuery();
          ResultSetMetaData rsmd = rs.getMetaData();

          int count=rsmd.getColumnCount();

          String[] name=new String[count];

          for(int i=0;i<count;i++) {
              name[i] = rsmd.getColumnName(i + 1);
              System.out.print(name[i]);

          }

          System.in.read();
    }
        catch(
    Exception e)

    {
        System.out.println(e.getMessage());
    }

   }
}
