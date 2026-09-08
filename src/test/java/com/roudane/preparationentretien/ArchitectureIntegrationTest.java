package com.roudane.preparationentretien.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roudane.preparationentretien.dto.order.OrderLineRequest;
import com.roudane.preparationentretien.dto.order.OrderRequest;
import com.roudane.preparationentretien.dto.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ArchitectureIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUserAndOrderFlow() throws Exception {
        // 1. Create User
        UserRequest userRequest = new UserRequest("John", "Doe", "john.doe@example.com", "0102030405");
        String userResponseJson = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andReturn().getResponse().getContentAsString();

        Long userId = objectMapper.readTree(userResponseJson).get("id").asLong();

        // 2. Get User
        mockMvc.perform(get("/api/v1/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        // 3. Create Order
        OrderLineRequest line1 = new OrderLineRequest("Product A", 2, new BigDecimal("10.50"));
        OrderRequest orderRequest = new OrderRequest(userId, List.of(line1));

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.totalAmount").value(21.00));
    }
}
