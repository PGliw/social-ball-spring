package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IdentityManager;
import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Acquaintance;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.AcquaitanceDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.AcquaintanceService;
import pwr.zpi.socialballspring.util.Constants;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "acquitanceService")
public class AcquaintanceServiceImpl implements AcquaintanceService {
    @Autowired
    AcquaitanceDao acquaitanceDao;

    @Autowired
    UserDao userDao;

    @Autowired
    IdentityManager identityManager;

    @Override
    public List<AcquaitanceResponse> findAll(long id, String status) {
        List<Acquaintance> list = new ArrayList<>();
        acquaitanceDao.findAll().iterator().forEachRemaining(list::add);
        switch (status) {
            case Constants.FRIENDSHIP_STATUS_PENDING:
                return list.stream()
                        .filter(a -> a.getRequestReceiver() != null && a.getRequestReceiver().getId().equals(id))
                        .filter(a -> a.getStatus() != null && a.getStatus().equals(status))
                        .map(AcquaitanceResponse::new).collect(Collectors.toList());
            case Constants.FRIENDSHIP_STATUS_ACCEPTED:
            case Constants.FRIENDSHIP_STATUS_REJECTED:
                return list.stream()
                        .filter(a -> (a.getRequestReceiver() != null
                                && a.getRequestReceiver().getId().equals(id)) ||
                                a.getRequestSender().getId().equals(id))
                        .filter(a -> a.getStatus().equals(status))
                        .map(AcquaitanceResponse::new).collect(Collectors.toList());
            default:
                throw new NotFoundException("Status");
        }
    }

    @Override
    public void delete(long id) {
        acquaitanceDao.deleteById(id);
    }

    @Override
    public AcquaitanceResponse findById(long id) {
        Optional<Acquaintance> optionalAcquaintance = acquaitanceDao.findById(id);
        return optionalAcquaintance.map(AcquaitanceResponse::new).orElseThrow(() -> new NotFoundException("AcquaitanceResponse"));
    }

    @Override
    public AcquaitanceResponse update(AcquaitanceDto acquaitanceDto, long id) {
        Optional<Acquaintance> optionalAcquaintance = acquaitanceDao.findById(id);
        if (optionalAcquaintance.isPresent()) {
            LocalDateTime dateOfAcceptance = dateUtils.convertFromString(acquaitanceDto.getDateOfAcceptance());
            User sender = null;
            if (acquaitanceDto.getRequestSenderId() != null) {
                sender = userDao.findById(acquaitanceDto.getRequestSenderId()).get();
            }
            User receiver = null;
            if (acquaitanceDto.getRequestReceiverId() != null) {
                receiver = userDao.findById(acquaitanceDto.getRequestReceiverId()).get();
            }
            Acquaintance acquaintance = Acquaintance.builder()
                    .dateOfAcceptance(dateOfAcceptance)
                    .id(id)
                    .requestReceiver(receiver)
                    .requestSender(sender)
                    .status(acquaitanceDto.getStatus())
                    .build();
            Acquaintance savedAcquaintance = acquaitanceDao.save(acquaintance);
            return new AcquaitanceResponse(savedAcquaintance);
        } else throw new NotFoundException("Acquaintance");
    }

    @Override
    public AcquaitanceResponse save(AcquaitanceDto acquaitanceDto) {
        LocalDateTime dateOfAcceptance = dateUtils.convertFromString(acquaitanceDto.getDateOfAcceptance());
        User sender = null;
        if (acquaitanceDto.getRequestSenderId() != null) {
            sender = userDao.findById(acquaitanceDto.getRequestSenderId()).get();
        }
        User receiver = null;
        if (acquaitanceDto.getRequestReceiverId() != null) {
            receiver = userDao.findById(acquaitanceDto.getRequestReceiverId()).get();
        }
        Acquaintance acquaintance = Acquaintance.builder()
                .dateOfAcceptance(dateOfAcceptance)
                .requestReceiver(receiver)
                .requestSender(sender)
                .status(acquaitanceDto.getStatus())
                .build();
        Acquaintance savedAcquaintance = acquaitanceDao.save(acquaintance);
        return new AcquaitanceResponse(savedAcquaintance);
    }

    @Override
    public void send(long userId) {
        Optional<User> receiver = userDao.findById(userId);
        if (receiver.isPresent()) {
            Acquaintance acquaintance = Acquaintance.builder()
                    .requestSender(identityManager.getCurrentUser())
                    .requestReceiver(receiver.get())
                    .status(Constants.FRIENDSHIP_STATUS_PENDING)
                    .build();
            acquaitanceDao.save(acquaintance);
        } else {
            throw new NotFoundException("User");
        }
    }

    @Override
    public void accept(long userId) {
        decideInvitation(userId, Constants.FRIENDSHIP_STATUS_ACCEPTED);
    }

    public void reject(long userId) {
        decideInvitation(userId, Constants.FRIENDSHIP_STATUS_REJECTED);
    }

    private void decideInvitation(long senderId, String decisionStatus) {
        Optional<User> sender = userDao.findById(senderId);
        if (sender.isPresent()) {
            List<Acquaintance> list = new ArrayList<>();
            acquaitanceDao.findAll().iterator().forEachRemaining(list::add);
            Optional<Acquaintance> acquaintance = list.stream()
                    .filter(a -> a.getRequestReceiver().getId().equals(identityManager.getCurrentUser().getId()))
                    .filter(a -> a.getRequestSender().getId().equals(senderId))
                    .findFirst();
            if (acquaintance.isPresent()) {
                acquaintance.get().setStatus(decisionStatus);
                acquaitanceDao.save(acquaintance.get());
            }
        } else {
            throw new NotFoundException("User");
        }
    }
}
