package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.Modal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.regex.Pattern;

public class ControllerCustomers {

    private static Modal add;
    private static Modal search;

    @FXML
    private TextField firstField;

    @FXML
    private TextField lastField;

    @FXML
    private TextField cityField;

    //TODO dropdown menu?
    @FXML
    private TextField provField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField addrField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField workField;

    @FXML
    private TextField cellField;

    @FXML
    private TextField homeField;

    private PseudoClass error = PseudoClass.getPseudoClass("error");

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> firstCol;

    @FXML
    private TableColumn<Customer, String> lastCol;

    @FXML
    private TableColumn<Customer, String> cityCol;

    @FXML
    private TableColumn<Customer, String> provCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> emailCol;

    @FXML
    private TableColumn<Customer, String> workCol;

    @FXML
    private TableColumn<Customer, String> cellCol;

    @FXML
    private TableColumn<Customer, String> homeCol;

    @FXML
    private TableColumn<Customer, String> addrCol;

    public void initialize() {
        firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        provCol.setCellValueFactory(new PropertyValueFactory<>("prov"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        workCol.setCellValueFactory(new PropertyValueFactory<>("work"));
        cellCol.setCellValueFactory(new PropertyValueFactory<>("cell"));
        homeCol.setCellValueFactory(new PropertyValueFactory<>("home"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("addr"));

        ObservableList<Customer> list = getCustomerList();
        table.setItems(list);
    }

    private ObservableList<Customer> getCustomerList() {
        Customer c1 = new Customer.Builder().first("Jim").last("Halpert").city("Scranton").prov("Pennsylvania").postal("90210").addr("51 Dunder Mifflin Dr").email("Jimhalps@dunder.com").work("5001231234").cell("5195051234").home("").build();

        ObservableList<Customer> list = FXCollections.observableArrayList(c1);

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
        String first = firstField.getText();
        String last = lastField.getText();
        String city = cityField.getText();
        String prov = provField.getText();
        String postal = postalField.getText();
        String addr = addrField.getText();
        String email = emailField.getText();
        String work = workField.getText();
        String cell = cellField.getText();
        String home = homeField.getText();

        //TODO extra name validation required???
        if (first.isEmpty()) {
            firstField.pseudoClassStateChanged(error, true);
        } else {
            firstField.pseudoClassStateChanged(error, false);
        }

        if (last.isEmpty()) {
            lastField.pseudoClassStateChanged(error, true);
        } else {
            lastField.pseudoClassStateChanged(error, false);
        }

        //TODO city validation
        if (!city.isEmpty()) {

        }

        if (!postal.isEmpty()) {
            if (validatePostal(postal)) {
                postal = postal.replaceAll("\\s", "");
                postalField.pseudoClassStateChanged(error, false);

                System.out.println(postal);
            } else {
                postalField.pseudoClassStateChanged(error, true);
            }
        }

        //TODO address validation

        if (!email.isEmpty()) {
            if (validateEmail(email)) {
                emailField.pseudoClassStateChanged(error, false);

                System.out.println(email);
            } else {
                emailField.pseudoClassStateChanged(error, true);
            }
        }

        if (!work.isEmpty()) {
            if (validateNumber(work)) {
                work = work.replaceAll("[^\\d.]", "");
                workField.pseudoClassStateChanged(error, false);

                System.out.println(work);
            } else {
                workField.pseudoClassStateChanged(error, true);
            }
        }

        if (!cell.isEmpty()) {
            if (validateNumber(cell)) {
                cell = cell.replaceAll("[^\\d.]", "");
                cellField.pseudoClassStateChanged(error, false);

                System.out.println(cell);
            } else {
                cellField.pseudoClassStateChanged(error, true);
            }
        }

        if (!home.isEmpty()) {
            if (validateNumber(home)) {
                home = home.replaceAll("[^\\d.]", "");
                homeField.pseudoClassStateChanged(error, false);

                System.out.println(home);
            } else {
                homeField.pseudoClassStateChanged(error, true);
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
