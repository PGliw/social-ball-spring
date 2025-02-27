package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.exception.DuplicateException;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.UserService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), new String(user.getPassword()), getAuthority());
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
    public UserResponse update(UserDto userDto, long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            @NotNull char[] passwod = optionalUser.get().getPassword();
            if (userDto.getPassword() != null) {
                passwod = userDto.getPassword();
            }
            @NotNull String email = optionalUser.get().getEmail();
            if (userDto.getEmail() != null) {
                email = userDto.getEmail();
            }
            @NotNull String firstName = optionalUser.get().getFirstName();
            if (userDto.getFirstName() != null) {
                firstName = userDto.getFirstName();
            }
            @NotNull String lastName = optionalUser.get().getLastName();
            if (userDto.getLastName() != null) {
                lastName = userDto.getLastName();
            }
            @NotNull String userName = optionalUser.get().getUsername();
            if (userDto.getLastName() != null) {
                userName = userDto.getUsername();
            }
            @NotNull LocalDate dateOfBirth = optionalUser.get().getDateOfBirth();
            if (userDto.getDateOfBirth() != null) {
                dateOfBirth = userDto.getDateOfBirth();
            }
            @NotNull String image = optionalUser.get().getImage();
            if (userDto.getImage() != null) {
                image = userDto.getImage();
            }
            @NotNull Long newId = optionalUser.get().getId();
            if (userDto.getId() != null) {
                newId = userDto.getId();
            }

            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(userName)
                    .password(bcryptEncoder.encode(new String(passwod)).toCharArray())
                    .dateOfBirth(dateOfBirth)
                    .image(image)
                    .id(newId)
                    .build();
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
                .password(bcryptEncoder.encode(new String(userDto.getPassword())).toCharArray())
                .dateOfBirth(userDto.getDateOfBirth())
                .image(userDto.getImage())
                .build();

        // save the user and return UserResponse
        return new UserResponse(userDao.save(newUser));
    }
}
