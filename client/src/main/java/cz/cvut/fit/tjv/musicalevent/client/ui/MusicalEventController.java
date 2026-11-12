package cz.cvut.fit.tjv.musicalevent.client.ui;

import cz.cvut.fit.tjv.musicalevent.client.entity.BandDTO;
import cz.cvut.fit.tjv.musicalevent.client.entity.MusicalEventDTO;
import cz.cvut.fit.tjv.musicalevent.client.service.BandService;
import cz.cvut.fit.tjv.musicalevent.client.service.MusicalEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/musicalevents")
public class MusicalEventController {
    private MusicalEventService musicalEventService;
    private BandService bandService;
    public MusicalEventController(MusicalEventService musicalEventService, BandService bandService){
        this.musicalEventService = musicalEventService;
        this.bandService = bandService;
    }
    @GetMapping
    public String listAllMusicalEvents(Model model){
        var allMusicalEvents = musicalEventService.readAll();
        model.addAttribute("musicalEvents", allMusicalEvents);
        return "MusicalEvents";
    }
    @GetMapping(path = "/create")
    public String createMusicalEvent(){
        return "CreateMusicalEvent";
    }
    @PostMapping(path = "/create")
    public String createMusicalEvent(MusicalEventDTO musicalEventDTO){
        musicalEventService.create(musicalEventDTO);
        return "redirect:/musicalevents";
    }
    @GetMapping(path = "/update")
    public String editMusicalEvent(@RequestParam("id") Long id, Model model){
        var musicalEvent = musicalEventService.readById(id);
        model.addAttribute("musicalevent", musicalEvent);
        return "EditMusicalEvent";
    }
    @PostMapping(path = "/update")
    public String updateMusicalEvent(MusicalEventDTO musicalEventDTO){
        musicalEventService.update(musicalEventDTO);
        return "redirect:/musicalevents";
    }

    @GetMapping(path = "/delete")
    public String deleteMusicalEvent(@RequestParam("id") Long id){
        musicalEventService.delete(id);
        return "redirect:/musicalevents";
    }
    @GetMapping(path = "/afterdate")
    public String showMusicalEventsAfterDate(@RequestParam("date")LocalDate date, Model model){
        var musicalEvents = musicalEventService.getAfterDate(date);
        model.addAttribute("musicalevent", musicalEvents);
        model.addAttribute("date", date);
        return "DateMusicalEvent";
    }

    @GetMapping(path = "/addNewBands")
    public String addNewBands(@RequestParam("id") Long eventId){
        List<BandDTO> bands = bandService.readAll();
        LocalDate rok = LocalDate.of(2020, 1, 1);
        for(BandDTO band : bands){
            if(band.getFormationDate().isAfter(rok)){
                bandService.addToMusicalEvent(band.getBandId(), eventId);
            }
        }
        return "redirect:/musicalevents";
    }
}
