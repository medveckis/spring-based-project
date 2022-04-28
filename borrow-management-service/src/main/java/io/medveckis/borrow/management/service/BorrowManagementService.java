package io.medveckis.borrow.management.service;

import io.medveckis.borrow.management.web.dto.BookRecordData;

import java.util.List;

public interface BorrowManagementService {
    Integer createRecord(BookRecordData bookRecordData);
    List<BookRecordData> getAllRecordsByUserId(Integer userId);
}
