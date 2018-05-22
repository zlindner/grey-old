package com.blackandwhite.grey.customers;

public class Customer {

    private String first;
    private String last;
    private String city;
    private String province;
    private String postal;
    private String address;
    private String email;
    private String work;
    private String cell;
    private String home;

    @Override
    public String toString() {
        return String.format("%s %s\n%s, %s, %s, %s\nEmail: %s\nWork #: %s\nCell #: %s\nHome #: %s", first, last, address, city, province, postal, email, work, cell, home);
    }

    public static class Builder {

        private String first;
        private String last;
        private String city;
        private String province;
        private String postal;
        private String address;
        private String email;
        private String work;
        private String cell;
        private String home;

        public Builder first(String first) {
            this.first = first;

            return this;
        }

        public Builder last(String last) {
            this.last = last;

            return this;
        }

        public Builder city(String city) {
            this.city = city;

            return this;
        }

        public Builder province(String province) {
            this.province = province;

            return this;
        }

        public Builder postal(String postal) {
            this.postal = postal;

            return this;
        }

        public Builder address(String address) {
            this.address = address;

            return this;
        }

        public Builder email(String email) {
            this.email = email;

            return this;
        }

        public Builder work(String work) {
            this.work = work;

            return this;
        }

        public Builder cell(String cell) {
            this.cell = cell;

            return this;
        }

        public Builder home(String home) {
            this.home = home;

            return this;
        }

        public Customer build() {
            Customer customer = new Customer();

            customer.first = this.first;
            customer.last = this.last;
            customer.city = this.city;
            customer.province = this.province;
            customer.postal = this.postal;
            customer.address = this.address;
            customer.email = this.email;
            customer.work = this.work;
            customer.cell = this.cell;
            customer.home = this.home;

            return customer;
        }
    }
}
