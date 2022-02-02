package wawer.kamil.beerproject.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.BreweryDTO;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wawer.kamil.beerproject.controllers.BreweryControllerTestHelper.breweryContent;
import static wawer.kamil.beerproject.controllers.BreweryControllerTestHelper.getListOfBreweryDTO;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class BreweryControllerIntegrationTest {

    private static final String SINGLE_BREWERY_ENDPOINT = "/brewery/%d";
    private static final String BREWERY_ENDPOINT = "/brewery";
    private static final String BREWERY_ENDPOINT_ALL = "/brewery/list";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test - should return status ok when getting single Brewery")
    void should_return_status_ok_when_getting_single_brewery() throws Exception {
        // given
        long id = 1;

        // when
        MvcResult mvcResult = mockMvc.perform(get(format(SINGLE_BREWERY_ENDPOINT, id)).contentType(TEXT_HTML))
                .andExpect(status().is(OK.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        BreweryDTO brewery = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreweryDTO.class);
        assertEquals("Browar Zakładowy", brewery.getName());
    }

    @Test
    @DisplayName("Test - should return status created when adding single Brewery")
    @Transactional
    void should_return_status_created_when_adding_single_brewery() throws Exception {

        // given
        String content = breweryContent();
        MvcResult mvcResult = getAllBrewerysRequest();
        int sizeOfOriginalFetchedList = getListOfBreweryDTO(mvcResult).size();

        // when
        ResultActions perform = mockMvc.perform(post(BREWERY_ENDPOINT)
                .content(content)
                .contentType(APPLICATION_JSON));

        // then
        perform.andExpect(status().is(CREATED.value()));

        MvcResult mvcResult1 = getAllBrewerysRequest();
        int sizeOfFetchedListAfterPost = getListOfBreweryDTO(mvcResult1).size();
        assertEquals(sizeOfOriginalFetchedList + 1, sizeOfFetchedListAfterPost);
    }

    private MvcResult getAllBrewerysRequest() throws Exception {
        return mockMvc.perform(get(BREWERY_ENDPOINT_ALL)
                        .contentType(APPLICATION_JSON))
                .andReturn();
    }

}
