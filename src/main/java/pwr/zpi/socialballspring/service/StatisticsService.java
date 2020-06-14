package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.dto.Response.TimeStatisticsResponse;

public interface StatisticsService {
    StatisticsResponse findById(Long id);
    StatisticsResponse findByCurrentUser();
    StatisticsResponse findGlobal();
    TimeStatisticsResponse findTimeStats(long monthsNumber);
}
