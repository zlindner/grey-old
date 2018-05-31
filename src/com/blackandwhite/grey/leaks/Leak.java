package com.blackandwhite.grey.leaks;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Leak {

    private SimpleObjectProperty<LeakStatus> status;
    private SimpleStringProperty address;
    private SimpleStringProperty dateOpened;
    private SimpleStringProperty opener;
    private SimpleStringProperty dateClosed;
    private SimpleStringProperty closer;
    private SimpleStringProperty dateAssigned;
    private SimpleStringProperty assignee;

    public ObjectProperty<LeakStatus> getStatus() {
        return status;
    }

    public void setStatus(LeakStatus status) {
        this.status.set(status);
    }

    public String getAddress() {
        return address.get();
    }

    public String getDateOpened() {
        return dateOpened.get();
    }

    public String getOpener() {
        return opener.get();
    }

    public String getOpened() {
        return String.format("%s: %s", getOpener(), getDateOpened());
    }

    public String getDateClosed() {
        if (dateClosed == null || dateClosed.get() == null) {
            return "";
        }

        return dateClosed.get();
    }

    public String getCloser() {
        if (closer == null || closer.get() == null) {
            return "";
        }

        return closer.get();
    }

    public String getClosed() {
        if (getDateClosed().isEmpty() || getCloser().isEmpty()) {
            return "";
        }

        return String.format("%s: %s", getCloser(), getDateClosed());
    }

    public String getDateAssigned() {
        if (dateAssigned == null || dateAssigned.get() == null) {
            return "";
        }

        return dateAssigned.get();
    }

    public String getAssignee() {
        if (assignee == null || assignee.get() == null) {
            return "";
        }

        return assignee.get();
    }

    public String getAssigned() {
        if (getDateAssigned().isEmpty() || getAssignee().isEmpty()) {
            return "";
        }

        return String.format("%s: %s", getAssignee(), getDateAssigned());
    }

    public static class Builder {

        private SimpleObjectProperty<LeakStatus> status;
        private SimpleStringProperty address;
        private SimpleStringProperty dateOpened;
        private SimpleStringProperty opener;
        private SimpleStringProperty dateClosed;
        private SimpleStringProperty closer;
        private SimpleStringProperty dateAssigned;
        private SimpleStringProperty assignee;

        public Builder status(LeakStatus status) {
            this.status = new SimpleObjectProperty<>(status);

            return this;
        }

        public Builder address(String address) {
            this.address = new SimpleStringProperty(address);

            return this;
        }

        public Builder openedAt(String dateOpened) {
            this.dateOpened = new SimpleStringProperty(dateOpened);

            return this;
        }

        public Builder openedBy(String opener) {
            this.opener = new SimpleStringProperty(opener);

            return this;
        }

        public Builder closedAt(String dateClosed) {
            this.dateClosed = new SimpleStringProperty(dateClosed);

            return this;
        }

        public Builder closedBy(String closer) {
            this.closer = new SimpleStringProperty(closer);

            return this;
        }

        public Builder assignedAt(String dateAssigned) {
            this.dateAssigned = new SimpleStringProperty(dateAssigned);

            return this;
        }

        public Builder assignedTo(String assignee) {
            this.assignee = new SimpleStringProperty(assignee);

            return this;
        }

        public Leak build() {
            Leak leak = new Leak();

            leak.status = this.status;
            leak.address = this.address;
            leak.dateOpened = this.dateOpened;
            leak.opener = this.opener;

            if (this.dateClosed == null) {
                this.dateClosed = new SimpleStringProperty("");
            }

            leak.dateClosed = this.dateClosed;

            if (this.closer == null) {
                this.closer = new SimpleStringProperty("");
            }

            leak.closer = this.closer;

            if (this.dateAssigned == null) {
                this.dateAssigned = new SimpleStringProperty("");
            }

            leak.dateAssigned = this.dateAssigned;

            if (this.assignee == null) {
                this.assignee = new SimpleStringProperty("");
            }

            leak.assignee = this.assignee;

            return leak;
        }
    }
}
