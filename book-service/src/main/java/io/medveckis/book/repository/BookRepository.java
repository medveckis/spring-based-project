package io.medveckis.book.repository;

import io.medveckis.book.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query(value = "SELECT DISTINCT b FROM Book b JOIN b.categories c WHERE c.id IN :ids")
    List<Book> findAllBooksByCategories(@Param("ids") List<Integer> categoryIds, Sort sort);
}
