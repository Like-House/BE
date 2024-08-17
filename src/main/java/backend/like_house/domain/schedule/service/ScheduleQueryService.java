package backend.like_house.domain.schedule.service;

import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.user.entity.User;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ScheduleQueryService {

    Optional<Schedule> findSchedule(Long id);

    List<Schedule> getScheduleByMonth(User user, YearMonth yearMonth);

    Page<Schedule> getScheduleByDay(User user, LocalDate date, Long cursor, Integer size);
}
