package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Comment;

public class CommentResponse {
    private Long id;
    private String dateOfAddition;
    private String content;
    private Long relatedMatchId;
    private MatchMemberResponse relatedMatchMemberId;

    public CommentResponse(Comment comment){
        this.id = comment.getId();
        if(comment.getDateOfAddition() != null){
            this.dateOfAddition = comment.getDateOfAddition().toString();
        }
        this.content = comment.getContent();
        if(comment.getRelatedMatch() != null){
            this.relatedMatchId = comment.getRelatedMatch().getId();
        }
        if(comment.getRelatedMatchMember() != null){
            this.relatedMatchMemberId = new MatchMemberResponse(comment.getRelatedMatchMember());
        }
    }

    public Long getId() {
        return id;
    }

    public String getDateOfAddition() {
        return dateOfAddition;
    }

    public String getContent() {
        return content;
    }

    public Long getRelatedMatchId() {
        return relatedMatchId;
    }

    public MatchMemberResponse getRelatedMatchMemberId() {
        return relatedMatchMemberId;
    }
}
