package com.blackandwhite.grey;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {

    private static DataSource ds;
    private BasicDataSource basicDS = new BasicDataSource();

    private DataSource() {
        basicDS.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDS.setUrl("jdbc:mysql://localhost:3306/grey?serverTimezone=EST&useSSL=false");

        basicDS.setInitialSize(5);
        basicDS.setMaxTotal(150);
        basicDS.setMaxIdle(20);
        basicDS.setMinIdle(5);
        basicDS.setMaxOpenPreparedStatements(180);
    }

    public static DataSource getInstance() {
        if (ds == null) {
            ds = new DataSource();
        }

        return ds;
    }

    public BasicDataSource getBasicDS() {
        return basicDS;
    }

    public void setBasicDS(BasicDataSource basicDS) {
        this.basicDS = basicDS;
    }

    public void setUsername(String username) {
        getBasicDS().setUsername(username);
    }

    public void setPassword(String password) {
        getBasicDS().setPassword(password);
    }
}
