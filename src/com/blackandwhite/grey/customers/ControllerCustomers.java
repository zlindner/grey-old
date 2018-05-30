package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.regex.Pattern;

public class ControllerCustomers {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private ComboBox<String> cbProvince;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfWorkPhone;

    @FXML
    private TextField tfCellPhone;

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> colName;

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
    private TableColumn<Customer, String> colAddress;

    private ObservableList<Customer> customers;

    private PseudoClass error;

    public void initialize() {
        cbProvince.getItems().add("Ontario");
        cbProvince.getItems().add("British Columbia");
        cbProvince.getItems().add("Quebec");
        cbProvince.getItems().add("Alberta");
        cbProvince.getItems().add("Nova Scotia");
        cbProvince.getItems().add("Saskatchewan");
        cbProvince.getItems().add("Manitoba");
        cbProvince.getItems().add("New Brunswick");
        cbProvince.getItems().add("Newfoundland and Labrador");
        cbProvince.getItems().add("Prince Edward Island");

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        colCellPhone.setCellValueFactory(new PropertyValueFactory<>("cellPhone"));

        try {
            customers = getCustomers();
            table.setItems(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        error = PseudoClass.getPseudoClass("error");
    }

    private ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM CUSTOMER";
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String city = rs.getString("city");
                String province = rs.getString("province");
                String postalCode = rs.getString("postal_code");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String workPhone = rs.getString("work_phone");
                String cellPhone = rs.getString("cell_phone");

                Customer c = new Customer.Builder()
                        .name(name)
                        .address(address)
                        .city(city)
                        .province(province)
                        .postalCode(postalCode)
                        .email(email)
                        .workPhone(workPhone)
                        .cellPhone(cellPhone)
                        .build();

                list.add(c);
            }
        }

        return list;
    }

    @FXML
    private void add() throws SQLException {
        String name = tfName.getText();
        String address = tfAddress.getText();
        String city = tfCity.getText();
        String province = cbProvince.getValue();
        String postalCode = tfPostalCode.getText();
        String email = tfEmail.getText();
        String workPhone = tfWorkPhone.getText();
        String cellPhone = tfCellPhone.getText();
        String err = "";
        boolean valid = true;

        if (name.isEmpty()) {
            err += "You must provide a name for the customer.\n";
            tfName.pseudoClassStateChanged(error, true);
            valid = false;
        } else {
            tfName.pseudoClassStateChanged(error, false);
        }

        if (province == null) {
            province = "";
        }

        if (!postalCode.isEmpty()) {
            if (validatePostal(postalCode)) {
                postalCode = postalCode.replaceAll("\\s", "");
                tfPostalCode.pseudoClassStateChanged(error, false);
            } else {
                err += "Invalid postal code.\n";
                tfPostalCode.pseudoClassStateChanged(error, true);
                valid = false;
            }
        }

        if (email.isEmpty() && workPhone.isEmpty() && cellPhone.isEmpty()) {
            err += "You must provide at least one form of contact for the customer.\n";
            tfEmail.pseudoClassStateChanged(error, true);
            tfWorkPhone.pseudoClassStateChanged(error, true);
            tfCellPhone.pseudoClassStateChanged(error, true);
            valid = false;
        } else {
            tfEmail.pseudoClassStateChanged(error, false);
            tfWorkPhone.pseudoClassStateChanged(error, false);
            tfCellPhone.pseudoClassStateChanged(error, false);
        }

        if (!email.isEmpty()) {
            if (validateEmail(email)) {
                tfEmail.pseudoClassStateChanged(error, false);
            } else {
                err += "Invalid email.\n";
                tfEmail.pseudoClassStateChanged(error, true);
                valid = false;
            }
        }

        if (!workPhone.isEmpty()) {
            if (validateNumber(workPhone)) {
                workPhone = workPhone.replaceAll("[^\\d.]", "");
                tfWorkPhone.pseudoClassStateChanged(error, false);
            } else {
                err += "Invalid work phone number.\n";
                tfWorkPhone.pseudoClassStateChanged(error, true);
                valid = false;
            }
        }

        if (!cellPhone.isEmpty()) {
            if (validateNumber(cellPhone)) {
                cellPhone = cellPhone.replaceAll("[^\\d.]", "");
                tfCellPhone.pseudoClassStateChanged(error, false);
            } else {
                err += "Invalid cell phone number.\n";
                tfCellPhone.pseudoClassStateChanged(error, true);
                valid = false;
            }
        }

        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.NONE, err, ButtonType.OK);
            alert.showAndWait();

            return;
        }

        String query = "INSERT INTO CUSTOMER (" +
                "name," +
                "address," +
                "city," +
                "province," +
                "postal_code," +
                "email," +
                "work_phone," +
                "cell_phone) " +
                "VALUES (" +
                "'" + name + "'," +
                "'" + address + "'," +
                "'" + city + "'," +
                "'" + province + "'," +
                "'" + postalCode + "'," +
                "'" + email + "'," +
                "'" + workPhone + "'," +
                "'" + cellPhone + "')";

        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.execute(query);
        }

        Customer c = new Customer.Builder()
                .name(name)
                .address(address)
                .city(city)
                .province(province)
                .postalCode(postalCode)
                .email(email)
                .workPhone(workPhone)
                .cellPhone(cellPhone)
                .build();

        customers.add(c);

        reset();
    }

    @FXML
    private void delete() {
        ObservableList<Customer> selected = table.getSelectionModel().getSelectedItems();

        if (selected.size() == 0) {
            return;
        }

        Customer c = selected.get(0);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + c.getName() + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() != ButtonType.YES) {
            return;
        }

        table.getItems().removeAll(c);

        String query = "DELETE FROM CUSTOMER WHERE " +
                "name = '" + c.getName() + "' AND " +
                "address = '" + c.getAddress() + "' AND " +
                "city = '" + c.getCity() + "' AND " +
                "province = '" + c.getProvince() + "' AND " +
                "postal_code = '" + c.getPostalCode() + "' AND " +
                "email = '" + c.getEmail() + "' AND " +
                "work_phone = '" + c.getWorkPhone() + "' AND " +
                "cell_phone = '" + c.getCellPhone() + "'";

        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filter() {
        FilteredList<Customer> filter = new FilteredList<>(customers);

        table.setItems(filter);

        filter.setPredicate(c -> {
            String name = tfName.getText();
            String address = tfAddress.getText();
            String city = tfCity.getText();
            String province = cbProvince.getValue();
            String postalCode = tfPostalCode.getText();
            String email = tfEmail.getText();
            String workPhone = tfWorkPhone.getText();
            String cellPhone = tfCellPhone.getText();

            if (!name.isEmpty() && !name.equalsIgnoreCase(c.getName())) {
                return false;
            }

            if (!address.isEmpty() && !address.equalsIgnoreCase(c.getAddress())) {
                return false;
            }

            if (!city.isEmpty() && !city.equalsIgnoreCase(c.getCity())) {
                return false;
            }

            if (province != null && !province.equalsIgnoreCase(c.getProvince())) {
                return false;
            }

            if (!postalCode.isEmpty() && !postalCode.equalsIgnoreCase(c.getPostalCode())) {
                return false;
            }

            if (!email.isEmpty() && !email.equalsIgnoreCase(c.getEmail())) {
                return false;
            }

            if (!workPhone.isEmpty() && !workPhone.equals(c.getWorkPhone())) {
                return false;
            }

            if (!cellPhone.isEmpty() && !cellPhone.equals(c.getCellPhone())) {
                return false;
            }

            return true;
        });

        reset();
    }

    @FXML
    private void clearFilter() {
        table.setItems(customers);
    }

    private boolean validatePostal(String postal) {
        if (Pattern.matches("^[0-9a-zA-Z ]*$", postal)) {
            postal = postal.replaceAll("\\s", "");

            return postal.length() == 6;
        }

        return false;
    }

    private boolean validateEmail(String email) {
        Pattern regex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        return regex.matcher(email).matches();
    }

    private boolean validateNumber(String num) {
        if (Pattern.matches("^[0-9- ]*$", num)) {
            num = num.replaceAll("[^\\d.]", "");

            return num.length() == 10;
        } else {
            return false;
        }
    }

    private void reset() {
        tfName.clear();
        tfAddress.clear();
        tfCity.clear();
        cbProvince.getSelectionModel().clearSelection();
        tfPostalCode.clear();
        tfEmail.clear();
        tfWorkPhone.clear();
        tfCellPhone.clear();
    }
}
