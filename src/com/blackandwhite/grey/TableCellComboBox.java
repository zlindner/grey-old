package com.blackandwhite.grey;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

public class TableCellComboBox<S, T extends Enum<T>> extends TableCell<S, T> {

    private ComboBox<T> comboBox;

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();

            create();
            setText(null);
            setGraphic(comboBox);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem().name());
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(getItem());
            }

            setText(getItem().name());
            setGraphic(comboBox);

        } else {
            setText(getItem().name());
            setGraphic(null);
        }
    }

    private void create() {
        comboBox = new ComboBox<>();

        comboBox.getItems().addAll(getItem().getDeclaringClass().getEnumConstants());

        comboBoxConverter(comboBox);

        comboBox.valueProperty().set(getItem());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
    }

    private void comboBoxConverter(ComboBox<T> comboBox) {
        comboBox.setCellFactory((c) -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(getItem().name());
                }
            }
        });
    }
}
