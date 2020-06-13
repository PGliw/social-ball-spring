package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.service.StatisticsService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @GetMapping
    public ResponseEntity<StatisticsResponse> getStats(){
        return new ResponseEntity<>(statisticsService.findById(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StatisticsResponse> getStats(@PathVariable long id){
        return new ResponseEntity<>(statisticsService.findByUser(id), HttpStatus.OK);
    }
    @GetMapping("/global")
    public ResponseEntity<StatisticsResponse> getGlobalStats(){
        return new ResponseEntity<>(statisticsService.findGlobal(), HttpStatus.OK);
    }
}
