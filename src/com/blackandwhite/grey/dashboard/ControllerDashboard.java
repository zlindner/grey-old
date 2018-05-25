package com.blackandwhite.grey.dashboard;

import com.blackandwhite.grey.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerDashboard  {

    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblNewCustomers;

    public void initialize() {
        lblCustomers.setText(String.valueOf(getCustomers()));
        lblNewCustomers.setText(getNewCustomers() + " New ↑");
    }

    private int getCustomers() {
        int customers = 0;
        String query = "SELECT * FROM CUSTOMER";

        try {
            Statement st = Database.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                customers++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    //TODO, new customers -> weekly
    private int getNewCustomers() {
        return 1;
    }
}
