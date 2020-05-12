package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.Response.UserResponse;
import pwr.zpi.socialballspring.dto.UserDto;
import pwr.zpi.socialballspring.model.User;

import java.util.List;

public interface UserService {
    UserResponse save(UserDto user);

    List<UserResponse> findAll();

    void delete(long id);

    User findOne(String username);

    UserResponse findById(long id);

    UserResponse update(UserDto userDto, long id);
}
