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

    @GetMapping("/withUser/{id}")
    public ResponseEntity<AcquaitanceResponse> getByOtherUserId(@PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.findByOtherUserId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquaitanceResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/acquitanceSent")
    public ResponseEntity<UserAcquaitanceResponse> acquitanceSent(@RequestParam long userId) {
        return new ResponseEntity<>(acquaintanceService.isAcquitanceSent(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcquaitanceResponse> update(@RequestBody AcquaitanceDto AcquaitanceDto, @PathVariable long id) {
        return new ResponseEntity<>(acquaintanceService.update(AcquaitanceDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        acquaintanceService.delete(id);
        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @PutMapping("/send")
    public ResponseEntity<AcquaitanceResponse> send(@RequestParam long receiverId) {
        return new ResponseEntity<>(acquaintanceService.send(receiverId), HttpStatus.OK);
    }

    @PutMapping("/accept")
    public ResponseEntity<AcquaitanceResponse> accept(@RequestParam long senderId) {
        return new ResponseEntity<>(acquaintanceService.accept(senderId), HttpStatus.OK);
    }

    @PutMapping("/reject")
    public ResponseEntity<AcquaitanceResponse> reject(@RequestParam long senderId) {
        return new ResponseEntity<>(acquaintanceService.reject(senderId), HttpStatus.OK);
    }
}
