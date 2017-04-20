package com.kailiang.gcitlms.bean;

import java.util.List;

public class Branch {
    private Integer branchId;
    private String branchName;
    private String branchAddress;
    private List<BookCopy> bookCopies;

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        if (branchId != null ? !branchId.equals(branch.branchId) : branch.branchId != null) return false;
        if (branchName != null ? !branchName.equals(branch.branchName) : branch.branchName != null) return false;
        return branchAddress != null ? branchAddress.equals(branch.branchAddress) : branch.branchAddress == null;
    }

    @Override
    public int hashCode() {
        int result = branchId != null ? branchId.hashCode() : 0;
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        result = 31 * result + (branchAddress != null ? branchAddress.hashCode() : 0);
        return result;
    }
}
