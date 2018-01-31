package com.uhuiapp.harness.utils.mybatis;

import com.uhuiapp.harness.utils.TextCrypto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhaoxiong on 2018/1/30.
 */
public class MybatisSqlSessionFactory {

    public static SqlSession getSqlSession() throws Exception{
        String mybatisConfig = "mybatis-config.xml";
        String jdbcConfig = "jdbc.properties";

        InputStream inputStream = Resources.getResourceAsStream(mybatisConfig);
        InputStream jdbcStream = Resources.getResourceAsStream(jdbcConfig);
        Properties jdbcProp = new Properties();
        jdbcProp.load(jdbcStream);
        String encryptPassword = jdbcProp.getProperty("jdbc.password");
        String plainPassword = decryptUserPassword(encryptPassword);
        jdbcProp.setProperty("jdbc.password",plainPassword);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "test",jdbcProp);
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

    private static String decryptUserPassword(String encryptPassword) {
        TextCrypto encryptor = new TextCrypto();
        String decryptText = "";
        try {
            decryptText = encryptor.decryptPassword(encryptPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return decryptText;
    }

}
