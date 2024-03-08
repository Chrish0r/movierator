package com.movierator.movierator.controller;

import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.*;
import com.movierator.movierator.service.DeletedUserEmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MediaRatingRepository mediaRatingRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private ModeratorRepository moderatorRepository;

    @MockBean
    private RegularUserRepository regularUserRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private DeletedUserEmailSenderService deletedUserEmailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserUsernameAlreadyExistsShouldReturnError() {
        // set up
        RegularUser regularUserForm = new RegularUser();
        regularUserForm.setUser(new User());
        regularUserForm.getUser().setLogin("ExistingUser");

        BindingResult bindingResult = new BeanPropertyBindingResult(regularUserForm, "regularUserForm");

        // mock userRepository to simulate that the username already exists
        when(userRepository.findUserByLogin("ExistingUser")).thenReturn(Optional.of(new User()));

        // act
        ModelAndView modelAndView = userController.createUser(regularUserForm, bindingResult);

        assertEquals("/user/user-regular-user-add", modelAndView.getViewName());
        assertEquals(true, bindingResult.hasErrors());
        assertEquals("An account already exists for this login.",
                bindingResult.getFieldError("user.login").getDefaultMessage());



    }


}
