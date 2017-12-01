package controller;

/**
 * Created by Administrator on 2017/11/30 0030.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.omg.CORBA.Request;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ComponentScan
@RestController
@EnableAutoConfiguration
public class WebRounter {
    OrcleDBConect orcleDBConect=new OrcleDBConect();
    Gson gson = new GsonBuilder().create();

    @RequestMapping("/")
    String home(@RequestParam(required = false,defaultValue = "") String sql , @RequestParam(required = false,defaultValue = "{\"fmt\":\"null\",\"username\":\"system\",\"password\":\"123456\",\"host\":\"127.0.0.1\",\"name\":\"orcl\"}") String db ) {
        Map<String, String> map = gson.fromJson(db, HashMap.class);
        if (sql.isEmpty()) {
            return "请填写sql语句";
        }
        if (sql.contains("SElECT") || sql.contains("select")) {
            String returndata = Select(sql, map);
            return returndata;
        } else {
            String ok = Updata(sql, map);
            return ok;
        }

    }





public String Select(String sql,Map map){
    try {
        List<Map<String, Object>> returndata = orcleDBConect.Select(sql, map);
        return gson.toJson(returndata);
    } catch (Exception e) {

        e.printStackTrace();
        return e.toString();
    } finally {

        if (orcleDBConect.con != null) {
            try {
                orcleDBConect.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (orcleDBConect.rs != null) {
            try {
                orcleDBConect.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (orcleDBConect.ps != null) {
            try {
                orcleDBConect.ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}


public String Updata(String sql,Map map){

    try {
        int update = orcleDBConect.Update(sql, map);
        return gson.toJson(update);
    } catch (Exception e) {
        e.printStackTrace();
        return e.toString();
    } finally {

        if (orcleDBConect.con != null) {
            try {
                orcleDBConect.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (orcleDBConect.rs != null) {
            try {
                orcleDBConect.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (orcleDBConect.ps != null) {
            try {
                orcleDBConect.ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}




}