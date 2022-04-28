package io.medveckis.book.service.impl;

import io.medveckis.book.model.Book;
import io.medveckis.book.repository.BookRepository;
import io.medveckis.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<Book> getAllBooksByCategories(List<Integer> categoryIds) {
        return bookRepository.findAllBooksByCategories(categoryIds, Sort.by("id"));
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow();
    }
}
