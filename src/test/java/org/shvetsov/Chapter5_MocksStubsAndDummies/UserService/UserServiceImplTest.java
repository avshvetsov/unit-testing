package org.shvetsov.Chapter5_MocksStubsAndDummies.UserService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Write a happy-path test for the class presented below. Verify that the user gets his new password, and
 * that the updateUser() method of userDAO is called.
 */
@DisplayName("5.7.1. User Service Tested")
class UserServiceImplTest {

    private static final String PASSWORD = "somePassword";
    private static final String MD5_PASSWORD = "newMD5Password";
    private final SecurityService securityService = mock(SecurityService.class);
    private final UserDAO userDAO = mock(UserDAO.class);
    private final User user = mock(User.class);
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(userDAO, securityService);
    }

    @Test
    public void shouldUpdatePassword() {
        when(securityService.md5(any())).thenReturn(MD5_PASSWORD);
        User user = new User(PASSWORD);
        service.assignPassword(user);
        assertThat(user.getPassword()).isEqualTo(MD5_PASSWORD);
    }

    @Test
    public void shouldCallUpdateUser() {
        service.assignPassword(user);
        verify(userDAO, times(1)).updateUser(user);
    }
}