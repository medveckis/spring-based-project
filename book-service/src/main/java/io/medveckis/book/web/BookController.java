package io.medveckis.book.web;

import io.medveckis.book.service.BookService;
import io.medveckis.book.web.dto.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController(value = "bookController")
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookData> getAllBooksByCategoriesAndType(@RequestParam(name = "type", required = false, defaultValue = "ALL") String type,
                                                         @RequestParam(name = "categories") List<String> categories) {

        return bookService.getAllBooksByCategories(categories
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                )
                .stream()
                .map(book -> new BookData(book.getId(), book.getName(), book.getQuantity(), book.getBookFee()))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{bookId}")
    public BookData getBookById(@PathVariable(value = "bookId") Integer bookId) {
        return Stream.of(bookService.getBookById(bookId))
                .map(book -> new BookData(book.getId(), book.getName(), book.getQuantity(), book.getBookFee()))
                .findFirst()
                .orElseThrow();
    }

}
