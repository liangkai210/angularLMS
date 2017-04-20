package com.kailiang.gcitlms.bean;

import java.util.Date;

public class BookLoan {
    private Book book;
    private Branch branch;
    private Borrower borrower;
    private Date dateOut;
    private Date dueDate;
    private Date dateIn;

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

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookLoan bookLoan = (BookLoan) o;

        if (book != null ? !book.equals(bookLoan.book) : bookLoan.book != null) return false;
        if (branch != null ? !branch.equals(bookLoan.branch) : bookLoan.branch != null) return false;
        if (borrower != null ? !borrower.equals(bookLoan.borrower) : bookLoan.borrower != null) return false;
        if (dateOut != null ? !dateOut.equals(bookLoan.dateOut) : bookLoan.dateOut != null) return false;
        if (dueDate != null ? !dueDate.equals(bookLoan.dueDate) : bookLoan.dueDate != null) return false;
        return dateIn != null ? dateIn.equals(bookLoan.dateIn) : bookLoan.dateIn == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (borrower != null ? borrower.hashCode() : 0);
        result = 31 * result + (dateOut != null ? dateOut.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (dateIn != null ? dateIn.hashCode() : 0);
        return result;
    }
}
