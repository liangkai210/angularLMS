package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.BookCopy;
import com.kailiang.gcitlms.bean.Branch;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCopyDao extends BaseDao<BookCopy> implements ResultSetExtractor<List<BookCopy>> {

    public void addBookCopy(BookCopy bookCopy) {
        String sql = "INSERT INTO tbl_book_copies(bookId, branchId, noOfCopies) VALUES(?, ?, ?)";
        template.update(sql, bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId(), bookCopy.getNoOfCopies());
    }

    public void updateBookCopy(BookCopy bookCopy) {
        String sql = "UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?";
        template.update(sql, bookCopy.getNoOfCopies(), bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId());
    }

    public void deleteBookCopy(BookCopy bookCopy) {
        String sql = "DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?";
        template.update(sql, bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId());
    }

    public BookCopy getBookCopyWithId(BookCopy bookCopy) {
        String sql = "SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?";
        List<BookCopy> bookCopies = template.query(sql, new Object[]{bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId()}, this);
        if (bookCopies != null) {
            return bookCopies.get(0);
        }
        return null;
    }

    public List<BookCopy> getAllBookCopies(String searchString, Branch branch) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_book_copies bc inner join tbl_book b on bc.bookId = b.bookId where b.title like ? AND branchId = ?";
            return template.query(sql, new Object[]{searchString, branch.getBranchId()}, this);
        } else {
            String sql = "select * from tbl_book_copies bc inner join tbl_book b on bc.bookId = b.bookId WHERE branchId = ? ";
            return template.query(sql, new Object[]{branch.getBranchId()}, this);
        }
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_book_copies";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String title) {
        String sql = "SELECT count(*) FROM tbl_book_copies bc INNER JOIN tbl_book b ON bc.bookId = b.bookId WHERE b.title like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%"}, Integer.class);
    }

    @Override
    public List<BookCopy> extractData(ResultSet rs) {
        List<BookCopy> bookCopies = new ArrayList<BookCopy>();
        try {
            while (rs.next()) {
                BookCopy bc = new BookCopy();

                Book book = new Book();
                book.setBookId(rs.getInt("bookId"));

                Branch branch = new Branch();
                branch.setBranchId(rs.getInt("branchId"));

                bc.setBook(book);
                bc.setBranch(branch);
                bc.setNoOfCopies(rs.getInt("noOfCopies"));
                bookCopies.add(bc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookCopies;
    }
}
