package pwr.zpi.socialballspring.repository;

import org.springframework.data.repository.CrudRepository;
import pwr.zpi.socialballspring.model.Acquaintance;

import java.util.Optional;

public interface AcquaitanceDao extends CrudRepository<Acquaintance, Long> {
    Optional<Acquaintance> findAcquaintanceByRequestSenderIdAndRequestReceiverId(Long requestSenderId, Long requestReceiverId);
}
