package com.blackandwhite.grey.login;

import com.blackandwhite.grey.ControllerGrey;
import com.blackandwhite.grey.Database;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class ControllerLogin {

    private Stage stage;
    private double dx;
    private double dy;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    private PseudoClass error = PseudoClass.getPseudoClass("error");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void menubarPressed(MouseEvent e) {
        dx = stage.getX() - e.getScreenX();
        dy = stage.getY() - e.getScreenY();
    }

    @FXML
    private void menubarDragged(MouseEvent e) {
        stage.setX(e.getScreenX() + dx);
        stage.setY(e.getScreenY() + dy);
    }

    @FXML
    private void close() {
        Platform.exit();
    }

    @FXML
    private void login() {
        String user = userField.getText();
        String pass = passField.getText();

        if (user.isEmpty()) {
            userField.pseudoClassStateChanged(error, true);
        } else {
            userField.pseudoClassStateChanged(error, false);
        }

        if (pass.isEmpty()) {
            passField.pseudoClassStateChanged(error, true);
        } else {
            passField.pseudoClassStateChanged(error, false);
        }

        Database.establishConnection(user, pass);
        Connection con = Database.getConnection();

        if (con == null) {
            System.out.println("Error establishing connection to database");
            return;
        }

        //TODO init database -> create tables if not exists, etc...
        //TODO progress bar / indicator for init database???

        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../grey.fxml"));

        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ControllerGrey grey = loader.getController();
        grey.setStage(stage);
        grey.setUser(user);

        Scene scene = new Scene(root, 1170, 720);

        stage.setScene(scene);
        stage.show();
    }
}
