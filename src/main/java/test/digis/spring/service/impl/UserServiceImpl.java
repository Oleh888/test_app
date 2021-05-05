package test.digis.spring.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.digis.spring.exception.UserAlreadyExistException;
import test.digis.spring.exception.UserNotFoundException;
import test.digis.spring.model.User;
import test.digis.spring.repository.UserRepository;
import test.digis.spring.service.UserService;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByLogin(String login) {
        return userRepository.findById(login)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with login %s was not found.", login);
                    log.error(errorMessage);
                    return new UserNotFoundException(errorMessage);
                });
    }

    @Transactional
    @Override
    public User create(User user) {
        if (isUserExist(user)) {
            String errorMessage = String.format("User with login %s already exist.", user.getLogin());
            log.error(errorMessage);
            throw new UserAlreadyExistException(errorMessage);
        }

        return userRepository.insert(user);
    }

    @Transactional
    @Override
    public User update(User user) {
        if (!isUserExist(user)) {
            String errorMessage = String.format("User with login %s was not found.", user.getLogin());
            log.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        return userRepository.save(user);
    }

    private boolean isUserExist(User user) {
        return userRepository.existsById(user.getLogin());
    }
}
