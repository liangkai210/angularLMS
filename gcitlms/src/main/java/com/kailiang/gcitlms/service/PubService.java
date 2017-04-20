package com.kailiang.gcitlms.service;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.Publisher;
import com.kailiang.gcitlms.dao.BookDao;
import com.kailiang.gcitlms.dao.PubDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class PubService {

    @Autowired
    PubDao pubDao;
    @Autowired
    BookDao bookDao;

    @Transactional
    @RequestMapping(value = "/addPub", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<Publisher> addPub(@RequestBody Publisher publisher) {
        pubDao.addPub(publisher);
        return getPublishers();
    }

    @Transactional
    @RequestMapping(value = "/updatePub", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public List<Publisher> updatePub(@RequestBody Publisher publisher) {
        pubDao.updatePublisher(publisher);
        return getPublishers();
    }

    @RequestMapping(value = "/deletePub/{publisherId}", method = RequestMethod.DELETE, produces = "application/json")
    public List<Publisher> deletePub(@PathVariable Integer publisherId) {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherId);
        pubDao.deletePublisher(publisher);
        return getPublishers();
    }

    @RequestMapping(value = "/getSinglePub/{publisherId}", method = RequestMethod.GET, produces = "application/json")
    public Publisher getSinglePub(@PathVariable Integer publisherId) {
        Publisher pub = new Publisher();
        pub.setPublisherId(publisherId);
        if(publisherId == 0){
            return pub;
        }
        pub = pubDao.getPubByPK(pub);
        fillPublisher(pub);
        return pub;
    }

    @RequestMapping(value = "/searchPubs", method = RequestMethod.GET, produces = "application/json")
    public List<Publisher> getPubByName(@RequestParam(value = "searchString", required = false) String searchString) {
        List<Publisher> publishers = pubDao.getAllPubs(searchString);
        for (Publisher publisher : publishers) {
            fillPublisher(publisher);
        }
        return publishers;
    }

    private void fillPublisher(Publisher publisher) {
        List<Book> books = bookDao.getBooksFromPub(publisher);
        publisher.setBooks(books);
    }

    private List<Publisher> getPublishers() {
        List<Publisher> publishers = pubDao.getAllPubs(null);
        publishers.stream().forEach(item -> fillPublisher(item));
        return publishers;
    }

}
