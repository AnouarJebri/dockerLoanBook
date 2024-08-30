package com.esprit.authservice.service;

import com.esprit.authservice.model.User;
import com.esprit.authservice.model.UserRole;
import com.esprit.authservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private AutoCloseable closeable;


    @BeforeAll
    static void beforeAll() {
        //Nothing for now
    }

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User(3, "Test", "User", email, UserRole.SUBSCRIBER, new ArrayList<>());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void saveUser() {
        // Arrange
        User user = new User(3, "Test", "User", "test@example.com", UserRole.SUBSCRIBER, new ArrayList<>());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        //Assert
        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findAll() {
        // Arrange
        User user1 = new User(3, "John", "Doe", "john@example.com", UserRole.SUBSCRIBER, new ArrayList<>());
        User user2 = new User(4, "Jane", "Doe", "jane@example.com", UserRole.ADMIN, new ArrayList<>());
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<Map<String, Object>> users = userService.findAll();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(3, users.get(0).get("id"));
        assertEquals("John", users.get(0).get("nom"));
        assertEquals("Doe", users.get(0).get("prenom"));
        assertEquals(UserRole.SUBSCRIBER, users.get(0).get("role"));
        assertEquals(new ArrayList<>(), users.get(0).get("reservation"));

        assertEquals(4, users.get(1).get("id"));
        assertEquals("Jane", users.get(1).get("nom"));
        assertEquals("Doe", users.get(1).get("prenom"));
        assertEquals(UserRole.ADMIN, users.get(1).get("role"));
        assertEquals(new ArrayList<>(), users.get(1).get("reservation"));

        verify(userRepository, times(1)).findAll();
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @AfterAll
    static void afterAll() {
        //You guessed it nothing here too
    }
}