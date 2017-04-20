package com.kailiang.gcitlms.bean;

import java.util.List;

/**
 * Created by kailiang on 2017/1/5.
 */
public class Publisher {
    private Integer publisherId;
    private String publisherName;
    private String publisherAddress;
    private String publisherPhone;
    private List<Book> books;

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;

        if (publisherId != null ? !publisherId.equals(publisher.publisherId) : publisher.publisherId != null)
            return false;
        if (publisherName != null ? !publisherName.equals(publisher.publisherName) : publisher.publisherName != null)
            return false;
        if (publisherAddress != null ? !publisherAddress.equals(publisher.publisherAddress) : publisher.publisherAddress != null)
            return false;
        return publisherPhone != null ? publisherPhone.equals(publisher.publisherPhone) : publisher.publisherPhone == null;
    }

    @Override
    public int hashCode() {
        int result = publisherId != null ? publisherId.hashCode() : 0;
        result = 31 * result + (publisherName != null ? publisherName.hashCode() : 0);
        result = 31 * result + (publisherAddress != null ? publisherAddress.hashCode() : 0);
        result = 31 * result + (publisherPhone != null ? publisherPhone.hashCode() : 0);
        return result;
    }
}
