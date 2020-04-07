package pwr.zpi.socialballspring.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.exception.DuplicateException;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.UserDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<UserResponse> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserResponse findById(long id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.map(UserResponse::new).orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    public UserResponse update(UserDto userDto) {
        long userId = userDto.getId();
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BeanUtils.copyProperties(userDto, user, "password"); // ignore password?
            User savedUser = userDao.save(user);
            return new UserResponse(savedUser);
        } else throw new NotFoundException("User");
    }

    @Override
    public UserResponse save(UserDto userDto) {
        // if user with given email exists then throw http exception
        Optional<User> existingUser = userDao.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateException("User");
        }

        // build user from DTO
        User newUser = User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .password(bcryptEncoder.encode(userDto.getPassword()))
                .dateOfBirth(userDto.getDateOfBirth())
                .image(userDto.getImage())
                .build();

        // save the user and return UserResponse
        return new UserResponse(userDao.save(newUser));
    }
}
