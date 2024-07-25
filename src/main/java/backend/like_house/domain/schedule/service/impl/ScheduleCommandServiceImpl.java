package backend.like_house.domain.schedule.service.impl;

import backend.like_house.domain.schedule.converter.ScheduleConverter;
import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleRequest.*;
import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.schedule.repository.ScheduleRepository;
import backend.like_house.domain.schedule.service.ScheduleCommandService;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Schedule generateNewSchedule(SaveScheduleRequest request, User user) {
        Schedule schedule = ScheduleConverter.toSchedule(request, user);
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Schedule updateSchedule(Long id, ModifyScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).get();
        Schedule updatedSchedule = ScheduleConverter.updateSchedule(schedule, request);
        return scheduleRepository.save(updatedSchedule);
    }
}
