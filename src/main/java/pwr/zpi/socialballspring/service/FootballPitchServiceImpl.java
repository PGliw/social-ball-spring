package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.FootballPitchDto;
import pwr.zpi.socialballspring.dto.Response.FootballPitchResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.FootballPitch;
import pwr.zpi.socialballspring.repository.FootballPitchDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "footballPitchService")
public class FootballPitchServiceImpl implements FootballPitchService{

    @Autowired
    FootballPitchDao footballPitchDao;

    public List<FootballPitchResponse> findAll() {
        List<FootballPitch> list = new ArrayList<>();
        footballPitchDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(FootballPitchResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        footballPitchDao.deleteById(id);
    }

    @Override
    public FootballPitchResponse findById(long id) {
        Optional<FootballPitch> optionalFootballMatch = footballPitchDao.findById(id);
        return optionalFootballMatch.map(FootballPitchResponse::new).orElseThrow(() -> new NotFoundException("FootballPitchResponse"));
    }

    @Override
    public FootballPitchResponse update(FootballPitchDto footballPitchDto, long id) {
        Optional<FootballPitch> optionalFootballMatch = footballPitchDao.findById(id);
        if (optionalFootballMatch.isPresent()) {
            FootballPitch footballPitch = FootballPitch.builder()
                    .name(footballPitchDto.getName())
                    .isPayable(footballPitchDto.getIsPayable())
                    .latitude(footballPitchDto.getLatitude())
                    .longitude(footballPitchDto.getLongitude())
                    .isReservationRequired(footballPitchDto.getIsReservationRequired())
                    .typeOfSurface(footballPitchDto.getTypeOfSurface())
                    .website(footballPitchDto.getWebsite())
                    .image(footballPitchDto.getImage())
                    .id(id)
                    .build();
            FootballPitch savedFootballPitch = footballPitchDao.save(footballPitch);
            return new FootballPitchResponse(savedFootballPitch);
        } else throw new NotFoundException("FootballPitch");
    }

    @Override
    public FootballPitchResponse save(FootballPitchDto footballPitchDto) {
        FootballPitch newFootballPitch = FootballPitch.builder()
                .name(footballPitchDto.getName())
                .isPayable(footballPitchDto.getIsPayable())
                .latitude(footballPitchDto.getLatitude())
                .longitude(footballPitchDto.getLongitude())
                .isReservationRequired(footballPitchDto.getIsReservationRequired())
                .typeOfSurface(footballPitchDto.getTypeOfSurface())
                .website(footballPitchDto.getWebsite())
                .image(footballPitchDto.getImage())
                .build();
        return new FootballPitchResponse(footballPitchDao.save(newFootballPitch));
    }
}
