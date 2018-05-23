package com.blackandwhite.grey;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.Statement;

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("grey.fxml"));

        Parent root = loader.load();

        ControllerGrey controller = loader.getController();
        controller.init();
        controller.setStage(stage);

        Scene scene = new Scene(root, 1170, 720);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        Database.establishConnection();
        Connection con = Database.getConnection();

        if (con == null) {
            System.out.println("Error establishing connection to database");
            return;
        }



        Application.launch(args);
    }
}
