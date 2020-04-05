package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.User;
import pwr.zpi.socialballspring.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
