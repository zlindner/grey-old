package com.blackandwhite.grey.customers;

import com.blackandwhite.grey.Controller;
import com.blackandwhite.grey.Modal;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ControllerCustomers extends Controller {

    private static Modal add;
    private static Modal search;

    @Override
    public void init() {
        add = new Modal.Builder().fxml("customers/add/add.fxml").width(700).height(600).build();
        search = new Modal.Builder().fxml("customers/search/search.fxml").width(700).height(600).build();
    }

    @FXML
    private void add(MouseEvent e) {
        add.show();
    }

    @FXML
    private void search(MouseEvent e) {
        search.show();
    }
}
