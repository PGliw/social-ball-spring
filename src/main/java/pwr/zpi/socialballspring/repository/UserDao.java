package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.zpi.socialballspring.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
