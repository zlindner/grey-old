package com.blackandwhite.grey.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

//TODO dragging of modals
public class ControllerCustomers {

    private static Stage newCustomer;
    private static Stage searchCustomer;

    @FXML
    private void newCustomer(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/new_customer.fxml"));

        try {
            Parent root = loader.load();

            Scene scene = new Scene(root, 500, 600);

            newCustomer = new Stage();
            newCustomer.setScene(scene);
            newCustomer.initModality(Modality.APPLICATION_MODAL);
            newCustomer.setResizable(false);
            newCustomer.initStyle(StageStyle.UNDECORATED);
            newCustomer.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void cancelNew(ActionEvent e) {
        newCustomer.close();
    }

    @FXML
    private void confirmNew(ActionEvent e) {
        //TODO stuff
    }

    @FXML
    private void searchCustomers(MouseEvent e) {

    }
}
