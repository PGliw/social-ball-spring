package pwr.zpi.socialballspring.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.zpi.socialballspring.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
