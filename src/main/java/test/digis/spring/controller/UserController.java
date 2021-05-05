package test.digis.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.digis.spring.model.User;
import test.digis.spring.service.UserService;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{login}")
    public User getByLogin(@PathVariable String login) {
        return userService.getByLogin(login);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }
}
