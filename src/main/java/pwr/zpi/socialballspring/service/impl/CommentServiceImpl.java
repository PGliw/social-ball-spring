package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IdentityManager;
import pwr.zpi.socialballspring.dto.CommentDto;
import pwr.zpi.socialballspring.dto.Response.CommentResponse;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.*;
import pwr.zpi.socialballspring.repository.CommentDao;
import pwr.zpi.socialballspring.repository.FootballMatchDao;
import pwr.zpi.socialballspring.repository.MatchMemberDao;
import pwr.zpi.socialballspring.service.CommentService;
import pwr.zpi.socialballspring.util.dateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    MatchMemberDao matchMemberDao;

    @Autowired
    FootballMatchDao footballMatchDao;

    @Autowired
    IdentityManager identityManager;

    @Override
    public List<CommentResponse> findAll(Optional<Long> matchId, Optional<Long> userId) {
        List<Comment> list = new ArrayList<>();
        commentDao.findAll().iterator().forEachRemaining(list::add);
        Stream<Comment> comments = list.stream();
        if(matchId.isPresent()){
            comments = comments.filter(c -> c.getRelatedMatch().getId().equals(matchId.get()));
        }
        if(userId.isPresent()){
            comments = comments.filter(c -> c.getRelatedMatchMember().getUser().getId().equals(userId.get()));
        }
        return comments.map(CommentResponse::new).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        commentDao.deleteById(id);
    }

    @Override
    public CommentResponse findById(long id) {
        Optional<Comment> optionalComment = commentDao.findById(id);
        return optionalComment.map(CommentResponse::new).orElseThrow(() -> new NotFoundException("CommentResponse"));
    }

    @Override
    public CommentResponse update(CommentDto commentDto, long id) {
        Optional<Comment> optionalComment = commentDao.findById(id);
        if (optionalComment.isPresent()) {
            LocalDateTime dateOfAddition = dateUtils.convertFromString(commentDto.getDateOfAddition());
            FootballMatch footballMatch = null;
            if(commentDto.getRelatedMatchId() != null){
                footballMatch = footballMatchDao.findById(commentDto.getRelatedMatchId()).get();
            }
            MatchMember matchMember = null;
            if(commentDto.getRelatedMatchMemberId() != null) {
                matchMember = matchMemberDao.findById(commentDto.getRelatedMatchMemberId()).get();
            }
            Comment comment = Comment.builder()
                    .relatedMatch(footballMatch)
                    .relatedMatchMember(matchMember)
                    .id(id)
                    .dateOfAddition(dateOfAddition)
                    .content(commentDto.getContent())
                    .build();
            Comment savedComment = commentDao.save(comment);
            return new CommentResponse(savedComment);
        } else throw new NotFoundException("Comment");
    }

    @Override
    public CommentResponse save(CommentDto commentDto) {
        LocalDateTime dateOfAddition = dateUtils.convertFromString(commentDto.getDateOfAddition());
        FootballMatch footballMatch = null;
        if(commentDto.getRelatedMatchId() != null){
            footballMatch = footballMatchDao.findById(commentDto.getRelatedMatchId()).get();
        }
        MatchMember matchMember = null;
        List<MatchMember> matchMembers = new ArrayList<>();
        matchMemberDao.findAll().iterator().forEachRemaining(matchMembers::add);
        FootballMatch finalFootballMatch = footballMatch;
        if(footballMatch != null) {
            Optional<MatchMember> matchMemberOpt = matchMembers.stream()
                    .filter(m -> m.getUser().getId().equals(identityManager.getCurrentUser().getId()))
                    .filter(m -> m.getFootballMatch().getId().equals(finalFootballMatch.getId()))
                    .findFirst();
            if(matchMemberOpt.isPresent()){
                matchMember = matchMemberOpt.get();
            }
        }
        Comment comment = Comment.builder()
                .relatedMatch(footballMatch)
                .relatedMatchMember(matchMember)
                .dateOfAddition(dateOfAddition)
                .content(commentDto.getContent())
                .build();
        Comment savedComment = commentDao.save(comment);
        return new CommentResponse(savedComment);
    }
}
