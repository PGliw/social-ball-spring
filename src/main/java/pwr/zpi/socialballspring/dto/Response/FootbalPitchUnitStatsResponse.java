package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

@Data
public class FootbalPitchUnitStatsResponse {
    private FootballPitchResponse footballPitchResponse;
    private double percentage;

    public FootbalPitchUnitStatsResponse(FootballPitchResponse footballPitchResponse, double percentage) {
        this.footballPitchResponse = footballPitchResponse;
        this.percentage = percentage;
    }
}
