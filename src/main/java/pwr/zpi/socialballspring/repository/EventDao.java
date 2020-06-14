package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventDao extends CrudRepository<Event, Long> {
    List<Event> findByFootballMatchId(Long footballMatchId);
}
