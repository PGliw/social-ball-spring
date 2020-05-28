package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;
import pwr.zpi.socialballspring.service.AcquaintanceService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/acquaitances")
public class AcquaitanceController {
    @Autowired
    private AcquaintanceService acquaintanceService;

    @PostMapping
    public ResponseEntity<AcquaitanceResponse> saveAcquaitance(@RequestBody AcquaitanceDto Acquaitance) {
        return new ResponseEntity<>(acquaintanceService.save(Acquaitance), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AcquaitanceResponse>> listAcquaitance() {
        return new ResponseEntity<>(acquaintanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquaitanceResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcquaitanceResponse> update(@RequestBody AcquaitanceDto AcquaitanceDto, @PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.update(AcquaitanceDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        acquaintanceService.delete(id);
        return new ResponseEntity<>("Acquaitance deleted successfully", HttpStatus.OK);
    }
}
