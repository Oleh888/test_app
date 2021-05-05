package test.digis.spring.service;

import test.digis.spring.model.User;

public interface UserService {

    User getByLogin(String login);

    User create(User user);

    User update(User user);
}
