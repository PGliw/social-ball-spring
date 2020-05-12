package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Rating;

public interface RatingDao extends CrudRepository<Rating, Long> {
}
