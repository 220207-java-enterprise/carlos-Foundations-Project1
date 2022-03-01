package com.revature.erm.services;

// Test Suite
// A class that encapsulates one or more test methods (cases)
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.dtos.requests.LoginRequest;
import com.revature.erm.models.Users;
import com.revature.erm.util.exceptions.AuthenticationException;
import com.revature.erm.util.exceptions.InvalidRequestException;
import org.junit.*;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

// Because the package structure of our test directory mirrors the src directory,
// Java's compiler and JVM look at this class as if it is in the same package as
// UserService.java
public class UserServiceTest {

    /*
        Common JUnit annotations:
            - @Test (marks a method as a test case)
            - @Ignore (tells JUnit to skip this test case)
            - @Before (logic that runs once before every test case)
            - @After (logic that runs once after every test case)
            - @BeforeClass (logic that runs only once before all test cases)
            - @AfterClass (logic that runs only once after all test cases)
     */

    private UsersService sut; // sut = System Under Test
    private UsersDAO mockUserDao = mock(UsersDAO.class);

    @Before
    public void setup() {
        sut = new UsersService(mockUserDao); // TODO UserDAO needs to be mocked
    }

    @After
    public void cleanUp() {
        reset(mockUserDao);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenEmptyString() {

        // AAA = Arrange, Act, and Assert

        // Arrange
        String username = "";

        // Act
        boolean result = sut.isUsernameValid(username);

        // Assert
        Assert.assertFalse(result);

    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenNullString() {

        // Arrange
        String username = null;
        // Act
        boolean result = sut.isUsernameValid(null);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenShortUsername() {
        Assert.assertFalse(sut.isUsernameValid("short"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenLongUsername() {
        Assert.assertFalse(sut.isUsernameValid("waytolongofausernameforourapplication"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenUsernameWithIllegalCharacters() {
        Assert.assertFalse(sut.isUsernameValid("tester99!"));
    }

    @Test
    public void test_isUsernameValid_returnsTrue_givenValidUsername() {
        Assert.assertTrue(sut.isUsernameValid("tester99"));
    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsername() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("no", "p4$$W0RD");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDao, times(0)).findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("tester99", "invalid");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDao, times(0)).findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsernameAndPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid", "invalid");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDao, times(0)).findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = AuthenticationException.class)
    public void test_login_throwsAuthenticationException_givenUnknownUserCredentials() {

        // Arrange
        UsersService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("unknownuser", "p4$$W0RD");

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
        when(mockUserDao.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(null);

        // Act
        sut.login(loginRequest);

    }

    @Test
    public void test_login_returnsNonNullAppUser_givenValidAndKnownCredentials() {

        // Arrange
        UsersService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("tester99", "p4$$W0RD");

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
        when(mockUserDao.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(new Users());

        // Act
        Users loginResult = spiedSut.login(loginRequest);

        // Assert
        assertNotNull(loginResult);
        verify(mockUserDao, times(1)).findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        verify(spiedSut, times(1)).isUsernameValid(loginRequest.getUsername());
        verify(spiedSut, times(1)).isPasswordValid(loginRequest.getPassword());

    }

}

