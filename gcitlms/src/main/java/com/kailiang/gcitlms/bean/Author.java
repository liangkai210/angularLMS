package com.kailiang.gcitlms.bean;

import java.util.List;

public class Author {
    private Integer authorId;
    private String authorName;
    List<Book> books;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

        Author author = (Author) o;

        if (authorId != null ? !authorId.equals(author.authorId) : author.authorId != null) return false;
        if (authorName != null ? !authorName.equals(author.authorName) : author.authorName != null) return false;
        return books != null ? books.equals(author.books) : author.books == null;
    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}
