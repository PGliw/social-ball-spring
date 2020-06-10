package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

@Data
public class StatisticsResponse {
    private Long goalsScored;
    private Long matchesPlayed;
    private Long hoursPlayed;
    private Long yellowCardsReceived;
    private Long redCardsReceived;
    private Long fauls;

    public StatisticsResponse(Long goalsScored, Long matchesPlayed, Long hoursPlayed, Long yellowCardsReceived, Long redCardsReceived, Long fauls){
        this.goalsScored = goalsScored;
        this.matchesPlayed = matchesPlayed;
        this.hoursPlayed = hoursPlayed;
        this.yellowCardsReceived = yellowCardsReceived;
        this.redCardsReceived = redCardsReceived;
        this.fauls = fauls;
    }
}
