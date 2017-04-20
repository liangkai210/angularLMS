package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.Borrower;
import com.kailiang.gcitlms.dao.BookLoanDao;
import com.kailiang.gcitlms.dao.BorrowerDao;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class BorrowerService {

    @Autowired
    BorrowerDao borrowerDao;
    @Autowired
    BookLoanDao blDao;

    @Transactional
    @RequestMapping(value = "/addBorrower", method = RequestMethod.POST, consumes = "application/json")
    public void addBorrower(@RequestBody Borrower borrower) {
        System.out.print("borrower: " + borrower.getUsername());
        borrowerDao.addBorrower(borrower);
    }

    @Transactional
    @RequestMapping(value = "/updateBorrower", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public List<Borrower> updateBorrower(@RequestBody Borrower borrower) {
        borrowerDao.updateBorrower(borrower);
        List<Borrower> borrowers = borrowerDao.getAllBorrowers(null);
        checkLoans(borrowers);
        return borrowers;
    }

    @RequestMapping(value = "/deleteBorrower/{cardNo}", method = RequestMethod.POST)
    public List<Borrower> deleteBorrower(@PathVariable Integer cardNo) {
        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);
        borrowerDao.deleteBorrower(borrower);
        return borrowerDao.getAllBorrowers(null);
    }

    @RequestMapping(value = "/validateBorrower/{username}", method = RequestMethod.GET, produces = "application/json")
    public boolean validateBorrower(@PathVariable String username) {
        System.out.print(username);
        return borrowerDao.validateBorrower(username);
    }

    @RequestMapping(value = "/logOff", method = RequestMethod.GET, produces = "application/json")
    public void logOff(HttpSession session){
        session.removeAttribute("cardNo");
    }

    @RequestMapping(value = "/loginBorrower/{username}/{password}", method = RequestMethod.POST, produces = "application/json")
    public Integer loginBorrower(@PathVariable String username, @PathVariable String password) {
        Borrower borrower = new Borrower();
        borrower.setUsername(username);
        borrower.setPassword(password);

        try {
            Integer cardNo = borrowerDao.loginBorrower(borrower);
            return cardNo;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return -1;
        }
    }

    @RequestMapping(value = "/getSingleBorrower/{cardNo}", method = RequestMethod.GET, produces = "application/json")
    public Borrower getSingleBorrower(@PathVariable Integer cardNo) {
        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);
        return borrowerDao.getBorrowerWithId(borrower);
    }

    @RequestMapping(value = "/searchBorrowers", method = RequestMethod.GET, produces = "application/json")
    public List<Borrower> getBorrowerByName(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Borrower> borrowers = borrowerDao.getAllBorrowers(searchString);
        checkLoans(borrowers);
        return borrowers;
    }

    private void checkLoans(List<Borrower> borrowers) {
        for (Borrower borrower : borrowers) {
            if (blDao.isHasLoan(borrower.getCardNo())) {
                borrower.setHasLoans(true);
            } else {
                borrower.setHasLoans(false);
            }
        }
    }
}
