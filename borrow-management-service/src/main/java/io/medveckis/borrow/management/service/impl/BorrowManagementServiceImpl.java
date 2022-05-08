package io.medveckis.borrow.management.service.impl;

import io.medveckis.borrow.management.client.BookServiceClient;
import io.medveckis.borrow.management.client.UserServiceClient;
import io.medveckis.borrow.management.client.response.BookResponse;
import io.medveckis.borrow.management.client.response.UserResponse;
import io.medveckis.borrow.management.model.BookRecord;
import io.medveckis.borrow.management.repository.BookRecordRepository;
import io.medveckis.borrow.management.service.BorrowManagementService;
import io.medveckis.borrow.management.web.dto.BookData;
import io.medveckis.borrow.management.web.dto.BookRecordData;
import io.medveckis.borrow.management.web.dto.UserData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowManagementServiceImpl implements BorrowManagementService {

    private final BookRecordRepository bookRecordRepository;
    private final BookServiceClient bookServiceClient;
    private final UserServiceClient userServiceClient;

    @Autowired
    public BorrowManagementServiceImpl(BookRecordRepository bookRecordRepository, BookServiceClient bookServiceClient, UserServiceClient userServiceClient) {
        this.bookRecordRepository = bookRecordRepository;
        this.bookServiceClient = bookServiceClient;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Integer createRecord(BookRecordData bookRecordData) {
        return bookRecordRepository.save(convert(bookRecordData,
                bookServiceClient.getBookById(bookRecordData.getBook().getId()).getBookFee(),
                userServiceClient.getUserById(bookRecordData.getUser().getId()).getLoyaltyLevel())).getId();
    }

    @Override
    public List<BookRecordData> getAllRecordsByUserId(Integer userId) {
        return bookRecordRepository.findAllBookRecordsByUserId(userId, Sort.by("id"))
                .stream()
                .map(this::convertToData)
                .collect(Collectors.toList());
    }

    private BookRecord convert(BookRecordData bookRecordData, Double bookFee, Integer loyaltyLevel) {
        BookRecord bookRecord = new BookRecord();
        bookRecord.setBookId(bookRecordData.getBook().getId());
        bookRecord.setUserId(bookRecordData.getUser().getId());
        bookRecord.setExpirationDate(DateTime.now().plusMonths(3).toDate());
        bookRecord.setFee(loyaltyLevel == 0 ? bookFee : bookFee - (bookFee * 0.1 * loyaltyLevel));
        return bookRecord;
    }

    private BookRecordData convertToData(BookRecord bookRecord) {
        BookRecordData bookRecordData = new BookRecordData();

        UserData userData = new UserData();
        UserResponse userResponse = userServiceClient.getUserById(bookRecord.getUserId());
        userData.setId(userResponse.getUserId());
        userData.setFirstName(userResponse.getFirstName());
        userData.setLastName(userResponse.getLastName());
        userData.setEmail(userResponse.getEmail());
        userData.setAge(userResponse.getAge());

        bookRecordData.setUser(userData);

        BookResponse bookResponse = bookServiceClient.getBookById(bookRecord.getBookId());
        BookData bookData = new BookData();
        bookData.setId(bookResponse.getId());
        bookData.setName(bookResponse.getName());

        bookRecordData.setBook(bookData);

        bookRecordData.setExpirationDate(bookRecord.getExpirationDate());
        bookRecordData.setFee(bookRecord.getFee());
        bookRecordData.setCompleted(bookRecord.getCompleted());

        return bookRecordData;
    }
}
