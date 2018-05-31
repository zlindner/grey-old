package com.blackandwhite.grey.leaks;

import com.blackandwhite.grey.DataSource;
import com.blackandwhite.grey.TableCellComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ControllerLeaks {

    @FXML
    private TextField tfAddress;

    @FXML
    private TableView<Leak> table;

    @FXML
    private TableColumn<Leak, LeakStatus> colStatus;

    @FXML
    private TableColumn<Leak, String> colAddress;

    @FXML
    private TableColumn<Leak, String> colOpened;

    @FXML
    private TableColumn<Leak, String> colClosed;

    @FXML
    private TableColumn<Leak, String> colAssigned;

    private ObservableList<Leak> leaks;

    private PseudoClass error;

    public void initialize() {
        table.setEditable(true);

        colStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOpened.setCellValueFactory(new PropertyValueFactory<>("opened"));
        colClosed.setCellValueFactory(new PropertyValueFactory<>("closed"));
        colAssigned.setCellValueFactory(new PropertyValueFactory<>("assigned"));

        Callback<TableColumn<Leak, LeakStatus>, TableCell<Leak, LeakStatus>> comboBoxCellFactory = (TableColumn<Leak, LeakStatus> param) -> new TableCellComboBox<>();
        colStatus.setCellFactory(comboBoxCellFactory);
        colStatus.setOnEditCommit((TableColumn.CellEditEvent<Leak, LeakStatus> t) -> {
            Leak leak = t.getTableView().getItems().get(t.getTablePosition().getRow());
            LeakStatus newStatus = t.getNewValue();

            String address = leak.getAddress();
            String dateOpened = leak.getDateOpened();
            String opener = leak.getOpener();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            String user = DataSource.getInstance().getBasicDS().getUsername();
            String query = "";
            boolean cancel = false;

            switch(newStatus) {
                case OPEN:
                    query = "UPDATE LEAK SET status='" + LeakStatus.OPEN + "', date_opened='" + date + "', opener='" + user + "', date_closed=null, closer=null, date_assigned=null, assignee=null WHERE address='" + address + "' AND date_opened='" + dateOpened + "' AND opener='" + opener + "'";

                    break;
                case CLOSED:
                    //TODO remove assigned information on closed?
                    query = "UPDATE LEAK SET status='" + LeakStatus.CLOSED + "', date_closed='" + date + "', closer='" + user + "' WHERE address='" + address + "' AND date_opened='" + dateOpened + "' AND opener='" + opener + "'";

                    break;
                case ASSIGNED:
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Assign Leak");
                    dialog.setContentText("Assignee name:");
                    dialog.setGraphic(null);
                    dialog.setHeaderText(null);
                    Optional<String> result = dialog.showAndWait();

                    String assignee;

                    if (result.isPresent()) {
                        assignee = result.get();
                    } else {
                        cancel = true;
                        break;
                    }

                    query = "UPDATE LEAK SET status='" + LeakStatus.ASSIGNED + "', date_assigned='" + date + "', assignee='" + assignee + "' WHERE address='" + address + "' AND date_opened='" + dateOpened + "' AND opener='" + opener + "'";

                    break;
            }

            if (!cancel) {
                BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

                try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
                    st.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //TODO better way to refresh table?
            try {
                leaks = getLeaks();
                table.setItems(leaks);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        try {
            leaks = getLeaks();
            table.setItems(leaks);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        error = PseudoClass.getPseudoClass("error");
    }

    private ObservableList<Leak> getLeaks() throws SQLException {
        ObservableList<Leak> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM LEAK";
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                LeakStatus status = LeakStatus.valueOf(rs.getString("status"));
                String address = rs.getString("address");
                String dateOpened = rs.getString("date_opened");
                String opener = rs.getString("opener");
                String dateClosed = rs.getString("date_closed");
                String closer = rs.getString("closer");
                String dateAssigned = rs.getString("date_assigned");
                String assignee = rs.getString("assignee");

                Leak l = new Leak.Builder().status(status).address(address).openedAt(dateOpened).openedBy(opener).closedAt(dateClosed).closedBy(closer).assignedAt(dateAssigned).assignedTo(assignee).build();

                list.add(l);
            }
        }

        return list;
    }

    @FXML
    private void open() throws SQLException {
        String address = tfAddress.getText();

        if (address.isEmpty()) {
            tfAddress.pseudoClassStateChanged(error, true);

            Alert alert = new Alert(Alert.AlertType.NONE, "You must specify an address for the leak.\n", ButtonType.OK);
            alert.showAndWait();

            return;
        } else {
            tfAddress.pseudoClassStateChanged(error, false);
        }

        String opener = DataSource.getInstance().getBasicDS().getUsername();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());

        String query = "INSERT INTO LEAK (" +
                "status," +
                "address," +
                "date_opened," +
                "opener)" +
                "VALUES (" +
                "'" + LeakStatus.OPEN.name() + "'," +
                "'" + address + "'," +
                "'" + date + "'," +
                "'" + opener + "')";

        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();

        try (Connection con = basicDS.getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.execute(query);
        }

        Leak leak = new Leak.Builder().status(LeakStatus.OPEN).address(address).openedAt(date).openedBy(opener).build();

        leaks.add(leak);

        reset();
    }

    private void reset() {
        tfAddress.clear();
    }
}
