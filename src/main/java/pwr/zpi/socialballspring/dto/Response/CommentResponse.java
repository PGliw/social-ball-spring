package pwr.zpi.socialballspring.dto.Response;

import pwr.zpi.socialballspring.model.Comment;

public class CommentResponse {
    private Long id;
    private String dateOfAddition;
    private String content;
    private Long relatedMatchId;
    private Long relatedMatchMemberId;

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
            this.relatedMatchMemberId = comment.getRelatedMatchMember().getId();
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

    public Long getRelatedMatchMemberId() {
        return relatedMatchMemberId;
    }
}
