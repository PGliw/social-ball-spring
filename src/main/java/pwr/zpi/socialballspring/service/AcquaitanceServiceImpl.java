package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.AcquaitanceDto;
import pwr.zpi.socialballspring.dto.Response.AcquaitanceResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Acquaintance;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.AcquaitanceDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "acquitanceService")
public class AcquaitanceServiceImpl implements AcquaitanceService{
    @Autowired
    AcquaitanceDao acquaitanceDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<AcquaitanceResponse> findAll() {
        List<Acquaintance> list = new ArrayList<>();
        acquaitanceDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(AcquaitanceResponse::new).collect(Collectors.toList());
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
            if(acquaitanceDto.getRequestSenderId() != null){
                sender = userDao.findById(acquaitanceDto.getRequestSenderId()).get();
            }
            User receiver = null;
            if(acquaitanceDto.getRequestReceiverId() != null){
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
        if(acquaitanceDto.getRequestSenderId() != null){
            sender = userDao.findById(acquaitanceDto.getRequestSenderId()).get();
        }
        User receiver = null;
        if(acquaitanceDto.getRequestReceiverId() != null){
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
}
