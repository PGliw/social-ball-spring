package pwr.zpi.socialballspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.dto.RatingDto;
import pwr.zpi.socialballspring.dto.Response.RatingResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.MatchMember;
import pwr.zpi.socialballspring.model.Rating;
import pwr.zpi.socialballspring.repository.MatchMemberDao;
import pwr.zpi.socialballspring.repository.RatingDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "ratingService")
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingDao ratingDao;

    @Autowired
    MatchMemberDao matchMemberDao;

    @Override
    public List<RatingResponse> findAll() {
        List<Rating> list = new ArrayList<>();
        ratingDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().map(RatingResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        ratingDao.deleteById(id);
    }

    @Override
    public RatingResponse findById(long id) {
        Optional<Rating> optionalRating = ratingDao.findById(id);
        return optionalRating.map(RatingResponse::new).orElseThrow(() -> new NotFoundException("RatingResponse"));
    }

    @Override
    public RatingResponse update(RatingDto ratingDto, long id) {
        Optional<Rating> optionalRating = ratingDao.findById(id);
        if (optionalRating.isPresent()) {
            MatchMember sender = null;
            if(ratingDto.getSenderId() != null){
                sender = matchMemberDao.findById(ratingDto.getSenderId()).get();
            }
            MatchMember receiver = null;
            if(ratingDto.getReceiverId() != null){
                receiver = matchMemberDao.findById(ratingDto.getReceiverId()).get();
            }
            Rating rating = Rating.builder()
                    .receiver(receiver)
                    .sender(sender)
                    .id(id)
                    .comment(ratingDto.getComment())
                    .ratingMark(ratingDto.getRatingMark())
                    .isAbuseReported(ratingDto.getIsAbuseReported())
                    .build();
            Rating savedRating = ratingDao.save(rating);
            return new RatingResponse(savedRating);
        } else throw new NotFoundException("Rating");
    }

    @Override
    public RatingResponse save(RatingDto ratingDto) {
        MatchMember sender = null;
        if(ratingDto.getSenderId() != null){
            sender = matchMemberDao.findById(ratingDto.getSenderId()).get();
        }
        MatchMember receiver = null;
        if(ratingDto.getReceiverId() != null){
            receiver = matchMemberDao.findById(ratingDto.getReceiverId()).get();
        }
        Rating rating = Rating.builder()
                .receiver(receiver)
                .sender(sender)
                .comment(ratingDto.getComment())
                .ratingMark(ratingDto.getRatingMark())
                .isAbuseReported(ratingDto.getIsAbuseReported())
                .build();
        Rating savedRating = ratingDao.save(rating);
        return new RatingResponse(savedRating);
    }
}
