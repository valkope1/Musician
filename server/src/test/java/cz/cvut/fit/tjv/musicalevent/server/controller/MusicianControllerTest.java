package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.MusicianDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicianService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;

@WebMvcTest(MusicianController.class)
public class MusicianControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MusicianService musicianService;
    @MockBean
    private MusicianDTOConverter musicianDTOConverter;

    @Test
    void create() throws Exception{
        var musician = new Musician();
        musician.setMusicianId(1L);
        Mockito.when(musicianService.createMusician(musician)).thenReturn(musician);
        mockMvc.perform(MockMvcRequestBuilders.post("/musicians")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"+
                        " \"musicianId\": 1,\n" +
                        " \"name\": \"test\",\n" +
                        " \"surname\": \"surname\",\n" +
                        " \"instrument\": \"instrument\",\n" +
                        " \"birthDate\": \"2024-01-05\",\n" +
                        " \"bandid\": 1\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception{
        var musician = new Musician();
        musician.setMusicianId(1L);
        Mockito.when(musicianService.updateMusician(musician)).thenReturn(musician);
        mockMvc.perform(MockMvcRequestBuilders.put("/musicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n"+
                                " \"musicianId\": 1,\n" +
                                " \"name\": \"test\",\n" +
                                " \"surname\": \"surname\",\n" +
                                " \"instrument\": \"instrument\",\n" +
                                " \"birthDate\": \"2024-01-05\",\n" +
                                " \"bandid\": 1\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void read() throws  Exception{
        var musician = new Musician();
        musician.setMusicianId(1L);
        Mockito.when(musicianService.getMusicianById(musician.getMusicianId())).thenReturn(musician);
        mockMvc.perform(MockMvcRequestBuilders.get("/musicians/{id}", musician.getMusicianId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
