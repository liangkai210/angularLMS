package com.kailiang.gcitlms.bean;

public class BookCopy {
    private Book book;
    private Branch branch;
    private Integer noOfCopies;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookCopy bookCopy = (BookCopy) o;

        if (book != null ? !book.equals(bookCopy.book) : bookCopy.book != null) return false;
        if (branch != null ? !branch.equals(bookCopy.branch) : bookCopy.branch != null) return false;
        return noOfCopies != null ? noOfCopies.equals(bookCopy.noOfCopies) : bookCopy.noOfCopies == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (noOfCopies != null ? noOfCopies.hashCode() : 0);
        return result;
    }
}
