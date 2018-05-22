package com.blackandwhite.grey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Modal {

    private Stage stage;

    private double dx;
    private double dy;

    private HBox menubar;
    private Button close;

    public void show() {
        stage.show();
    }

    private void pressed(MouseEvent e) {
        dx = stage.getX() - e.getScreenX();
        dy = stage.getY() - e.getScreenY();
    }

    private void dragged(MouseEvent e) {
        stage.setX(e.getScreenX() + dx);
        stage.setY(e.getScreenY() + dy);
    }

    private void close(ActionEvent e) {
        stage.hide();
    }

    public static class Builder {

        private String fxml;
        private int width;
        private int height;

        public Builder fxml(String fxml) {
            this.fxml = fxml;

            return this;
        }

        public Builder width(int width) {
            this.width = width;

            return this;
        }

        public Builder height(int height) {
            this.height = height;

            return this;
        }

        //TODO rewrite this
        public Modal build() {
            Modal modal = new Modal();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/" + this.fxml + ".fxml"));
            Pane root;

            try {
                root = loader.load();

                Scene scene = new Scene(root, this.width, this.height);

                modal.stage = new Stage();
                modal.stage.initModality(Modality.APPLICATION_MODAL);
                modal.stage.initStyle(StageStyle.UNDECORATED);
                modal.stage.setResizable(false);
                modal.stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            modal.menubar = new HBox();
            modal.menubar.setAlignment(Pos.CENTER_RIGHT);
            modal.menubar.setPrefSize(this.width, 50);
            modal.menubar.setOnMousePressed(modal::pressed);
            modal.menubar.setOnMouseDragged(modal::dragged);

            modal.close = new Button("x");
            modal.close.setOnAction(modal::close);
            modal.close.getStyleClass().add("close");

            modal.menubar.getChildren().add(modal.close);
            root.getChildren().add(modal.menubar);

            return modal;
        }
    }
}
