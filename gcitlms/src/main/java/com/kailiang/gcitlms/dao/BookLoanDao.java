package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.BookLoan;
import com.kailiang.gcitlms.bean.Borrower;
import com.kailiang.gcitlms.bean.Branch;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookLoanDao extends BaseDao<BookLoan> implements ResultSetExtractor<List<BookLoan>> {

    public void addBookLoan(BookLoan bookLoan) {
        String sql = "INSERT INTO tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, new Object[]{bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo(),
                bookLoan.getDateOut(), bookLoan.getDueDate(), bookLoan.getDateIn()});
    }

    public void updateBookLoan(BookLoan bookLoan) {
        String sql = "UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?";
        template.update(sql, new Object[]{bookLoan.getDueDate(), bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo()});
    }

    public void deleteBookLoan(BookLoan bookLoan) {
        String sql = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
        template.update(sql, new Object[]{bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo()});
    }

    public BookLoan getLoanById(BookLoan bookLoan) {
        String sql = "SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
        List<BookLoan> bookLoans = template.query(sql, new Object[]{bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo()}, this);
        if (bookLoans != null) {
            return bookLoans.get(0);
        }
        return null;
    }

    public boolean isHasLoan(Integer cardNo) {
        String sql = "SELECT * FROM tbl_book_loans WHERE cardNo = ?";
        List<BookLoan> bookLoans = template.query(sql, new Object[]{cardNo}, this);
        if (bookLoans != null && !bookLoans.isEmpty()) {
            return true;
        }
        return false;
    }

    public BookLoan getLoanByBookId(BookLoan bookLoan) {
        String sql = "SELECT * FROM tbl_book_loans WHERE bookId = ?";
        List<BookLoan> bookLoans = template.query(sql, new Object[]{bookLoan.getBook().getBookId()}, this);
        if (bookLoans != null && !bookLoans.isEmpty()) {
            return bookLoans.get(0);
        }
        return null;
    }

    public List<BookLoan> getAllBookLoans(String searchString,Branch branch, Borrower borrower) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_book_loans bl inner join tbl_book b on bl.bookId = b.bookId where b.title like ? AND bl.branchId = ? AND bl.cardNo = ?";
            return template.query(sql, new Object[]{searchString, branch.getBranchId(), borrower.getCardNo()}, this);
        } else {
            String sql = "select * from tbl_book_loans bl inner join tbl_book b on bl.bookId = b.bookId WHERE branchId = ? AND cardNo = ?";
            return template.query(sql, new Object[]{branch.getBranchId(), borrower.getCardNo()}, this);
        }
    }

    public List<BookLoan> getLoansFromBorrower(String searchString, Borrower borrower) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "SELECT * FROM tbl_book_loans bl INNER JOIN tbl_book b ON bl.bookId = b.bookId WHERE b.title like ? AND bl.cardNo = ?";
            return template.query(sql, new Object[]{searchString, borrower.getCardNo()}, this);
        } else {
            String sql = "SELECT * FROM tbl_book_loans bl INNER JOIN tbl_book b ON bl.bookId = b.bookId WHERE bl.cardNo = ?";
            return template.query(sql, new Object[]{borrower.getCardNo()}, this);
        }
    }

    public int getBookLoanCountInBranch(Branch branch, Borrower borrower) {
        String sql = "SELECT count(*) FROM tbl_book_loans WHERE branchId = ? AND cardNo = ?";
        return template.queryForObject(sql, new Object[]{branch.getBranchId(), borrower.getCardNo()}, Integer.class);
    }

    public int getBookLoanLikeCountInBranch(String title, Branch branch, Borrower borrower) {
        String sql = "SELECT count(*) FROM tbl_book_loans bl INNER JOIN tbl_book b ON bl.bookId = b.bookId WHERE bl.branchId = ? AND bl.cardNo = ? AND b.title like ?";
        return template.queryForObject(sql, new Object[]{branch.getBranchId(), borrower.getCardNo(), "%" + title + "%"}, Integer.class);
    }

    public int getBookLoanCount(Borrower borrower) {
        String sql = "SELECT count(*) FROM tbl_book_loans WHERE cardNo = ?";
        return template.queryForObject(sql, new Object[]{borrower.getCardNo()}, Integer.class);
    }

    public int getBookLoanLikeCount(String title, Borrower borrower) {
        String sql = "SELECT count(*) FROM tbl_book_loans bl INNER JOIN tbl_book b ON bl.bookId = b.bookId WHERE bl.cardNo = ? AND b.title like ?";
        return template.queryForObject(sql, new Object[]{borrower.getCardNo(), "%" + title + "%"}, Integer.class);
    }

    @Override
    public List<BookLoan> extractData(ResultSet rs) {
        List<BookLoan> bookLoans = new ArrayList<BookLoan>();
        try {
            while (rs.next()) {
                BookLoan bl = new BookLoan();

                Book book = new Book();
                book.setBookId(rs.getInt("bookId"));

                Branch branch = new Branch();
                branch.setBranchId(rs.getInt("branchId"));

                Borrower borrower = new Borrower();
                borrower.setCardNo(rs.getInt("cardNo"));

                bl.setBook(book);
                bl.setBranch(branch);
                bl.setBorrower(borrower);
                bl.setDateOut(rs.getDate("dateOut"));
                bl.setDueDate(rs.getDate("dueDate"));
                bl.setDateIn(rs.getDate("dateIn"));
                bookLoans.add(bl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookLoans;
    }
}
