package cz.etyka.exam.pub.controller;


import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@EnableWebSecurity
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/users", method = GET)
    public Iterable<User> getUsers() {
        return service.getUsers();
    }

    @RequestMapping(value = "/user/{id}", method = GET)
    public User getUserDetails(@PathVariable long id) {
        try {
            return service.getUser(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found: " + e.getLocalizedMessage(), e);
        }
    }

}
