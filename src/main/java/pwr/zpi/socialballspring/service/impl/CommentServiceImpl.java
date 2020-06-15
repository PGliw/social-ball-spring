package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.config.IdentityManager;
import pwr.zpi.socialballspring.dto.CommentDto;
import pwr.zpi.socialballspring.dto.Response.CommentResponse;
import pwr.zpi.socialballspring.exception.BadRequestException;
import pwr.zpi.socialballspring.exception.NotFoundException;
import pwr.zpi.socialballspring.model.Comment;
import pwr.zpi.socialballspring.model.FootballMatch;
import pwr.zpi.socialballspring.model.MatchMember;
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
        if (matchId.isPresent()) {
            comments = comments.filter(c -> c.getRelatedMatch().getId().equals(matchId.get()));
        }
        if (userId.isPresent()) {
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
            if (commentDto.getRelatedMatchId() != null) {
                footballMatch = footballMatchDao.findById(commentDto.getRelatedMatchId()).get();
            }
            MatchMember matchMember = null;
            if (commentDto.getRelatedMatchMemberId() != null) {
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
        final LocalDateTime dateOfAddition = dateUtils.convertFromString(commentDto.getDateOfAddition());
        FootballMatch footballMatch;
        if (commentDto.getRelatedMatchId() != null) {
            final Optional<FootballMatch> optionalFootballMatch = footballMatchDao.findById(commentDto.getRelatedMatchId());
            if (optionalFootballMatch.isPresent()) {
                footballMatch = optionalFootballMatch.get();
            } else {
                throw new NotFoundException("FootballMatch");
            }
        } else {
            throw new BadRequestException("relatedMatchId is null!");
        }
        MatchMember matchMember;
        if (commentDto.getRelatedMatchMemberId() != null) {
            final Optional<MatchMember> optionalMatchMember= matchMemberDao.findById(commentDto.getRelatedMatchMemberId());
            if (optionalMatchMember.isPresent()) {
                matchMember = optionalMatchMember.get();
            } else {
                throw new NotFoundException("MatchMember");
            }
        } else {
            throw new BadRequestException("relatedMatchMemberId is null!");
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
