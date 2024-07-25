package backend.like_house.domain.schedule.service;

import backend.like_house.domain.schedule.entity.Schedule;
import java.util.Optional;

public interface ScheduleQueryService {

    Optional<Schedule> findSchedule(Long id);
}
