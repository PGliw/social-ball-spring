package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;
import pwr.zpi.socialballspring.dto.Response.UserAcquaitanceResponse;
import pwr.zpi.socialballspring.service.AcquaintanceService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/acquaitances")
public class AcquaitanceController {
    @Autowired
    private AcquaintanceService acquaintanceService;

    @Autowired
    IIdentityManager identityManager;

    @PostMapping
    public ResponseEntity<AcquaitanceResponse> saveAcquaitance(@RequestBody AcquaitanceDto Acquaitance) {
        return new ResponseEntity<>(acquaintanceService.save(Acquaitance), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AcquaitanceResponse>> listAcquaitance(@RequestParam String status) {
        return new ResponseEntity<>(acquaintanceService.findAll(identityManager.getCurrentUser().getId(), status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquaitanceResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/acquitanceSent")
    public ResponseEntity<UserAcquaitanceResponse> acquitanceSent(@RequestParam long userId){
        return new ResponseEntity<>(acquaintanceService.isAcquitanceSent(userId), HttpStatus.OK);
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

    @PutMapping("/send")
    public void send(@RequestParam long receiverId){
        acquaintanceService.send(receiverId);
    }

    @PutMapping("/accept")
    public void accept(@RequestParam long senderId){
        acquaintanceService.accept(senderId);
    }

    @PutMapping("/reject")
    public void reject(@RequestParam long senderId){
        acquaintanceService.reject(senderId);
    }
}
