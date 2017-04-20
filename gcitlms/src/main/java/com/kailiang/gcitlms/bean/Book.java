package com.kailiang.gcitlms.bean;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


public class Book {
    private Integer bookId;
    private String title;
    private Publisher publisher;
    private List<Author> authors;
    private List<Genre> genres;

    public Book() {
        publisher = new Publisher();
        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != null ? !bookId.equals(book.bookId) : book.bookId != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (authors != null ? !authors.equals(book.authors) : book.authors != null) return false;
        return genres != null ? genres.equals(book.genres) : book.genres == null;
    }

    @Override
    public int hashCode() {
        int result = bookId != null ? bookId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }
}
