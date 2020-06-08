package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

@Data
public class StatisticsResponse {
    private Long goalsScored;
    private Long matchesPlayed;
    private Long hoursPlayed;

    public StatisticsResponse(Long goalsScored, Long matchesPlayed, Long hoursPlayed){
        this.goalsScored = goalsScored;
        this.matchesPlayed = matchesPlayed;
        this.hoursPlayed = hoursPlayed;
    }
}
