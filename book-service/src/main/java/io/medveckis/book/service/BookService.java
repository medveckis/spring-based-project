package io.medveckis.book.service;

import io.medveckis.book.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooksByCategories(List<Integer> categoryIds);
    Book getBookById(Integer bookId);
}