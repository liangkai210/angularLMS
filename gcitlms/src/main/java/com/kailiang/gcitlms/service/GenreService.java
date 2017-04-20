package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.Genre;
import com.kailiang.gcitlms.dao.BookDao;
import com.kailiang.gcitlms.dao.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class GenreService {

    @Autowired
    GenreDao genreDao;
    @Autowired
    BookDao bookDao;

    @Transactional
    @RequestMapping(value = "/addGenre", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<Genre> addGenre(@RequestBody Genre genre) {
        genreDao.addGenre(genre);
        return getGenres();
    }

    @Transactional
    @RequestMapping(value = "/updateGenre", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public List<Genre> updateGenre(@RequestBody Genre genre) {
        genreDao.updateGenre(genre);
        genreDao.unRelateGenre(genre);
        genreDao.relateGenre(genre);
        return getGenres();
    }

    @RequestMapping(value = "/deleteGenre/{genre_id}", method = RequestMethod.DELETE, produces = "application/json")
    public List<Genre> deleteAuthor(@PathVariable Integer genre_id) {
        Genre genre = new Genre();
        genre.setGenre_id(genre_id);
        genreDao.deleteGenre(genre);
        return getGenres();
    }

    @RequestMapping(value = "/getSingleGenre/{genre_id}", method = RequestMethod.GET, produces = "application/json")
    public Genre getSingleGenre(@PathVariable Integer genre_id) {
        Genre genre = new Genre();
        genre.setGenre_id(genre_id);
        if (genre_id == 0) {
            return genre;
        }
        genre = genreDao.getGenreByPK(genre);
        fillGenre(genre);
        return genre;
    }

    @RequestMapping(value = "/searchGenres", method = RequestMethod.GET, produces = "application/json")
    public List<Genre> getGenreByName(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Genre> genres = genreDao.getAllGenres(searchString);
        for (Genre genre : genres) {
            fillGenre(genre);
        }
        return genres;
    }

    private void fillGenre(Genre genre) {
        List<Book> books = bookDao.getBooksFromGenre(genre);
        genre.setBooks(books);
    }

    private List<Genre> getGenres() {
        List<Genre> genres = genreDao.getAllGenres(null);
        genres.stream().forEach(item -> fillGenre(item));
        return genres;
    }
}
