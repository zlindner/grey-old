package com.blackandwhite.grey.dashboard;

import com.blackandwhite.grey.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ControllerDashboard {

    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblNewCustomers;

    @FXML
    private PieChart pcLeaks;

    public void initialize() {
        try {
            lblCustomers.setText(String.valueOf(getCustomers()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lblNewCustomers.setText(getNewCustomers() + " New ↑");


        int open = 13;
        int closed = 61;

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Open", open),
                new PieChart.Data("Closed", closed));

        pcLeaks.setData(data);
    }

    private int getCustomers() throws SQLException {
        int customers = 0;
        String query = "SELECT * FROM CUSTOMER";
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                customers++;
            }
        }

        return customers;
    }

    //TODO, new customers -> weekly
    private int getNewCustomers() {
        return 0;
    }
}
