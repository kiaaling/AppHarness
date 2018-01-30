package com.uhuiapp.harness.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zhaoxiong on 2018/1/30.
 */
public class DatabaseCheckUtils {

    public boolean checkUserName(String expectName){
        Connection conn ;
        String sql = "select * from user where name="+"\'"+expectName+"\'";
        try {
            conn= DatabaseConnectionFactory.getDatabaseConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs =stmt.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString("name");
                if (name.equalsIgnoreCase(expectName)){
                    rs.close();
                    stmt.close();
                    conn.close();
                    return true;
                }
            }

        }catch (Exception e){

        }
        return false;
    }
}
