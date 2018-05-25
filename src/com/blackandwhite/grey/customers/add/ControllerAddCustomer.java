package com.blackandwhite.grey.customers.add;

import com.blackandwhite.grey.DataSource;
import com.blackandwhite.grey.IModalController;
import com.blackandwhite.grey.Modal;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerAddCustomer implements IModalController {

    //TODO could be replaced by programmatically created confirm button in modal class if every modal ends up needing a confirm (would remove need for IModalController)
    private Modal modal;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfCity;

    //TODO dropdown menu?
    @FXML
    private TextField provField;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfWorkPhone;

    @FXML
    private TextField tfCellPhone;

    @FXML
    private TextField tfHomePhone;

    private PseudoClass error;

    public void initialize() {
        error = PseudoClass.getPseudoClass("error");
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    @FXML
    private void confirm() throws SQLException {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String city = tfCity.getText();
        String province = provField.getText();
        String postalCode = tfPostalCode.getText();
        String address = tfAddress.getText();
        String email = tfEmail.getText();
        String workPhone = tfWorkPhone.getText();
        String cellPhone = tfCellPhone.getText();
        String homePhone = tfHomePhone.getText();

        boolean valid = true;

        if (firstName.isEmpty() || lastName.isEmpty() || (email.isEmpty() && workPhone.isEmpty() && cellPhone.isEmpty() && homePhone.isEmpty())) {
            valid = false;
        }

        if (firstName.isEmpty()) {
            tfFirstName.pseudoClassStateChanged(error, true);
        } else {
            tfFirstName.pseudoClassStateChanged(error, false);
        }

        if (lastName.isEmpty()) {
            tfLastName.pseudoClassStateChanged(error, true);
        } else {
            tfLastName.pseudoClassStateChanged(error, false);
        }

        //TODO city validation
        if (!city.isEmpty()) {

        }

        if (!postalCode.isEmpty()) {
            if (validatePostal(postalCode)) {
                postalCode = postalCode.replaceAll("\\s", "");
                tfPostalCode.pseudoClassStateChanged(error, false);
            } else {
                tfPostalCode.pseudoClassStateChanged(error, true);
            }
        }

        //TODO address validation

        if (!email.isEmpty()) {
            if (validateEmail(email)) {
                tfEmail.pseudoClassStateChanged(error, false);
            } else {
                tfEmail.pseudoClassStateChanged(error, true);
            }
        }

        if (!workPhone.isEmpty()) {
            if (validateNumber(workPhone)) {
                workPhone = workPhone.replaceAll("[^\\d.]", "");
                tfWorkPhone.pseudoClassStateChanged(error, false);
            } else {
                tfWorkPhone.pseudoClassStateChanged(error, true);
            }
        }

        if (!cellPhone.isEmpty()) {
            if (validateNumber(cellPhone)) {
                cellPhone = cellPhone.replaceAll("[^\\d.]", "");
                tfCellPhone.pseudoClassStateChanged(error, false);
            } else {
                tfCellPhone.pseudoClassStateChanged(error, true);
            }
        }

        if (!homePhone.isEmpty()) {
            if (validateNumber(homePhone)) {
                homePhone = homePhone.replaceAll("[^\\d.]", "");
                tfHomePhone.pseudoClassStateChanged(error, false);
            } else {
                tfHomePhone.pseudoClassStateChanged(error, true);
            }
        }

        if (!valid) {
            return;
        }

        String query = "INSERT INTO CUSTOMER (" +
                "first_name," +
                "last_name," +
                "city," +
                "province," +
                "postal_code," +
                "address," +
                "email," +
                "work_phone," +
                "cell_phone," +
                "home_phone) " +
                "VALUES (" +
                "'" + firstName + "'," +
                "'" + lastName + "'," +
                "'" + city + "'," +
                "'" + province + "'," +
                "'" + postalCode + "'," +
                "'" + address + "'," +
                "'" + email + "'," +
                "'" + workPhone + "'," +
                "'" + cellPhone + "'," +
                "'" + homePhone + "')";

        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.execute(query);
            modal.close();
        }
    }

    private boolean validatePostal(String postal) {
        if (Pattern.matches("^[0-9a-zA-Z ]*$", postal)) {
            postal = postal.replaceAll("\\s", "");

            return postal.length() == 6;
        }

        return false;
    }

    private boolean validateEmail(String email) {
        Pattern regex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        return regex.matcher(email).matches();
    }

    private boolean validateNumber(String num) {
        if (Pattern.matches("^[0-9- ]*$", num)) {
            num = num.replaceAll("[^\\d.]", "");

            return num.length() == 10;
        } else {
            return false;
        }
    }
}
