package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Comment;
import pwr.zpi.socialballspring.model.MatchMember;

public interface MatchMemberDao extends CrudRepository<MatchMember, Long> {
}
