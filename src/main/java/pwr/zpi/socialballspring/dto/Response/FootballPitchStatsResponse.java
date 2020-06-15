package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

import java.util.List;

@Data
public class FootballPitchStatsResponse {
    private List<FootbalPitchUnitStatsResponse> footbalPitchUnitStatsResponses;

    public FootballPitchStatsResponse(List<FootbalPitchUnitStatsResponse> footbalPitchUnitStatsResponses) {
        this.footbalPitchUnitStatsResponses = footbalPitchUnitStatsResponses;
    }
}
