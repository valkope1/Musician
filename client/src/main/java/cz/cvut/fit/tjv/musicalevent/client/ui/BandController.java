package cz.cvut.fit.tjv.musicalevent.client.ui;

import cz.cvut.fit.tjv.musicalevent.client.entity.BandDTO;
import cz.cvut.fit.tjv.musicalevent.client.service.BandService;
import cz.cvut.fit.tjv.musicalevent.client.service.MusicalEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bands")
public class BandController {
    private BandService bandService;
    private MusicalEventService musicalEventService;
    public BandController(BandService bandService, MusicalEventService musicalEventService){
        this.bandService = bandService;
        this.musicalEventService = musicalEventService;
    }

    @GetMapping
    public String listAllBands(Model model){
        var allBands = bandService.readAll();
        var allMusicalEventsId = musicalEventService.getAllId();
        model.addAttribute("bands", allBands);
        model.addAttribute("musicalEventId", allMusicalEventsId);
        return "Bands";
    }
    @GetMapping(path = "/create")
    public String createBand(){
        return "CreateBand";
    }
    @PostMapping(path = "/create")
    public String createBand(BandDTO bandDTO){
        bandService.create(bandDTO);
        return "redirect:/bands";
    }
    @GetMapping(path = "/update")
    public String editBandForm(@RequestParam("id") Long id, Model model){
        var band = bandService.readById(id);
        model.addAttribute("band", band);
        return "EditBand";
    }
    @PostMapping(path = "/update")
    public String updateBand(BandDTO bandDTO){
        bandService.update(bandDTO);
        return "redirect:/bands";
    }
    @GetMapping(path = "/addToMusicalEvent")
    public String addToMusicalEvent(@RequestParam("bandId") Long bandId, @RequestParam("eventId") Long eventId){
        bandService.addToMusicalEvent(bandId, eventId);
        return "redirect:/bands";
    }
    @GetMapping(path = "/removeFromMusicalEvent")
    public String removeFromMusicalEvent(@RequestParam("bandId") Long bandId, @RequestParam("eventId2") Long eventId){
        bandService.removeFromMusicalEvent(bandId, eventId);
        return "redirect:/bands";
    }

    @GetMapping(path = "/delete")
    public String deleteBand(@RequestParam("id") Long id){
        bandService.delete(id);
        return "redirect:/bands";
    }
}
