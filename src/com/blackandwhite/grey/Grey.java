package com.blackandwhite.grey;

import com.blackandwhite.grey.login.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//TODO connection pooling, close connection, result, statement on finish...
public class Grey extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        /*String query = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "customer_id INT AUTO_INCREMENT PRIMARY KEY," +
                "first_name VARCHAR(30) NOT NULL," +
                "last_name VARCHAR(30) NOT NULL," +
                "city VARCHAR(30)," +
                "province VARCHAR(30)," +
                "postal_code VARCHAR(6)," +
                "address VARCHAR(30)," +
                "email VARCHAR(30)," +
                "work_phone VARCHAR(10)," +
                "home_phone VARCHAR(10)," +
                "cell_phone VARCHAR(10))";

        Statement statement = connection.createStatement();
        statement.execute(query);*/

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
