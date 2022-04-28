package io.medveckis.user.service;

import io.medveckis.user.model.User;
import io.medveckis.user.web.dto.UserData;

public interface UserService {
    Integer createUser(UserData userData);
    User getUserById(Integer id);
}

