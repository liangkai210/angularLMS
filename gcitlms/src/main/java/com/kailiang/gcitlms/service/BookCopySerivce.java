package com.kailiang.gcitlms.service;


import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.BookCopy;
import com.kailiang.gcitlms.bean.Branch;
import com.kailiang.gcitlms.dao.BookCopyDao;
import com.kailiang.gcitlms.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class BookCopySerivce {

    @Autowired
    BookCopyDao bcDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    BookService bookService;

    @RequestMapping(value = "/addBookCopy", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<Book> addBookCopy(@RequestBody BookCopy bookCopy) {
        bcDao.addBookCopy(bookCopy);
        Branch branch = new Branch();
        branch.setBranchId(bookCopy.getBranch().getBranchId());
        List<Book> books = bookDao.getBookCopiesNotInBranch(branch);
        for (Book book : books) {
            bookService.fillBook(book);
        }
        return books;
    }

    @Transactional
    @RequestMapping(value = "/updateBookCopy", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<BookCopy> updateBookCopy(@RequestBody BookCopy bookCopy) {
        bcDao.updateBookCopy(bookCopy);
        return getBookCopies(bookCopy);
    }

    @RequestMapping(value = "/getBookCopiesInBranch/{branchId}", method = RequestMethod.GET, produces = "application/json")
    public List<BookCopy> getBookCopiesByName(@RequestParam(value = "searchString", required = false) String searchString, @PathVariable Integer branchId) {
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        List<BookCopy> bookCopies = bcDao.getAllBookCopies(searchString, branch);
        for (BookCopy bookCopy : bookCopies) {
            fillBookCopy(bookCopy);
        }
        return bookCopies;
    }

    private void fillBookCopy(BookCopy bookCopy) {
        Book book = bookDao.getBookWithId(bookCopy.getBook());
        bookService.fillBook(book);
        bookCopy.setBook(book);
    }

    private List<BookCopy> getBookCopies(BookCopy bookCopy) {
        List<BookCopy> bookCopies = bcDao.getAllBookCopies(null, bookCopy.getBranch());
        bookCopies.stream().forEach(item -> fillBookCopy(item));
        return bookCopies;
    }
}
