package controller;
import java.sql.*;
import java.util.*;

import oracle.jdbc.pool.OracleDataSource;

public class connect {
   public static List<Map<String,Object>> con(){
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
          List<String> list =new ArrayList<String>();
          for(int i=0;i<count;i++) {
              name[i] = rsmd.getColumnName(i + 1);
              list.add(name[i]);
              System.out.print(name[i]);

          }
        List<Map<String,Object>> datelist=new ArrayList<Map<String,Object>>();
          while (rs.next()){
              Map<String,Object> map=new HashMap<String, Object>();
              for (int i=0;i<list.size();i++){

                  Object object = rs.getObject(list.get(i));
                  map.put(list.get(i),object);
                  System.out.print(object);
              }
             datelist.add(map);


          }
          System.out.print(datelist);
          return datelist;



    }
        catch(
    Exception e)

    {

        System.out.println(e.getMessage());
    }
    return null;
   }
}
