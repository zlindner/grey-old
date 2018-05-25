package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.DataSource;
import com.blackandwhite.grey.Modal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.regex.Pattern;

public class ControllerCustomers {

    private static Modal add;
    private static Modal search;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfCity;

    //TODO dropdown menu?
    @FXML
    private TextField provField;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfWorkPhone;

    @FXML
    private TextField tfCellPhone;

    @FXML
    private TextField tfHomePhone;

    private PseudoClass error = PseudoClass.getPseudoClass("error");

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

    public void initialize() {
        ObservableList<Customer> customers = null;

        try {
            customers = getCustomerList();
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

    private ObservableList<Customer> getCustomerList() throws SQLException {
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

    /*
     * Add customer
     */

    @FXML
    private void add() {
        add = new Modal.Builder().fxml("customers/add/add.fxml").width(700).height(400).build();
        add.show();
    }

    @FXML
    private void confirmAdd() {
        String first = tfFirstName.getText();
        String last = tfLastName.getText();
        String city = tfCity.getText();
        String prov = provField.getText();
        String postal = tfPostalCode.getText();
        String addr = tfAddress.getText();
        String email = tfEmail.getText();
        String work = tfWorkPhone.getText();
        String cell = tfCellPhone.getText();
        String home = tfHomePhone.getText();

        //TODO extra name validation required???
        if (first.isEmpty()) {
            tfFirstName.pseudoClassStateChanged(error, true);
        } else {
            tfFirstName.pseudoClassStateChanged(error, false);
        }

        if (last.isEmpty()) {
            tfLastName.pseudoClassStateChanged(error, true);
        } else {
            tfLastName.pseudoClassStateChanged(error, false);
        }

        //TODO city validation
        if (!city.isEmpty()) {

        }

        if (!postal.isEmpty()) {
            if (validatePostal(postal)) {
                postal = postal.replaceAll("\\s", "");
                tfPostalCode.pseudoClassStateChanged(error, false);

                System.out.println(postal);
            } else {
                tfPostalCode.pseudoClassStateChanged(error, true);
            }
        }

        //TODO address validation

        if (!email.isEmpty()) {
            if (validateEmail(email)) {
                tfEmail.pseudoClassStateChanged(error, false);

                System.out.println(email);
            } else {
                tfEmail.pseudoClassStateChanged(error, true);
            }
        }

        if (!work.isEmpty()) {
            if (validateNumber(work)) {
                work = work.replaceAll("[^\\d.]", "");
                tfWorkPhone.pseudoClassStateChanged(error, false);

                System.out.println(work);
            } else {
                tfWorkPhone.pseudoClassStateChanged(error, true);
            }
        }

        if (!cell.isEmpty()) {
            if (validateNumber(cell)) {
                cell = cell.replaceAll("[^\\d.]", "");
                tfCellPhone.pseudoClassStateChanged(error, false);

                System.out.println(cell);
            } else {
                tfCellPhone.pseudoClassStateChanged(error, true);
            }
        }

        if (!home.isEmpty()) {
            if (validateNumber(home)) {
                home = home.replaceAll("[^\\d.]", "");
                tfHomePhone.pseudoClassStateChanged(error, false);

                System.out.println(home);
            } else {
                tfHomePhone.pseudoClassStateChanged(error, true);
            }
        }

        /*if (validateAdd(first, last, city, province, postal, address, email, work, cell, home)) {
            Customer customer = new Customer.Builder()
                    .first(first)
                    .last(last)
                    .city(city)
                    .province(province)
                    .postal(postal)
                    .address(address)
                    .email(email)
                    .work(work)
                    .cell(cell)
                    .home(home)
                    .build();

            Connection con = Database.getConnection();
            String query = "INSERT INTO CUSTOMER (" +
                    "first_name," +
                    "last_name," +
                    "city," +
                    "province," +
                    "postal_code," +
                    "address," +
                    "email," +
                    "work_phone," +
                    "cell_phone," +
                    "home_phone) " +
                    "VALUES (" +
                    "'" + first + "'," +
                    "'" + last + "'," +
                    "'" + city + "'," +
                    "'" + province + "'," +
                    "'" + postal + "'," +
                    "'" + address + "'," +
                    "'" + email + "'," +
                    "'" + work + "'," +
                    "'" + cell + "'," +
                    "'" + home + "')";

            Statement st = null;

            try {
                st = con.createStatement();
                st.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println(customer.toString());
            add.close();
        }*/
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

    /*
     * Search customers
     */

    @FXML
    private void search() {
        search = new Modal.Builder().fxml("customers/search/search.fxml").width(700).height(600).build();
        search.show();
    }
}
