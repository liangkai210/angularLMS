package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.*;
import com.kailiang.gcitlms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class BookService {

    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    GenreDao genreDao;
    @Autowired
    PubDao pubDao;
    @Autowired
    BookLoanDao bookLoanDao;

    @Transactional
    @RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public List<Book> addBook(@RequestBody Book book) {
        Integer bookId = bookDao.addBookWithID(book);
        book.setBookId(bookId);
        bookDao.relateBook(book);
        return getBooks();
    }

    @Transactional
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public List<Book> updateBook(@RequestBody Book book) {
        bookDao.updateBook(book);
        bookDao.unRelateBook(book);
        bookDao.relateBook(book);
        return getBooks();
    }

    @RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.POST)
    public List<Book> deleteBook(@PathVariable Integer bookId) {
        Book book = new Book();
        book.setBookId(bookId);
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBook(book);
        if (bookLoanDao.getLoanByBookId(bookLoan) == null) {
            bookDao.deleteBook(book);
        }
        return getBooks();
    }

    @RequestMapping(value = "/getAllInfos", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAllInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("publishers", pubDao.getAllPubs(null));
        map.put("authors", authorDao.getAllAuthors(null));
        map.put("genres", genreDao.getAllGenres(null));
        return map;
    }

    @RequestMapping(value = "/possLoan/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
    public List<Book> getPossLoanFromBranch(@RequestParam(value = "searchString", required = false) String searchString, @PathVariable Integer branchId, @PathVariable Integer cardNo) {
        Borrower borrower = new Borrower();
        System.out.println("cardNo is : "+ cardNo);
        borrower.setCardNo(cardNo);
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        List<Book> books = bookDao.getPossLoansFromBranch(searchString, branch, borrower);
        for (Book book : books) {
            fillBook(book);
        }
        return books;
    }

    @RequestMapping(value = "/bookNoInBranch/{branchId}")
    public List<Book> getBooksNotInBranch(@PathVariable Integer branchId) {
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        return bookDao.getBookNotInBranch(branch);
    }

    @RequestMapping(value = "/getSingleBook/{bookId}", method = RequestMethod.GET, produces = "application/json")
    public Book getSingleBook(@PathVariable Integer bookId) {
        Book book = new Book();
        book.setBookId(bookId);
        if (bookId == 0) {
            return book;
        }
        book = bookDao.getBookWithId(book);
        fillBook(book);
        return book;
    }

    @RequestMapping(value = "/searchBooks", method = RequestMethod.GET, produces = "application/json")
    public List<Book> getBookByTitle(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Book> books = bookDao.getAllBooks(searchString);
        for (Book book : books) {
            fillBook(book);
        }
        return books;
    }

    public void fillBook(Book book) {
        List<Author> authors = authorDao.getAuthorsFromBook(book);
        book.setAuthors(authors);
        List<Genre> genres = genreDao.getGenresFromBook(book);
        book.setGenres(genres);
        Publisher publisher = pubDao.getPubsFromBook(book);
        book.setPublisher(publisher);
    }

    private List<Book> getBooks() {
        List<Book> books = bookDao.getAllBooks(null);
        books.stream().forEach(item -> fillBook(item));
        return books;
    }
}
