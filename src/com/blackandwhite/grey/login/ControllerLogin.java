package com.blackandwhite.grey.login;

import com.blackandwhite.grey.ControllerGrey;
import com.blackandwhite.grey.DataSource;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControllerLogin {

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Label lblError;

    private Stage stage;
    private double dx;
    private double dy;
    private PseudoClass error;

    public void initialize() {
        error = PseudoClass.getPseudoClass("error");
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
    private void close() {
        Platform.exit();
    }

    @FXML
    private void login() {
        String user = tfUsername.getText();
        String pass = pfPassword.getText();
        boolean doConnect = true;

        lblError.setText("");

        if (user.isEmpty()) {
            tfUsername.pseudoClassStateChanged(error, true);
            doConnect = false;
        } else {
            tfUsername.pseudoClassStateChanged(error, false);
        }

        if (pass.isEmpty()) {
            pfPassword.pseudoClassStateChanged(error, true);
            doConnect = false;
        } else {
            pfPassword.pseudoClassStateChanged(error, false);
        }

        if (!doConnect) {
            lblError.setText("Invalid login information.");
            return;
        }

        DataSource.getInstance().setUsername(user);
        DataSource.getInstance().setPassword(pass);
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection()) {
            initDatabase();
        } catch (SQLException e) {
            lblError.setText("Error connecting to the database.");
            return;
        }

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
        stage.centerOnScreen();
    }

    private void initDatabase() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "customer_id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "address VARCHAR(30)," +
                "city VARCHAR(30)," +
                "province VARCHAR(30)," +
                "postal_code VARCHAR(6)," +
                "email VARCHAR(50)," +
                "work_phone VARCHAR(10)," +
                "cell_phone VARCHAR(10))";

        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.execute();
        }
    }
}
