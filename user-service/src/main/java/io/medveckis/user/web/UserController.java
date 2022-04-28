package io.medveckis.user.web;

import io.medveckis.user.model.Role;
import io.medveckis.user.service.UserService;
import io.medveckis.user.web.dto.UserData;
import io.medveckis.user.web.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody UserForm userForm) {
        return ResponseEntity.created(URI.create(request.getRequestURL().toString()
                + "/" + userService.createUser(convert(userForm)))).build();
    }

    @GetMapping(value = "/{userId}")
    public UserData getUserById(@PathVariable(value = "userId") Integer userId) {
        return Optional.ofNullable(userService.getUserById(userId))
                .map(user -> new UserData(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getLoyaltyLevel(), user.getRole()))
                .orElse(new UserData());
    }

    private UserData convert(UserForm userForm) {
        UserData userData = new UserData();
        userData.setFirstName(userForm.getFirstName());
        userData.setLastName(userForm.getLastName());
        userData.setEmail(userForm.getEmail());
        userData.setAge(userForm.getAge());
        userData.setRole(Role.CUSTOMER);
        return userData;
    }
}