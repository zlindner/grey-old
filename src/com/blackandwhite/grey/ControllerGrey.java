package com.blackandwhite.grey;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerGrey extends Controller {

    private Stage stage;

    private double dx;
    private double dy;

    @FXML
    private BorderPane content;

    @FXML
    private Button dashboard;
    private Button active;

    @Override
    public void init() {
        active = dashboard;
        active.getStyleClass().add("active");
    }

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
    public void close(ActionEvent e) {
        Platform.exit();
    }

    @FXML
    public void minimize(ActionEvent e) {
        stage.setIconified(true);
    }

    @FXML
    private void sidebarClicked(ActionEvent e) {
        Button b = (Button) e.getSource();
        String id = b.getId();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(id + "/" + id + ".fxml"));

        active.getStyleClass().remove("active");
        active = b;
        active.getStyleClass().add("active");

        try {
            Class controller = Class.forName("com.blackandwhite.grey." + id + ".Controller" + Character.toUpperCase(id.charAt(0)) + id.substring(1));
            Object o = controller.getConstructor().newInstance();

            Method init = o.getClass().getMethod("init");
            init.invoke(o);

            //loader.setController(controller.getConstructor().newInstance());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

        try {
            content.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
