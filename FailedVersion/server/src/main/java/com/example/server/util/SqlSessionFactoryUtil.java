package com.example.server.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-18:42
 * @version: 1.0
 */
public class SqlSessionFactoryUtil {

    public static SqlSessionFactory sqlSessionFactory = null;

    private SqlSessionFactoryUtil() {}

    static{
        String resource = "mybatisConfig.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
