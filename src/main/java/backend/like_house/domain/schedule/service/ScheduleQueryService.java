package backend.like_house.domain.schedule.service;

import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.user.entity.User;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ScheduleQueryService {

    Optional<Schedule> findSchedule(Long id);

    Page<Schedule> getScheduleByMonth(User user, YearMonth yearMonth, Integer page, Integer size);
}
