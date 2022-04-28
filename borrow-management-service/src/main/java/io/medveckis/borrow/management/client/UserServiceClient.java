package io.medveckis.borrow.management.client;

import feign.Headers;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.medveckis.borrow.management.client.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Headers("Content-Type: application/json")
@FeignClient(value = "user-service")
public interface UserServiceClient {
    Logger LOG = LoggerFactory.getLogger(UserServiceClient.class);


    @GetMapping(value = "users/{userId}")
    @CircuitBreaker(name = "userCircuitBreaker", fallbackMethod = "getUserByIdFallbackMethod")
    UserResponse getUserById(@PathVariable(value = "userId") Integer userId);

    default UserResponse getUserByIdFallbackMethod(Integer userId, Exception e) {
        LOG.error("Error during call to user-service (getUserById)");
        return new UserResponse(userId, "Unknown", "Unknown", -1, "Unknown", 0, "Unknown");
    }
}
