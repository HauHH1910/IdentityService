package com.hauhh.service;

import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.entities.User;
import com.hauhh.exception.AppException;
import com.hauhh.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    private UserCreationRequest userRequest;

    private UserResponse userResponse;

    private String token;

    @BeforeEach
    public void initData() throws Exception {
//        ResultActions resultActions = this.mockMvc.perform(post("/api/auth/token")
//                .content("{\"username\":\"admin\", \"password\":\"123\"}")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
//
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//
//        JSONObject jsonObject = new JSONObject(contentAsString);
//
//        this.token = "Bearer " + jsonObject.getJSONObject("result").getString("token"); //Don't forget to add "Bearer " as a prefix

        LocalDate dob = LocalDate.of(2003, 10, 19);

        userRequest = UserCreationRequest.builder()
                .username("Game")
                .password("12345678")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("123456")
                .username("Game")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("123456")
                .username("Game")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();
    }

    @Test
    public void createUserTest_valid_request_success(){
        //GIVE
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //WHEN
        UserResponse response = userService.createUser(userRequest);
        //THEN
        assertThat(response.getId()).isEqualTo("123456");
        assertThat(response.getUsername()).isEqualTo("Game");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void createUserTest_invalid_request_fail(){
        //GIVE
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        //WHEN
        AppException exception = assertThrows(AppException.class, () -> userService.createUser(userRequest));

        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("User already exists");
        assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

}
