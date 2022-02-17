package com.example.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-18:34
 * @version: 1.0
 */
public class DBCPDataSourceFactory extends UnpooledDataSourceFactory {

    public DBCPDataSourceFactory() {
        this.dataSource = new BasicDataSource();
    }

}