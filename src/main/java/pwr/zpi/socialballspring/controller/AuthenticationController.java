package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.dto.Response.ApiResponse;
import pwr.zpi.socialballspring.dto.AuthToken;
import pwr.zpi.socialballspring.dto.LoginUserDto;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.config.JwtTokenUtil;
import pwr.zpi.socialballspring.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUserDto loginUserDto) throws AuthenticationException {

        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final User user = userService.findOne(loginUserDto.getEmail());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getEmail()));
    }

}
