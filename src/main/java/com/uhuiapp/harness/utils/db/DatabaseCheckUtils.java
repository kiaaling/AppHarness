package com.uhuiapp.harness.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zhaoxiong on 2018/1/30.
 */
public class DatabaseCheckUtils {

    public boolean checkUserName(String expectName, String column){
        Connection conn ;
        String sql = "select * from user where name="+"\'"+expectName+"\'";
        try {
            conn= DatabaseConnectionFactory.getDatabaseConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs =stmt.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString(column);
                if (name.equalsIgnoreCase(expectName)){
                    rs.close();
                    stmt.close();
                    conn.close();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e){

        }
        return false;
    }
}
