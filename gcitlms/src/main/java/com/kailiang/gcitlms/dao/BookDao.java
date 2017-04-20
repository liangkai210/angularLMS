package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.*;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends BaseDao<Book> implements ResultSetExtractor<List<Book>> {

    public void addBook(Book book) {
        String sql = "INSERT INTO tbl_book(title) VALUES (?)";
        template.update(sql, new Object[]{book.getTitle()});
    }


    public Integer addBookWithID(Book book) {

        final String title = book.getTitle();
        final String INSERT_SQL = "insert into tbl_book (title, pubId) values (?, ?)";
        checkBook(book);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"bookId"});
                ps.setString(1, title);
                ps.setObject(2, book.getPublisher().getPublisherId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void updateBook(Book book) {
        checkBook(book);
        template.update("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[]{book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});
    }

    public void deleteBook(Book book) {
        template.update("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
    }

    public Book getBookWithId(Book book) {
        List<Book> books = template.query("SELECT * FROM tbl_book WHERE bookId = ?", new Object[]{book.getBookId()}, this);
        if (books != null) {
            return books.get(0);
        }
        return null;
    }

    public List<Book> getAllBooks(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_book where title like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_book";
            return template.query(sql, this);
        }
    }

    public List<Book> getBooksFromAuthor(Author author) {
        String sql = "SELECT * FROM tbl_book b INNER JOIN tbl_book_authors ba ON b.bookId = ba.bookId WHERE ba.authorId = ?";
//        String sqlTemp = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId = ?)";
        return template.query(sql, new Object[]{author.getAuthorId()}, this);
    }

    public List<Book> getBooksFromGenre(Genre genre) {
        String sql = "SELECT * FROM tbl_book b INNER JOIN tbl_book_genres bg ON b.bookId = bg.bookId WHERE bg.genre_id = ?";
        return template.query(sql, new Object[]{genre.getGenre_id()}, this);
    }

    public List<Book> getBooksFromPub(Publisher publisher) {
        String sql = "SELECT * FROM tbl_book WHERE pubId = ?";
        return template.query(sql, new Object[]{publisher.getPublisherId()}, this);
    }

    public void relateBook(Book book) {

        for (Author author : book.getAuthors()) {
            template.update("INSERT INTO tbl_book_authors(bookId, authorId) VALUES(?, ?)",
                    new Object[]{book.getBookId(), author.getAuthorId()});

        }
        for (Genre genre : book.getGenres()) {
            template.update("INSERT INTO tbl_book_genres(bookId, genre_id) VALUES(?, ?)",
                    new Object[]{book.getBookId(), genre.getGenre_id()});
        }
    }

    public void unRelateBook(Book book) {
        for (Author author : book.getAuthors()) {
            template.update("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[]{book.getBookId()});
        }
        for (Genre genre : book.getGenres()) {
            template.update("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[]{book.getBookId()});
        }
    }

    public List<Book> getBookNotInBranch(Branch branch) {
        String sql = "SELECT * FROM tbl_book b WHERE b.bookId NOT IN(SELECT bc.bookId FROM tbl_book_copies bc WHERE bc.branchId = ?)";
        List<Book> books = template.query(sql, new Object[]{branch.getBranchId()}, this);
        if (books != null && !books.isEmpty()) {
            return books;
        }
        return null;

    }

    public List<Book> getPossLoansFromBranch(String searchString, Branch branch, Borrower borrower) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                    "ON tbl_book_copies.bookId = b.bookId INNER " +
                    "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                    "WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? " +
                    "AND b.bookId NOT IN(SELECT bl.bookId " +
                    "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
            return template.query(sql, new Object[]{searchString, branch.getBranchId(), borrower.getCardNo()}, this);
        } else {
            String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                    "ON tbl_book_copies.bookId = b.bookId INNER " +
                    "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                    "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? " +
                    "AND b.bookId NOT IN(SELECT bl.bookId " +
                    "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
            return template.query(sql, new Object[]{branch.getBranchId(), borrower.getCardNo()}, this);
        }
    }

    public List<Book> getBookCopiesNotInBranch(Branch branch) {
        String sql = "SELECT * FROM tbl_book b WHERE b.bookId NOT IN(SELECT bc.bookId FROM tbl_book_copies bc WHERE bc.branchId = ?)";
        List<Book> books = template.query(sql, new Object[]{branch.getBranchId()}, this);
        if (books != null && !books.isEmpty()) {
            return books;
        }
        return null;
    }

    public int getPossLoansCount(String branchId, String cardNo) {
        String sql = "SELECT count(*) FROM tbl_book b INNER JOIN tbl_book_copies " +
                "ON tbl_book_copies.bookId = b.bookId INNER " +
                "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                "AND b.bookId NOT IN(SELECT bl.bookId " +
                "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
        return template.queryForObject(sql, new Object[]{branchId, cardNo}, Integer.class);
    }

    public int getPossLoansCountbyLike(String title, String branchId, String cardNo) {
        String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                "ON tbl_book_copies.bookId = b.bookId INNER " +
                "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                "WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                "AND b.bookId NOT IN(SELECT bl.bookId " +
                "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
        return template.queryForObject(sql, new Object[]{"%" + title + "%", branchId, cardNo}, Integer.class);
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_book";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String title) {
        String sql = "SELECT count(*) FROM tbl_book WHERE title like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%"}, Integer.class);
    }


    @Override
    public List<Book> extractData(ResultSet rs) {
        List<Book> books = new ArrayList<Book>();
        try {
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("bookId"));
                b.setTitle(rs.getString("title"));
                Publisher publisher = new Publisher();
                publisher.setPublisherId(rs.getInt("pubId"));
                b.setPublisher(publisher);
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private void checkBook(Book book) {
        if (book.getTitle() != null && book.getTitle().trim().length() == 0) book.setTitle(null);
        if (book.getPublisher() == null) book.setPublisher(new Publisher());
    }

}
