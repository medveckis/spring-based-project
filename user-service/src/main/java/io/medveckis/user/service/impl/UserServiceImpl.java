package io.medveckis.user.service.impl;

import io.medveckis.user.model.User;
import io.medveckis.user.repository.UserRepository;
import io.medveckis.user.service.UserService;
import io.medveckis.user.web.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Integer createUser(UserData userData) {
        return userRepository.save(convert(userData)).getId();
    }

    @Override
    @Nullable
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    private User convert(UserData userData) {
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setAge(userData.getAge());
        user.setRole(userData.getRole());
        user.setLoyaltyLevel(1);
        return user;
    }
}
