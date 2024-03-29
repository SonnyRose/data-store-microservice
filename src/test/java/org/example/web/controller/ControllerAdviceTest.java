package org.example.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerAdviceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private ControllerAdvice controllerAdvice;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerAdvice).build();
    }
    @Test
    public void testServerExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/v1/anotherendpoint"))
                .andExpect(status().isOk())
                .andExpect(content().string("Things happens"));
    }
}
