package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;

import java.util.List;

@Data
public class TimeStatisticsResponse {
    private List<MonthStatisticsResponse> timeStats;

    public TimeStatisticsResponse(List<MonthStatisticsResponse> timeStats) {
        this.timeStats = timeStats;
    }
}
