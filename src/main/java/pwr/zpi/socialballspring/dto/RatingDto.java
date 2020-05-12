package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long id;
    private Integer ratingMark;
    private String comment;
    private Boolean isAbuseReported;
    private Long senderId;
    private Long receiverId;
}
