package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;
import pwr.zpi.socialballspring.dto.Response.TimeStatisticsResponse;

public interface StatisticsService {
    StatisticsResponse findById();
    StatisticsResponse findByUser(long id);
    StatisticsResponse findGlobal();
    TimeStatisticsResponse findTimeStats(long monthsNumber);
}
