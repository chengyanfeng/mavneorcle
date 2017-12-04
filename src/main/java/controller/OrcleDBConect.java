package controller;

import java.sql.*;
import java.util.*;

import oracle.jdbc.pool.OracleDataSource;

import static oracle.net.aso.C05.e;

public class OrcleDBConect {
    // 获取数据库连接方法, 返回Connection对象
    public static Connection con = null;
    //数据执行语句
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    //创建数据库连接
    public  Connection con(String sql, Map datamap) throws Exception {

          /* String username = "OUTLN";
           String password = "123";*/
            OracleDataSource ods = new OracleDataSource();
            ods.setDriverType("thin"); // type of driver
            ods.setNetworkProtocol("tcp"); // tcp is the default anyway
            ods.setServerName(datamap.get("host").toString()); // database server name
            ods.setDatabaseName(datamap.get("name").toString()); // Oracle SID
            ods.setPortNumber(1521); // listener port number
            ods.setUser(datamap.get("username").toString()); // username
            ods.setPassword(datamap.get("password").toString()); // password
            con = ods.getConnection();
            return con;
    }

    //查询
    public List<Map<String, Object>> Select(String sql, Map datamap) throws Exception {

        List<Map<String, Object>> datelist = new ArrayList<Map<String, Object>>();

         Connection conn = con(sql, datamap);
        PreparedStatement  ps = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        //每次存100条数据返回一次
        ps.setFetchSize(100);
        ps.setFetchDirection(ResultSet.FETCH_REVERSE);
        rs = ps.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int count = rsmd.getColumnCount();
         String[] name = new String[count];
         List<String> list = new ArrayList<String>();
         for (int i = 0; i < count; i++) {
             name[i] = rsmd.getColumnName(i + 1);
             list.add(name[i]);
             System.out.print(name[i]);
         }

         while (rs.next()) {
             Map<String, Object> map = new HashMap<String, Object>();
             for (int i = 0; i < list.size(); i++) {

                 Object object = rs.getObject(list.get(i));
                 map.put(list.get(i), object);
                 System.out.print(object);
             }
             datelist.add(map);

         }
        return datelist;
     }



    //删除，修改，增加
    public int Update(String sql, Map datamap) throws Exception{
     int i=0;

          Connection conn = con(sql, datamap);
          PreparedStatement ps = conn.prepareStatement(sql);
           i = ps.executeUpdate(sql);

        return i;

    }
}