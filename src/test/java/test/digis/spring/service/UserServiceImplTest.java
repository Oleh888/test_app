package test.digis.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.digis.spring.exception.UserAlreadyExistException;
import test.digis.spring.exception.UserNotFoundException;
import test.digis.spring.model.Gender;
import test.digis.spring.model.User;
import test.digis.spring.repository.UserRepository;
import test.digis.spring.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_FUL_NAME = "testFullName";
    private static final LocalDate TEST_DOB = LocalDate.of(2000, 1, 1);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public  void initTestUser() {
        testUser = new User(
                TEST_LOGIN,
                TEST_FUL_NAME,
                TEST_DOB,
                Gender.MALE);
    }

    @Test
    public void getByLoginShouldReturnUser() {
        when(userRepository.findById(TEST_LOGIN)).thenReturn(Optional.of(testUser));

        User userFromDB = userService.getByLogin(TEST_LOGIN);

        assertEquals(testUser, userFromDB);
    }

    @Test
    public void getByNotExistingLoginShouldThrowException() {
        when(userRepository.findById(TEST_LOGIN)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.getByLogin(TEST_LOGIN));
    }

    @Test
    public void createUserShouldReturnTheSameUser() {
        when(userRepository.existsById(TEST_LOGIN)).thenReturn(Boolean.FALSE);
        when(userRepository.insert(testUser)).thenReturn(testUser);

        User createdUser = userService.create(testUser);

        assertEquals(testUser, createdUser);
    }

    @Test
    public void createAlreadyExistingUserShouldThrowException() {
        when(userRepository.existsById(TEST_LOGIN)).thenReturn(Boolean.TRUE);

        assertThrows(UserAlreadyExistException.class, () ->
                userService.create(testUser));
    }

    @Test
    public void updateUserShouldReturnTheSameUser() {
        when(userRepository.existsById(TEST_LOGIN)).thenReturn(Boolean.TRUE);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User updatedUser = userService.update(testUser);

        assertEquals(testUser, updatedUser);
    }

    @Test
    public void updateNotExistingUserShouldThrowException() {
        when(userRepository.existsById(TEST_LOGIN)).thenReturn(Boolean.FALSE);

        assertThrows(UserNotFoundException.class, () ->
                userService.update(testUser));
    }
}
