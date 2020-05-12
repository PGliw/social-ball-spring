package pwr.zpi.socialballspring.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String dateOfAddition;
    private String content;
    private Long relatedMatchId;
    private Long relatedMatchMemberId;
}
