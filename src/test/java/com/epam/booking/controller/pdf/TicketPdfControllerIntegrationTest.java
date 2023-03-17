package com.epam.booking.controller.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class TicketPdfControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetBookedTicketsByUserIdentifier() throws Exception {
        byte[] array = mockMvc.perform(get("/pdf/getBookedTickets/{userIdentifier}", "sergio.cepeda"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andReturn().getResponse().getContentAsByteArray();

        Assert.notEmpty(Collections.singleton(array), "Not empty");


    }


}
