package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Author;
import com.kailiang.gcitlms.bean.Book;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao extends BaseDao<Author> implements ResultSetExtractor<List<Author>> {

    public void addAuthor(Author author) {
        template.update("insert into tbl_author (authorName) values (?)", new Object[]{author.getAuthorName()});
    }

    public void updateAuthor(Author author) {
        template.update("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
    }


    public void deleteAuthor(Author author) {
        template.update("delete from tbl_author where authorId = ?", new Object[]{author.getAuthorId()});
    }

    public void relateAuthor(Author author) {
        for (Book book : author.getBooks()) {
            template.update("INSERT INTO tbl_book_authors(bookId, authorId) VALUES(?, ?)",
                    new Object[]{book.getBookId(), author.getAuthorId()});
        }
    }

    public void unRelateAuthor(Author author) {
        for (Book book : author.getBooks()) {
            template.update("DELETE FROM tbl_book_authors WHERE authorId = ?", new Object[]{author.getAuthorId()});
        }
    }

    public List<Author> getAllAuthors(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_author where authorName like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_author";
            return template.query(sql, this);
        }
    }

    public Integer getAuthorsCountLike(String authorName) {
        String sql = "SELECT count(*) FROM tbl_author WHERE authorName like ?";
        return template.queryForObject(sql, new Object[]{"%" + authorName + "%"}, Integer.class);
    }

    public Integer getAuthorsCount() {
        return template.queryForObject("select count(*) AS COUNT from tbl_author", Integer.class);
    }

    public Author getAuthorByPK(Author author) {
        List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[]{author.getAuthorId()}, this);
        if (authors != null) {
            return authors.get(0);
        }
        return null;
    }

    public List<Author> getAuthorsFromBook(Book book) {
        return template.query("SELECT * FROM tbl_author a " +
                        "INNER JOIN tbl_book_authors ba ON a.authorId = ba.authorId WHERE ba.bookId = ?",
                new Object[]{book.getBookId()}, this);
    }


    @Override
    public List<Author> extractData(ResultSet rs) {
        List<Author> authors = new ArrayList<Author>();
        try {
            while (rs.next()) {
                Author a = new Author();
                a.setAuthorId(rs.getInt("authorId"));
                a.setAuthorName(rs.getString("authorName"));
                authors.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
