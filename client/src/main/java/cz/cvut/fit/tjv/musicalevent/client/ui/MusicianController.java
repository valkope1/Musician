package cz.cvut.fit.tjv.musicalevent.client.ui;

import cz.cvut.fit.tjv.musicalevent.client.entity.BandDTO;
import cz.cvut.fit.tjv.musicalevent.client.entity.MusicianDTO;
import cz.cvut.fit.tjv.musicalevent.client.service.BandService;
import cz.cvut.fit.tjv.musicalevent.client.service.MusicianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/musicians")
public class MusicianController {
    private MusicianService musicianService;
    private BandService bandService;
    public MusicianController(MusicianService musicianService, BandService bandService){
        this.musicianService = musicianService;
        this.bandService = bandService;
    }

    @GetMapping
    public String listAllMusicians(Model model){
        var allMusicians = musicianService.readAll();
        model.addAttribute("musicians", allMusicians);
        return "Musicians";
    }

    @GetMapping(path = "/create")
    public String createMusicianForm(Model model){
        var allBands = bandService.readAll().stream().map(BandDTO::getBandId);
        model.addAttribute("bandsId", allBands);
        return "CreateMusician";
    }

    @PostMapping(path = "/create")
    public String createMusician(MusicianDTO musicianDTO){
        musicianService.create(musicianDTO);
        return "redirect:/musicians";
    }

    @GetMapping(path = "/update")
    public String editMusicianForm(@RequestParam("id") Long id, Model model){
        var musician = musicianService.readById(id);
        var allBands = bandService.readAll().stream().map(BandDTO::getBandId);
        model.addAttribute("bandsId", allBands);
        model.addAttribute("musician", musician);
        return "EditMusician";
    }
    @PostMapping(path = "/update")
    public String updateMusician(MusicianDTO musicianDTO){
        musicianService.update(musicianDTO);
        return "redirect:/musicians";
    }

    @GetMapping(path = "/delete")
    public String deleteMusician(@RequestParam("id") Long id){
        musicianService.delete(id);
        return "redirect:/musicians";
    }

}
