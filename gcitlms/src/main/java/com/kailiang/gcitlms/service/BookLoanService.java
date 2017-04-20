package com.kailiang.gcitlms.service;


import com.kailiang.gcitlms.bean.*;
import com.kailiang.gcitlms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class BookLoanService {

    @Autowired
    BookLoanDao bookLoanDao;
    @Autowired
    BookCopyDao bookCopyDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    BranchDao branchDao;
    @Autowired
    BorrowerDao borrowerDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    GenreDao genreDao;
    @Autowired
    PubDao pubDao;
    @Autowired
    BookService bookService;


    @Transactional
    @RequestMapping(value = "/loanBook", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<Book> loanBook(@RequestBody BookLoan bookLoan) {
        Borrower borrower = new Borrower();
        borrower.setCardNo(bookLoan.getBorrower().getCardNo());
        bookLoan.setBorrower(borrowerDao.getBorrowerWithId(borrower));
        BookCopy bookCopy = new BookCopy();
        bookCopy.setNoOfCopies(getNewBookCopies(bookLoan, bookCopy) - 1);
        bookCopyDao.updateBookCopy(bookCopy);
        bookLoanDao.addBookLoan(bookLoan);
        System.out.print("!!!!!!! :" + bookLoan.getDateOut());
        return bookService.getPossLoanFromBranch(null, bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo());
    }

    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST, consumes = "application/json")
    public void returnBook(@RequestBody BookLoan bookLoan) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setNoOfCopies(getNewBookCopies(bookLoan, bookCopy) + 1);
        bookCopyDao.updateBookCopy(bookCopy);
        bookLoanDao.deleteBookLoan(bookLoan);
    }

    private Integer getNewBookCopies(BookLoan bookLoan, BookCopy bookCopy) {
        bookCopy.setBook(bookLoan.getBook());
        bookCopy.setBranch(bookLoan.getBranch());
        BookCopy bookCopyTemp = bookCopyDao.getBookCopyWithId(bookCopy);
        if (bookCopyTemp != null) {
            return bookCopyTemp.getNoOfCopies();
        }
        return -1;
    }

    @Transactional
    @RequestMapping(value = "/updateBookLoan", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<BookLoan> updateBookLoan(@RequestBody BookLoan bookLoan) {
        bookLoan.setDueDate(new Date(bookLoan.getDueDate().getTime() + 24 * 3600 * 1000));
        bookLoanDao.updateBookLoan(bookLoan);
        List<BookLoan> bookLoans = bookLoanDao.getLoansFromBorrower(null, bookLoan.getBorrower());
        for (BookLoan bl : bookLoans) {
            fillLoans(bl);
        }
        return bookLoans;
    }

    @RequestMapping(value = "/deleteBookLoan", method = RequestMethod.DELETE, consumes = "application/json")
    public void deleteBookLoan(@RequestBody BookLoan bookLoan) {
        bookLoanDao.deleteBookLoan(bookLoan);
    }

    @RequestMapping(value = "/getSingleBookLoan/{bookId}/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
    public BookLoan getSingleBookLoan(@PathVariable Integer bookId, @PathVariable Integer branchId, @PathVariable Integer cardNo) {
        Book book = new Book();
        book.setBookId(bookId);
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBook(book);
        bookLoan.setBranch(branch);
        bookLoan.setBorrower(borrower);
        bookLoan = bookLoanDao.getLoanById(bookLoan);
        fillLoans(bookLoan);
        return bookLoan;
    }

    @RequestMapping(value = "/loansFromBorrower/{cardNo}", method = RequestMethod.GET, produces = "application/json")
    public List<BookLoan> getLoansFromBorrower(@RequestParam(value = "searchString", required = false) String searchString, @PathVariable Integer cardNo) {
        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);
        List<BookLoan> bookLoans = bookLoanDao.getLoansFromBorrower(searchString, borrower);
        for (BookLoan bookLoan : bookLoans) {
            fillLoans(bookLoan);
        }
        return bookLoans;
    }

    @RequestMapping(value = "/loansFromBorInBranch/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
    public List<BookLoan> getLoansFromBorInBranch(@RequestParam(value = "searchString", required = false) String searchString, @PathVariable Integer branchId, @PathVariable Integer cardNo) {
        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        List<BookLoan> bookLoans = bookLoanDao.getAllBookLoans(searchString, branch, borrower);
        for (BookLoan bookLoan : bookLoans) {
            fillLoans(bookLoan);
        }
        return bookLoans;
    }

    private void fillLoans(BookLoan bookLoan) {
        Book book = bookDao.getBookWithId(bookLoan.getBook());
        bookService.fillBook(book);
        Branch branch = branchDao.getBranchWithId(bookLoan.getBranch());
        Borrower borrower = borrowerDao.getBorrowerWithId(bookLoan.getBorrower());
        bookLoan.setBook(book);
        bookLoan.setBranch(branch);
        bookLoan.setBorrower(borrower);
    }

}
