package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.zpi.socialballspring.model.User;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

}
