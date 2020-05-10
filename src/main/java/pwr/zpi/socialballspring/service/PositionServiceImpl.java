package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.PositionDto;
import pwr.zpi.socialballspring.dto.Response.PositionResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Position;
import pwr.zpi.socialballspring.repository.PositionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "positionService")
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionDao positionDao;

    @Override
    public List<PositionResponse> findAll() {
        List<Position> list = new ArrayList<>();
        positionDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(PositionResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        positionDao.deleteById(id);
    }

    @Override
    public PositionResponse findById(long id) {
        Optional<Position> optionalPosition = positionDao.findById(id);
        return optionalPosition.map(PositionResponse::new).orElseThrow(() -> new NotFoundException("PositionResponse"));
    }

    @Override
    public PositionResponse update(PositionDto positionDto, long id) {
        Optional<Position> optionalPosition = positionDao.findById(id);
        if (optionalPosition.isPresent()) {
            Position position = Position.builder()
                    .name(positionDto.getName())
                    .side(positionDto.getSide())
                    .id(id)
                    .build();
            Position savedPosition = positionDao.save(position);
            return new PositionResponse(savedPosition);
        } else throw new NotFoundException("Position");
    }

    @Override
    public PositionResponse save(PositionDto positionDto) {
        Position position = Position.builder()
                .name(positionDto.getName())
                .side(positionDto.getSide())
                .build();
        Position savedPosition = positionDao.save(position);
        return new PositionResponse(savedPosition);
    }
}
