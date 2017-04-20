package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.BookCopy;
import com.kailiang.gcitlms.bean.Branch;
import com.kailiang.gcitlms.dao.BookCopyDao;
import com.kailiang.gcitlms.dao.BookDao;
import com.kailiang.gcitlms.dao.BranchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class BranchService {

    @Autowired
    BranchDao branchDao;
    @Autowired
    BookCopyDao bookCopyDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    BookService bookService;

    @Transactional
    @RequestMapping(value = "/addBranch", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<Branch> addBranch(@RequestBody Branch branch) {
        branchDao.addBranch(branch);
        return getBranches();
    }

    @Transactional
    @RequestMapping(value = "/updateBranch", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public List<Branch> updateBranch(@RequestBody Branch branch) {
        branchDao.updateBranch(branch);
        return getBranches();
    }

    @RequestMapping(value = "/deleteBranch/{branchId}", method = RequestMethod.DELETE, produces = "application/json")
    public List<Branch> deleteBranch(@PathVariable Integer branchId) {
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        branchDao.deleteBranch(branch);
        return getBranches();
    }

    @RequestMapping(value = "/getSingleBranch/{branchId}", method = RequestMethod.GET, produces = "application/json")
    public Branch getSingleBranch(@PathVariable Integer branchId) {
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        if (branchId == 0) {
            return branch;
        }
        branch = branchDao.getBranchWithId(branch);
        fillBranch(branch);
        return branch;
    }

    @RequestMapping(value = "/getAvaiBookCopy/{branchId}", method = RequestMethod.GET, produces = "application/json")
    public List<Book> getAvaiBookCopy(@PathVariable Integer branchId) {
        System.out.print("branchId: " + branchId);
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        List<Book> books = bookDao.getBookCopiesNotInBranch(branch);
        for (Book book : books) {
            bookService.fillBook(book);
        }
        return books;
    }

    @RequestMapping(value = "/searchBranches", method = RequestMethod.GET, produces = "application/json")
    public List<Branch> getBranchByName(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Branch> branches = branchDao.getAllBranches(searchString);
        for (Branch branch : branches) {
            fillBranch(branch);
        }
        return branches;
    }

    private void fillBranch(Branch branch) {
        List<BookCopy> copies = bookCopyDao.getAllBookCopies(null, branch);
        for (BookCopy copy : copies) {
            this.fillCopy(copy);
        }
        branch.setBookCopies(copies);
    }

    private void fillCopy(BookCopy copy) {
        Book book = bookDao.getBookWithId(copy.getBook());
        bookService.fillBook(book);
        copy.setBook(book);
    }

    private List<Branch> getBranches() {
        List<Branch> branches = branchDao.getAllBranches(null);
        branches.stream().forEach(item -> fillBranch(item));
        return branches;
    }

}
