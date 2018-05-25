package com.blackandwhite.grey.customers;

import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty city;
    private SimpleStringProperty province;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty address;
    private SimpleStringProperty email;
    private SimpleStringProperty workPhone;
    private SimpleStringProperty cellPhone;
    private SimpleStringProperty homePhone;

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getProvince() {
        return province.get();
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getWorkPhone() {
        return workPhone.get();
    }

    public String getCellPhone() {
        return cellPhone.get();
    }

    public String getHomePhone() {
        return homePhone.get();
    }

    public static class Builder {

        private SimpleStringProperty firstName;
        private SimpleStringProperty lastName;
        private SimpleStringProperty city;
        private SimpleStringProperty province;
        private SimpleStringProperty postalCode;
        private SimpleStringProperty address;
        private SimpleStringProperty email;
        private SimpleStringProperty workPhone;
        private SimpleStringProperty cellPhone;
        private SimpleStringProperty homePhone;

        public Builder firstName(String firstName) {
            this.firstName = new SimpleStringProperty(firstName);

            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = new SimpleStringProperty(lastName);

            return this;
        }

        public Builder city(String city) {
            this.city = new SimpleStringProperty(city);

            return this;
        }

        public Builder province(String province) {
            this.province = new SimpleStringProperty(province);

            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = new SimpleStringProperty(postalCode);

            return this;
        }

        public Builder address(String address) {
            this.address = new SimpleStringProperty(address);

            return this;
        }

        public Builder email(String email) {
            this.email = new SimpleStringProperty(email);

            return this;
        }

        public Builder workPhone(String workPhone) {
            this.workPhone = new SimpleStringProperty(workPhone);

            return this;
        }

        public Builder cellPhone(String cellPhone) {
            this.cellPhone = new SimpleStringProperty(cellPhone);

            return this;
        }

        public Builder homePhone(String homePhone) {
            this.homePhone = new SimpleStringProperty(homePhone);

            return this;
        }

        public Customer build() {
            Customer customer = new Customer();

            customer.firstName = this.firstName;
            customer.lastName = this.lastName;
            customer.city = this.city;
            customer.province = this.province;
            customer.postalCode = this.postalCode;
            customer.address = this.address;
            customer.email = this.email;
            customer.workPhone = this.workPhone;
            customer.cellPhone = this.cellPhone;
            customer.homePhone = this.homePhone;

            return customer;
        }
    }
}
