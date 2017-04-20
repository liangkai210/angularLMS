package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.Author;
import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.dao.AuthorDao;
import com.kailiang.gcitlms.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class AuthorService {

    @Autowired
    AuthorDao authorDao;
    @Autowired
    BookDao bookDao;

    @Transactional
    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public List<Author> addAuthor(@RequestBody Author author) {
        authorDao.addAuthor(author);
        return getAuthors();
    }

    @Transactional
    @RequestMapping(value = "/updateAuthor", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public List<Author> updateAuthor(@RequestBody Author author) {
        authorDao.updateAuthor(author);
        authorDao.unRelateAuthor(author);
        authorDao.relateAuthor(author);
        return getAuthors();
    }

    @RequestMapping(value = "/deleteAuthor/{authorId}", method = RequestMethod.DELETE, produces = "application/json")
    public List<Author> deleteAuthor(@PathVariable Integer authorId) {
        Author author = new Author();
        author.setAuthorId(authorId);
        authorDao.deleteAuthor(author);
        return getAuthors();
    }

    @RequestMapping(value = "/getBooks", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getBooks() {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookDao.getAllBooks(null);
        map.put("books", books);
        return map;
    }


    @RequestMapping(value = "/getSingleAuthor/{authorId}", method = RequestMethod.GET, produces = "application/json")
    public Author getSingleAuthor(@PathVariable Integer authorId) {
        Author author = new Author();
        author.setAuthorId(authorId);
        if (authorId == 0) {
            return author;
        }
        author = authorDao.getAuthorByPK(author);
        fillAuthor(author);
        return author;
    }

    @RequestMapping(value = "/searchAuthors", method = RequestMethod.GET, produces = "application/json")
    public List<Author> getAuthorByName(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Author> authors = authorDao.getAllAuthors(searchString);
        for (Author author : authors) {
            fillAuthor(author);
        }
        return authors;
    }

    private void fillAuthor(Author author) {
        List<Book> books = bookDao.getBooksFromAuthor(author);
        author.setBooks(books);
    }

    private List<Author> getAuthors() {
        List<Author> authors = authorDao.getAllAuthors(null);
        authors.stream().forEach(item -> fillAuthor(item));
        return authors;
    }
}
