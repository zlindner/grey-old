package com.blackandwhite.grey.controller;

import com.blackandwhite.grey.Controller;
import com.blackandwhite.grey.Modal;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ControllerCustomers extends Controller {

    private static Modal newCustomer;
    private static Modal searchCustomer;

    @Override
    public void init() {
        newCustomer = new Modal.Builder().fxml("new_customer").width(700).height(600).build();
        //searchCustomer = new Modal.Builder().fxml("search_customer").width(700).height(600).build();
    }

    @FXML
    private void customerAdd(MouseEvent e) {
        newCustomer.show();
    }

    @FXML
    private void customerSearch(MouseEvent e) {
        searchCustomer.show();
    }
}
