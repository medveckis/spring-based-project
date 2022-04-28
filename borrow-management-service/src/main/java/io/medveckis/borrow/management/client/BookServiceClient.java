package io.medveckis.borrow.management.client;

import feign.Headers;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.medveckis.borrow.management.client.response.BookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Headers("Content-Type: application/json")
@FeignClient(value = "book-service")
public interface BookServiceClient {
    Logger LOG = LoggerFactory.getLogger(BookServiceClient.class);

    @GetMapping(value = "books/{bookId}")
    @CircuitBreaker(name = "bookCircuitBreaker", fallbackMethod = "getBookByIdFallbackMethod")
    BookResponse getBookById(@PathVariable(value = "bookId") Integer bookId);

    default BookResponse getBookByIdFallbackMethod(Integer id, Exception e) {
        LOG.error("Error during call to book-service (getBookById)");
        return new BookResponse(id, -1., "Unknown name", -1);
    }
}
