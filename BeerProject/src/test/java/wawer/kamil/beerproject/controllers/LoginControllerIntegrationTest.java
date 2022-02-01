package wawer.kamil.beerproject.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wawer.kamil.beerproject.controllers.LoginControllerTestHelper.credentialsContent;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test - should return status ok when signing in")
    void should_return_status_ok_when_signing_in() throws Exception {
        mockMvc.perform(post("/login").content(credentialsContent()))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
