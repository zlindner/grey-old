package com.blackandwhite.grey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Modal {

    private Stage stage;

    public void show() {
        stage.show();
    }

    private void close(ActionEvent e) {
        stage.close();
    }

    public static class Builder {

        private FXMLLoader loader;
        private int width;
        private int height;

        public Builder fxml(String fxml) {
            loader = new FXMLLoader(getClass().getResource(fxml));

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

        public Modal build() {
            Modal modal = new Modal();

            Pane root;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);

            try {
                root = loader.load();

                Scene scene = new Scene(root, this.width, this.height);

                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            modal.stage = stage;

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.setPrefSize(this.width, 50);

            Button close = new Button("x");
            close.setOnAction(modal::close);
            close.getStyleClass().add("close");

            hbox.getChildren().add(close);
            root.getChildren().add(hbox);

            return modal;
        }
    }
}
