package com.blackandwhite.grey.dashboard;

import com.blackandwhite.grey.DataSource;
import com.blackandwhite.grey.leaks.LeakStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
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

        try {
            pcLeaks.setData(getLeaks());
            pcLeaks.getData().forEach(data -> {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getName() + ": " + (int) data.getPieValue());
                tooltip.setShowDelay(new Duration(0));
                Tooltip.install(data.getNode(), tooltip);
                data.pieValueProperty().addListener((observable, oldValue, newValue) -> tooltip.setText(newValue + "%"));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private ObservableList<PieChart.Data> getLeaks() throws SQLException {
        int open = 0;
        int closed = 0;
        int assigned = 0;
        String query = "SELECT * FROM LEAK";
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                LeakStatus status = LeakStatus.valueOf(rs.getString("status"));

                switch(status) {
                    case OPEN:
                        open++;
                        break;
                    case CLOSED:
                        closed++;
                        break;
                    case ASSIGNED:
                        assigned++;
                        break;
                }
            }
        }

        return FXCollections.observableArrayList(
                new PieChart.Data("Open", open),
                new PieChart.Data("Closed", closed),
                new PieChart.Data("Assigned", assigned)
        );
    }
}
