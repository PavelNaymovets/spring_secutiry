package com.naumovets.market.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void securityTokenTest() throws Exception {
        String jsonRequest = "{\n" +
                "\t\"username\": \"user_1\",\n" +
                "\t\"password\": \"100\"\n" +
                "}";
        MvcResult result = mvc.perform(
                post("/auth")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();
        token = token.replace("{\"token\":\"", "").replace("\"}", "");

        mvc.perform(
                get("/api/v1/admin")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }
}
