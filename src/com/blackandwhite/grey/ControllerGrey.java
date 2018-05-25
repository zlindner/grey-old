package com.blackandwhite.grey;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGrey {

    private Stage stage;

    private double dx;
    private double dy;

    @FXML
    private BorderPane content;

    @FXML
    private Label welcome; // welcome text

    @FXML
    private Button btnDashboard;
    private Button btnActive; // currently selected button

    public void initialize() {
        btnActive = btnDashboard;
        btnActive.getStyleClass().add("btnActive");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(String user) {
        welcome.setText("Welcome, " + user);
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
    public void close() {
        Platform.exit();
    }

    @FXML
    public void minimize() {
        stage.setIconified(true);
    }

    @FXML
    private void sidebarClicked(ActionEvent e) {
        Button btn = (Button) e.getSource();
        String id = btn.getId();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(id + "/" + id + ".fxml"));

        btnActive.getStyleClass().remove("btnActive");
        btnActive = btn;
        btnActive.getStyleClass().add("btnActive");

        try {
            content.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
