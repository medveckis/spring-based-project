package io.medveckis.borrow.management.repository;

import io.medveckis.borrow.management.model.BookRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRecordRepository extends CrudRepository<BookRecord, Integer> {

    List<BookRecord> findAllBookRecordsByUserId(Integer userId, Sort sort);
}
