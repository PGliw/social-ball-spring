package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Team;

public interface TeamDao extends CrudRepository<Team, Long> {
}
