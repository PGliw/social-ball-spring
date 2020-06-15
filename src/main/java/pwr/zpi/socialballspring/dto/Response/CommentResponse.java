package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.Comment;

@Data
public class CommentResponse {
    private Long id;
    private String dateOfAddition;
    private String content;
    private Long relatedMatchId;
    private MatchMemberResponse relatedMatchMember;

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
            this.relatedMatchMember = new MatchMemberResponse(comment.getRelatedMatchMember());
        }
    }
}
