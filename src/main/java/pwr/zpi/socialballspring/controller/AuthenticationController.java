package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.JwtTokenUtil;
import pwr.zpi.socialballspring.dto.LoginUserDto;
import pwr.zpi.socialballspring.dto.Response.LoginResponse;
import pwr.zpi.socialballspring.service.MyUserDetailsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;


    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) throws AuthenticationException {

        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginUserDto.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new LoginResponse(token, userDetails.getUsername()), HttpStatus.OK);
    }

}
