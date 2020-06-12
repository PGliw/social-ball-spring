package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.Response.StatisticsResponse;

public interface StatisticsService {
    StatisticsResponse findById();
    StatisticsResponse findByUser(long id);
    StatisticsResponse findGlobal();
}
