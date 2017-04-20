package com.kailiang.gcitlms.bean;

import java.util.List;

public class Genre {
    private Integer genre_id;
    private String genre_name;
    List<Book> books;

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
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

        Genre genre = (Genre) o;

        if (genre_id != null ? !genre_id.equals(genre.genre_id) : genre.genre_id != null) return false;
        if (genre_name != null ? !genre_name.equals(genre.genre_name) : genre.genre_name != null) return false;
        return books != null ? books.equals(genre.books) : genre.books == null;
    }

    @Override
    public int hashCode() {
        int result = genre_id != null ? genre_id.hashCode() : 0;
        result = 31 * result + (genre_name != null ? genre_name.hashCode() : 0);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}
