package backend.like_house.domain.schedule.service;

import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleRequest.*;
import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.user.entity.User;

public interface ScheduleCommandService {

    Schedule generateNewSchedule(SaveScheduleRequest request, User user);

    void deleteSchedule(Long id);

    Schedule updateSchedule(Long id, ModifyScheduleRequest request);
}
