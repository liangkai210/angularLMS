package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Book;
import com.kailiang.gcitlms.bean.Publisher;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PubDao extends BaseDao<Publisher> implements ResultSetExtractor<List<Publisher>> {
    public void addPub(Publisher publisher) {
        template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)",
                new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
    }

    public void updatePublisher(Publisher publisher) {
        template.update("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
                new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
    }


    public void deletePublisher(Publisher publisher) {
        template.update("delete from tbl_publisher where publisherId = ?", new Object[]{publisher.getPublisherId()});
    }

    public List<Publisher> getAllPubs(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_publisher where publisherName like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_publisher";
            return template.query(sql, this);
        }
    }

    public Integer getPubsCount() {
        return template.queryForObject("select count(*) AS COUNT from tbl_publisher", Integer.class);
    }

    public Integer getPubsCountLike(String pubName) {
        String sql = "SELECT count(*) FROM tbl_genre WHERE publisherName like ?";
        return template.queryForObject(sql, new Object[]{"%" + pubName + "%"}, Integer.class);
    }

    public Publisher getPubByPK(Publisher publisher) {
        List<Publisher> publishers = template.query("select * from tbl_publisher where publisherId = ?",
                new Object[]{publisher.getPublisherId()}, this);
        if (publishers != null) {
            return publishers.get(0);
        }
        return null;
    }

    public Publisher getPubsFromBook(Book book) {
        List<Publisher> pubs = template.query("SELECT * FROM tbl_publisher WHERE publisherId = ?",
                new Object[]{book.getPublisher().getPublisherId()}, this);
        if (pubs != null && !pubs.isEmpty()) {
            return pubs.get(0);
        }
        return null;
    }


    @Override
    public List<Publisher> extractData(ResultSet rs) {
        List<Publisher> publishers = new ArrayList<Publisher>();
        try {
            while (rs.next()) {
                Publisher p = new Publisher();
                p.setPublisherId(rs.getInt("publisherId"));
                p.setPublisherName(rs.getString("publisherName"));
                p.setPublisherAddress(rs.getString("publisherAddress"));
                p.setPublisherPhone(rs.getString("publisherPhone"));
                publishers.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }
}
