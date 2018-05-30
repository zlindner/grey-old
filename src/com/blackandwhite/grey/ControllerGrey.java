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

    @FXML
    private BorderPane content;

    @FXML
    private Label lblWelcome;

    @FXML
    private Button dashboard;

    private Button btnActive;
    private Stage stage;
    private double dx;
    private double dy;

    public void initialize() {
        btnActive = dashboard;
        btnActive.getStyleClass().add("active");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(String user) {
        lblWelcome.setText("Welcome, " + user);
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

        btnActive.getStyleClass().remove("active");
        btnActive = btn;
        btnActive.getStyleClass().add("active");

        try {
            content.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
