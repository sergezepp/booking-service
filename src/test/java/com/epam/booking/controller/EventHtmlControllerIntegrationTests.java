package com.epam.booking.controller;


import com.epam.booking.model.dto.EventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class EventHtmlControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllEvents() throws Exception {
        mockMvc.perform(get("/getAllEvents"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("events", hasSize(2)))
                .andExpect(view().name("event"));
    }

    @Test
    void testGetEventById() throws Exception {
        mockMvc.perform(get("/getEventById/{id}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("events",
                        is(new EventDto(1L, "FORMULA 1", java.sql.Date.valueOf("2022-10-10")))))
                .andExpect(view().name("event"));
    }

    @Test
    void testGetEventById_InvalidId() throws Exception {
        mockMvc.perform(get("/getEventById/{id}", 9999L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("message",
                        is("No Element Found")))
                .andExpect(view().name("error"));
    }

}
