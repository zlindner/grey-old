package com.blackandwhite.grey;

import com.blackandwhite.grey.login.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Grey extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login/login.fxml"));

        Pane root = loader.load();

        ControllerLogin login = loader.getController();
        login.setStage(stage);

        Scene scene = new Scene(root, 400, 500);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        root.requestFocus();

        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
