package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.DataSource;
import com.blackandwhite.grey.IController;
import com.blackandwhite.grey.Modal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ControllerCustomers implements IController {

    private static Modal add;
    private static Modal search;

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> colFirstName;

    @FXML
    private TableColumn<Customer, String> colLastName;

    @FXML
    private TableColumn<Customer, String> colCity;

    @FXML
    private TableColumn<Customer, String> colProvince;

    @FXML
    private TableColumn<Customer, String> colPostalCode;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, String> colWorkPhone;

    @FXML
    private TableColumn<Customer, String> colCellPhone;

    @FXML
    private TableColumn<Customer, String> colHomePhone;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @Override
    public void initialize() {
        ObservableList<Customer> customers = null;

        try {
            customers = getCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        colCellPhone.setCellValueFactory(new PropertyValueFactory<>("cellPhone"));
        colHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));

        table.setItems(customers);
    }

    private ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM CUSTOMER";
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String city = rs.getString("city");
                String province = rs.getString("province");
                String postalCode = rs.getString("postal_code");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String workPhone = rs.getString("work_phone");
                String cellPhone = rs.getString("cell_phone");
                String homePhone = rs.getString("home_phone");

                Customer c = new Customer.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .city(city)
                        .province(province)
                        .postalCode(postalCode)
                        .address(address)
                        .email(email)
                        .workPhone(workPhone)
                        .cellPhone(cellPhone)
                        .homePhone(homePhone)
                        .build();

                list.add(c);
            }
        }

        return list;
    }

    @FXML
    private void add() {
        add = new Modal.Builder().fxml("customers/add/add_customer.fxml").parent(this).width(700).height(400).build();
        add.show();
    }

    @FXML
    private void search() {
        search = new Modal.Builder().fxml("customers/search/search_customer.fxml").parent(this).width(700).height(600).build();
        search.show();
    }
}
