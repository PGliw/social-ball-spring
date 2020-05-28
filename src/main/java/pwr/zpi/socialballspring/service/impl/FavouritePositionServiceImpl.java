package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.FavouritePositionDto;
import pwr.zpi.socialballspring.dto.Response.FavouritePositionResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.*;
import pwr.zpi.socialballspring.repository.FavouritePositionDao;
import pwr.zpi.socialballspring.repository.PositionDao;
import pwr.zpi.socialballspring.repository.UserDao;
import pwr.zpi.socialballspring.service.FavouritePositionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "favouritePositionService")
public class FavouritePositionServiceImpl implements FavouritePositionService {
    @Autowired
    FavouritePositionDao favouritePositionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PositionDao positionDao;

    @Override
    public List<FavouritePositionResponse> findAll() {
        List<FavouritePosition> list = new ArrayList<>();
        favouritePositionDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(FavouritePositionResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        favouritePositionDao.deleteById(id);
    }

    @Override
    public FavouritePositionResponse findById(long id) {
        Optional<FavouritePosition> optionalFavouritePosition = favouritePositionDao.findById(id);
        return optionalFavouritePosition.map(FavouritePositionResponse::new).orElseThrow(() -> new NotFoundException("FavouritePositionResponse"));
    }

    @Override
    public FavouritePositionResponse update(FavouritePositionDto favouritePositionDto, long id) {
        Optional<FavouritePosition> optionalFavouritePosition = favouritePositionDao.findById(id);
        if (optionalFavouritePosition.isPresent()) {
            User user = null;
            if(favouritePositionDto.getUserId() != null){
                user = userDao.findById(favouritePositionDto.getUserId()).get();
            }
            Position position = null;
            if(favouritePositionDto.getPositionId() != null) {
                position = positionDao.findById(favouritePositionDto.getPositionId()).get();
            }
            FavouritePosition favouritePosition = FavouritePosition.builder()
                    .position(position)
                    .user(user)
                    .id(id)
                    .build();
            FavouritePosition savedFavouritePosition = favouritePositionDao.save(favouritePosition);
            return new FavouritePositionResponse(savedFavouritePosition);
        } else throw new NotFoundException("FavouritePosition");
    }

    @Override
    public FavouritePositionResponse save(FavouritePositionDto favouritePositionDto) {
        User user = null;
        if (favouritePositionDto.getUserId() != null) {
            user = userDao.findById(favouritePositionDto.getUserId()).get();
        }
        Position position = null;
        if (favouritePositionDto.getPositionId() != null) {
            position = positionDao.findById(favouritePositionDto.getPositionId()).get();
        }
        FavouritePosition favouritePosition = FavouritePosition.builder()
                .position(position)
                .user(user)
                .build();
        FavouritePosition savedFavouritePosition = favouritePositionDao.save(favouritePosition);
        return new FavouritePositionResponse(savedFavouritePosition);
    }
}
