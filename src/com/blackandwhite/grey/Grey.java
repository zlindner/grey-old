package com.blackandwhite.grey;

import com.blackandwhite.grey.controller.ControllerGrey;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Grey extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/grey.fxml"));

        Parent root = loader.load();

        ControllerGrey controller = loader.getController();
        controller.init(stage);

        Scene scene = new Scene(root, 1170, 720);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
