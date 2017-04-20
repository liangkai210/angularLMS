package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Author;
import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao extends BaseDao<Genre> implements ResultSetExtractor<List<Genre>> {

    public void addGenre(Genre genre) {
        String sql = "INSERT INTO tbl_genre(genre_name) VALUES (?)";
        template.update(sql, genre.getGenre_name());
    }

    public void updateGenre(Genre genre) {
        String sql = "UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?";
        template.update(sql, genre.getGenre_name(), genre.getGenre_id());
    }

    public void deleteGenre(Genre genre) {
        String sql = "DELETE FROM tbl_genre WHERE genre_id = ?";
        template.update(sql, genre.getGenre_id());
    }

    public void relateGenre(Genre genre) {
        for (Book book : genre.getBooks()) {
            template.update("INSERT INTO tbl_book_genres(bookId, genre_id) VALUES(?, ?)",
                    new Object[]{book.getBookId(), genre.getGenre_id()});
        }
    }

    public void unRelateGenre(Genre genre) {
        for (Book book : genre.getBooks()) {
            template.update("DELETE FROM tbl_book_genres WHERE genre_id = ?", new Object[]{genre.getGenre_id()});
        }
    }

    public List<Genre> getAllGenres(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_genre where genre_name like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_genre";
            return template.query(sql, this);
        }
    }

    public Integer getGenresCount() {
        return template.queryForObject("select count(*) AS COUNT from tbl_genre", Integer.class);
    }

    public Integer getGenresCountLike(String genreName) {
        String sql = "SELECT count(*) FROM tbl_genre WHERE genre_name like ?";
        return template.queryForObject(sql, new Object[]{"%" + genreName + "%"}, Integer.class);
    }

    public Genre getGenreByPK(Genre genre) {
        List<Genre> genres = template.query("select * from tbl_genre where genre_id = ?", new Object[]{genre.getGenre_id()}, this);
        if (genres != null) {
            return genres.get(0);
        }
        return null;
    }

    public List<Genre> getGenresFromBook(Book book) {
        return template.query("SELECT * FROM tbl_genre g " +
                        "INNER JOIN tbl_book_genres bg ON g.genre_id = bg.genre_id WHERE bg.bookId = ?",
                new Object[]{book.getBookId()}, this);
    }


    @Override
    public List<Genre> extractData(ResultSet rs) {
        List<Genre> genres = new ArrayList<Genre>();
        try {
            while (rs.next()) {
                Genre g = new Genre();
                g.setGenre_id(rs.getInt("genre_id"));
                g.setGenre_name(rs.getString("genre_name"));
                genres.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }
}
