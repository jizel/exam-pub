package cz.etyka.exam.pub.controller;


import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/users", method = GET)
    public Iterable<User> getUsers() {
        return service.getUsers();
    }

    @RequestMapping(value = "/user/{id}", method = GET)
    public User getUserDetails(@PathVariable long id) {
        return service.getUser(id);
    }

}
