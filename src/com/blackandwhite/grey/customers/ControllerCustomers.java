package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.Controller;
import com.blackandwhite.grey.Modal;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

        if (validateAdd(first, last, city, province, postal, address, email, work, cell, home)) {
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

            System.out.println(customer.toString());
            add.close();
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
