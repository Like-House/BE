package backend.like_house.domain.schedule.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.schedule.repository.ScheduleRepository;
import backend.like_house.domain.schedule.service.ScheduleQueryService;
import backend.like_house.domain.user.entity.User;
import java.time.YearMonth;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public Page<Schedule> getScheduleByMonth(User user, YearMonth yearMonth, Integer page, Integer size) {
        FamilySpace familySpace = user.getFamilySpace();
        return scheduleRepository.findAllByFamilySpaceAndDateBetween(
                familySpace, yearMonth.atDay(1), yearMonth.atEndOfMonth(), PageRequest.of(page, size));
    }
}
