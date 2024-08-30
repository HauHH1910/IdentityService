package com.hauhh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest userRequest;

    private UserResponse userResponse;

    private String token;

    @BeforeEach
    public void initData() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/auth/token")
                        .content("{\"username\":\"admin\", \"password\":\"123\"}")
                        .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        JSONObject jsonObject = new JSONObject(contentAsString);

        this.token = "Bearer " + jsonObject.getJSONObject("result").getString("token"); //Don't forget to add "Bearer " as a prefix

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
    }

    @Test
    void createUser_valid_request_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String contentMapper = objectMapper.writeValueAsString(userRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token)
                        .content(contentMapper))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("code")
                        .value("1000"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("result.id")
                        .value("123456"));
    }

    @Test
    void createUser_username_invalid_fail() throws Exception {
        //GIVEN
        userRequest.setUsername("jb");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String valueAsString = objectMapper.writeValueAsString(userRequest);
        //WHEN
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token)
                        .content(valueAsString))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("code")
                        .value("1009"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("message")
                        .value("Username must be at least 3"));
    }

}
