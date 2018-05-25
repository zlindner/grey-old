package com.blackandwhite.grey.customers;

import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private SimpleStringProperty first;
    private SimpleStringProperty last;
    private SimpleStringProperty city;
    private SimpleStringProperty prov;
    private SimpleStringProperty postal;
    private SimpleStringProperty addr;
    private SimpleStringProperty email;
    private SimpleStringProperty work;
    private SimpleStringProperty cell;
    private SimpleStringProperty home;

    public static class Builder {

        private SimpleStringProperty first;
        private SimpleStringProperty last;
        private SimpleStringProperty city;
        private SimpleStringProperty prov;
        private SimpleStringProperty postal;
        private SimpleStringProperty addr;
        private SimpleStringProperty email;
        private SimpleStringProperty work;
        private SimpleStringProperty cell;
        private SimpleStringProperty home;

        public Builder first(String first) {
            this.first = new SimpleStringProperty(first);

            return this;
        }

        public Builder last(String last) {
            this.last = new SimpleStringProperty(last);

            return this;
        }

        public Builder city(String city) {
            this.city = new SimpleStringProperty(city);

            return this;
        }

        public Builder prov(String prov) {
            this.prov = new SimpleStringProperty(prov);

            return this;
        }

        public Builder postal(String postal) {
            this.postal = new SimpleStringProperty(postal);

            return this;
        }

        public Builder addr(String addr) {
            this.addr = new SimpleStringProperty(addr);

            return this;
        }

        public Builder email(String email) {
            this.email = new SimpleStringProperty(email);

            return this;
        }

        public Builder work(String work) {
            this.work = new SimpleStringProperty(work);

            return this;
        }

        public Builder cell(String cell) {
            this.cell = new SimpleStringProperty(cell);

            return this;
        }

        public Builder home(String home) {
            this.home = new SimpleStringProperty(home);

            return this;
        }

        public Customer build() {
            Customer customer = new Customer();

            customer.first = this.first;
            customer.last = this.last;
            customer.city = this.city;
            customer.prov = this.prov;
            customer.postal = this.postal;
            customer.addr = this.addr;
            customer.email = this.email;
            customer.work = this.work;
            customer.cell = this.cell;
            customer.home = this.home;

            return customer;
        }
    }
}
