package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.dto.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
