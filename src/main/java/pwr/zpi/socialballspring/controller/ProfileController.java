package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private IIdentityManager identityManager;

    @GetMapping
    public ResponseEntity<UserResponse> getOne() {
        return new ResponseEntity<>(userService.findById(identityManager.getCurrentUser().getId()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto, identityManager.getCurrentUser().getId()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        userService.delete(identityManager.getCurrentUser().getId());
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
