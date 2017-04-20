package com.kailiang.gcitlms.bean;

public class Borrower {
    private Integer cardNo;
    private String name;
    private String address;
    private String phone;
    private String username;
    private String password;
    private boolean hasLoans;

    public boolean isHasLoans() {
        return hasLoans;
    }

    public void setHasLoans(boolean hasLoans) {
        this.hasLoans = hasLoans;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrower borrower = (Borrower) o;

        if (cardNo != null ? !cardNo.equals(borrower.cardNo) : borrower.cardNo != null) return false;
        if (name != null ? !name.equals(borrower.name) : borrower.name != null) return false;
        if (address != null ? !address.equals(borrower.address) : borrower.address != null) return false;
        if (phone != null ? !phone.equals(borrower.phone) : borrower.phone != null) return false;
        if (username != null ? !username.equals(borrower.username) : borrower.username != null) return false;
        return password != null ? password.equals(borrower.password) : borrower.password == null;
    }

    @Override
    public int hashCode() {
        int result = cardNo != null ? cardNo.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
