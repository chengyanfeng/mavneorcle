package controller;

import java.sql.*;
import java.util.*;

import oracle.jdbc.pool.OracleDataSource;

public class OrcleDBConect {
    // 获取数据库连接方法, 返回Connection对象
    private static Connection con = null;
    //数据执行语句
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //创建数据库连接
    public static Connection con(String sql, Map datamap) {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    //查询
    public List<Map<String, Object>> add(String sql, Map datamap) throws Exception {
        Connection conn = con(sql, datamap);
        PreparedStatement ps = conn.prepareStatement(sql);
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
        List<Map<String, Object>> datelist = new ArrayList<Map<String, Object>>();
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
    public int Update(String sql, Map datamap){
     int i=0;
      try {
          Connection conn = con(sql, datamap);
          PreparedStatement ps = conn.prepareStatement(sql);
           i = ps.executeUpdate(sql);
      }catch (Exception e){
          System.out.print(e.toString());
      }finally {

          if(con!=null){
              try {
                  con.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
          if(rs!=null){
              try {
                  rs.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
          if (ps!=null){
              try {
                  ps.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }
        return i;

    }
}