package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Comment;

public interface CommentDao extends CrudRepository<Comment, Long> {
}
