package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.BandDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.service.BandService;
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

@WebMvcTest(BandController.class)
public class BandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BandService bandService;
    @MockBean
    private BandDTOConverter bandDTOConverter;

    @Test
    void create() throws Exception{
        var band = new Band();
        Mockito.when(bandService.createBand(band)).thenReturn(band);
        mockMvc.perform(MockMvcRequestBuilders.post("/bands")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"bandId\": 402,\n" +
                        "        \"name\": \"kapela\",\n" +
                        "        \"genre\": \"nc\",\n" +
                        "        \"formationDate\": \"2024-01-05\",\n" +
                        "        \"musiciansId\": [\n" +
                        "            252,\n" +
                        "            302\n" +
                        "        ],\n" +
                        "        \"musicalEventsId\": [\n" +
                        "            202\n" +
                        "        ]\n" +
                        "    }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception{
        var band = new Band();
        Mockito.when(bandService.updateBand(band)).thenReturn(band);
        mockMvc.perform(MockMvcRequestBuilders.put("/bands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"bandId\": 402,\n" +
                                "        \"name\": \"kapela\",\n" +
                                "        \"genre\": \"nc\",\n" +
                                "        \"formationDate\": \"2024-01-05\",\n" +
                                "        \"musiciansId\": [\n" +
                                "            252,\n" +
                                "            302\n" +
                                "        ],\n" +
                                "        \"musicalEventsId\": [\n" +
                                "            202\n" +
                                "        ]\n" +
                                "    }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void read()  throws Exception{
        var band = new Band();
        var band2 = new Band();
        band.setBandId(1L);
        band.setBandId(2L);
        Mockito.when(bandService.getBandById(band.getBandId())).thenReturn(band);
        Mockito.when(bandService.getBandById(band2.getBandId())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/bands/{id}", band.getBandId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/bands/{id}", band2.getBandId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
