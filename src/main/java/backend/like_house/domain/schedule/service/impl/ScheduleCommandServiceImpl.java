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
@Transactional
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule generateNewSchedule(SaveScheduleRequest request, User user) {
        Schedule schedule = ScheduleConverter.toSchedule(request, user);
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public Schedule updateSchedule(Long id, ModifyScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).get();
        schedule.setUpdateSchedule(request);
        return scheduleRepository.save(schedule);
    }
}
