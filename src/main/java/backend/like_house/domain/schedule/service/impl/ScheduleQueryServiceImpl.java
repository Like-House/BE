package backend.like_house.domain.schedule.service.impl;

import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.schedule.repository.ScheduleRepository;
import backend.like_house.domain.schedule.service.ScheduleQueryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Optional<Schedule> findSchedule(Long id) {
        return scheduleRepository.findById(id);
    }
}
