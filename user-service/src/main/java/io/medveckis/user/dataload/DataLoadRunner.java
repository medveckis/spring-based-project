package io.medveckis.user.dataload;

import io.medveckis.user.model.Role;
import io.medveckis.user.model.User;
import io.medveckis.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@Component
public class DataLoadRunner implements CommandLineRunner {
    private final UserRepository userRepository;

    @Autowired
    public DataLoadRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // create users
        IntStream.range(0, 4).forEach(idx -> userRepository.save(createTestUser(idx)));
    }

    private User createTestUser(int idx) {
        String firstName = "firstName_" + idx;
        String lastName = "lastName_" + idx;
        int age = idx + 1;
        String email = "example" + idx + "@mail.com";
        return new User(firstName, lastName, age, email, 2, age == 4 ? Role.ADMIN : Role.CUSTOMER);
    }
}

