package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.MusicalEventDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicalEventService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MusicalEventController.class)
public class MusicalEventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MusicalEventService musicalEventService;
    @MockBean
    private MusicalEventDTOConverter musicalEventDTOConverter;

    @Test
    void create() throws Exception{
        var musicalEvent = new MusicalEvent();
        Mockito.when(musicalEventService.createMusicalEvent(musicalEvent)).thenReturn(musicalEvent);
        mockMvc.perform(MockMvcRequestBuilders.post("/musicalevents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"musicalEventId\": 202,\n" +
                        "        \"town\": \"dsadsa\",\n" +
                        "        \"eventName\": \"dsadsaddd\",\n" +
                        "        \"date\": \"2024-01-04\",\n" +
                        "        \"bandsId\": [\n" +
                        "            402\n" +
                        "        ]\n" +
                        "    }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws  Exception{
        var musicalEvent = new MusicalEvent();
        Mockito.when(musicalEventService.updateMusicalEvent(musicalEvent)).thenReturn(musicalEvent);
        mockMvc.perform(MockMvcRequestBuilders.put("/musicalevents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"musicalEventId\": 202,\n" +
                                "        \"town\": \"dsadsa\",\n" +
                                "        \"eventName\": \"dsadsaddd\",\n" +
                                "        \"date\": \"2024-01-04\",\n" +
                                "        \"bandsId\": [\n" +
                                "            402\n" +
                                "        ]\n" +
                                "    }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void read() throws  Exception{
        var musicalEvent = new MusicalEvent();
        var musicalEvent2 = new MusicalEvent();
        musicalEvent.setMusicalEventId(15L);
        musicalEvent2.setMusicalEventId(16L);
        Mockito.when(musicalEventService.getMusicalEventById(musicalEvent.getMusicalEventId())).thenReturn(musicalEvent);
        Mockito.when(musicalEventService.getMusicalEventById(musicalEvent2.getMusicalEventId())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/musicalevents/{id}", musicalEvent.getMusicalEventId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/musicalevents/{id}", musicalEvent2.getMusicalEventId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
}
