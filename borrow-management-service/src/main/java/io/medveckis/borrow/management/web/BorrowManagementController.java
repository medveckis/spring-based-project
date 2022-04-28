package io.medveckis.borrow.management.web;

import io.medveckis.borrow.management.service.BorrowManagementService;
import io.medveckis.borrow.management.web.dto.BookData;
import io.medveckis.borrow.management.web.dto.BookRecordData;
import io.medveckis.borrow.management.web.dto.UserData;
import io.medveckis.borrow.management.web.form.BookRecordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/borrow-management")
public class BorrowManagementController {

    private final BorrowManagementService borrowManagementService;

    @Autowired
    public BorrowManagementController(BorrowManagementService borrowManagementService) {
        this.borrowManagementService = borrowManagementService;
    }

    @GetMapping(value = "/records")
    public ResponseEntity<List<BookRecordData>> getAllRecordsByUser(@RequestParam(name = "userId") Integer userId) {
        return ResponseEntity.ok(borrowManagementService.getAllRecordsByUserId(userId));
    }

    @PostMapping(value = "/records")
    public ResponseEntity<?> createRecord(HttpServletRequest request, @RequestBody BookRecordForm bookRecordForm) {
        return ResponseEntity.created(URI.create(request.getRequestURL().toString()
                + "/" + borrowManagementService.createRecord(convert(bookRecordForm)))).build();
    }

    private BookRecordData convert(BookRecordForm bookRecordForm) {
        BookRecordData bookRecordData = new BookRecordData();
        UserData userData = new UserData();
        userData.setId(bookRecordForm.getUserId());
        bookRecordData.setUser(userData);
        BookData bookData = new BookData();
        bookData.setId(bookRecordForm.getBookId());
        bookRecordData.setBook(bookData);
        return bookRecordData;
    }
}
