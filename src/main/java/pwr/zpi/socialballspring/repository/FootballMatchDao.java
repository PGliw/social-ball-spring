package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.FootballMatch;

public interface FootballMatchDao extends CrudRepository<FootballMatch, Long> {
}
