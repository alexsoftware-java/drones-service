package com.musala.interview.exception.handler;

import com.musala.interview.service.DispatcherScheduledService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@MockBean(DispatcherScheduledService.class) // disable scheduler by mocking it
@AutoConfigureMockMvc
class ValidationExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testExceptionHandler() throws Exception {
        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"#@!\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("""
                        Error occurred, request validation failed! addDrone.droneRequestDto: - serial number (100 characters max);
                        - model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
                        - weight limit (500gr max);"""));
    }

}