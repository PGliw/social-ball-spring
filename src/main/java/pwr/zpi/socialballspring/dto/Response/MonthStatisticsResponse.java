package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

@Data
public class MonthStatisticsResponse {
    private String month;
    private Long matches;
    private Long players;

    public MonthStatisticsResponse(String month, Long matches, Long players) {
        this.month = month;
        this.matches = matches;
        this.players = players;
    }
}
