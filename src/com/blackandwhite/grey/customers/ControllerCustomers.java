package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.Controller;
import com.blackandwhite.grey.Modal;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class ControllerCustomers extends Controller {

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
    private TextField provinceField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField workField;

    @FXML
    private TextField cellField;

    @FXML
    private TextField homeField;

    private PseudoClass error = PseudoClass.getPseudoClass("error");

    @Override
    public void init() {

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
        String province = provinceField.getText();
        String postal = postalField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String work = workField.getText();
        String cell = cellField.getText();
        String home = homeField.getText();



        if (!postal.isEmpty()) {
            if (validatePostal(postal)) {
                postal = postal.replaceAll("\\s", "");
                postalField.pseudoClassStateChanged(error, false);

                System.out.println(postal);
            } else {
                postalField.pseudoClassStateChanged(error, true);
            }
        }

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

    private boolean validateAdd(String first, String last, String city, String province, String postal, String address, String email, String work, String cell, String home) {
        boolean valid = true;

        if (first.isEmpty()) {
            firstField.pseudoClassStateChanged(error, true);
            valid = false;
        } else {
            firstField.pseudoClassStateChanged(error, false);
        }

        if (last.isEmpty()) {
            lastField.pseudoClassStateChanged(error, true);
            valid = false;
        } else {
            lastField.pseudoClassStateChanged(error, false);
        }

        if (work.isEmpty() && cell.isEmpty() && home.isEmpty()) {
            workField.pseudoClassStateChanged(error, true);
            cellField.pseudoClassStateChanged(error, true);
            homeField.pseudoClassStateChanged(error, true);
            valid = false;
        } else {
            workField.pseudoClassStateChanged(error, false);
            cellField.pseudoClassStateChanged(error, false);
            homeField.pseudoClassStateChanged(error, false);
        }

        return valid;
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
