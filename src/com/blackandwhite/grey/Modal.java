package com.blackandwhite.grey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Modal {

    private Stage stage;
    private IController parent;

    public void show() {
        stage.showAndWait();
    }

    public void close() {
        stage.close();
        parent.initialize();
    }

    public static class Builder {

        private FXMLLoader loader;
        private IController parent;
        private int width;
        private int height;

        public Builder fxml(String fxml) {
            loader = new FXMLLoader(getClass().getResource(fxml));

            return this;
        }

        //TODO better way to do this?
        public Builder parent(IController parent) {
            this.parent = parent;

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
            modal.parent = this.parent;

            if (loader.getController() instanceof IModalController) {
                ((IModalController) loader.getController()).setModal(modal);
            }

            Button close = new Button("x");
            close.setLayoutX(this.width - 50);
            close.setLayoutY(10);
            close.setOnAction(event -> modal.close());
            close.getStyleClass().add("close");

            root.getChildren().add(close);
            root.requestFocus();

            return modal;
        }
    }
}
