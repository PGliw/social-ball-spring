package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.Rating;

@Data
public class RatingResponse {
    private Long id;
    private Integer ratingMark;
    private String comment;
    private Boolean isAbuseReported;
    private Long senderId;
    private Long receiverId;

    public RatingResponse(Rating rating){
        this.id = rating.getId();
        this.ratingMark = rating.getRatingMark();
        this.comment = rating.getComment();
        this.isAbuseReported = rating.isAbuseReported();
        if(rating.getSender() != null){
            this.senderId = rating.getSender().getId();
        }
        if(rating.getReceiver() != null){
            this.receiverId = rating.getReceiver().getId();
        }
    }
}
