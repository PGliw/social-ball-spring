package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.service.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IIdentityManager identityManager;

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto, identityManager.getCurrentUser().getId()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody UserDto userDto, @PathVariable long id) {
        return new ResponseEntity<>(userService.update(userDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
