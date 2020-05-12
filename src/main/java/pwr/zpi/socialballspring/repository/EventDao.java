package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Event;

public interface EventDao extends CrudRepository<Event, Long> {
}
