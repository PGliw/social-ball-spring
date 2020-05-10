package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Position;

public interface PositionDao extends CrudRepository<Position, Long> {
}
