package backend.like_house.domain.schedule.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.schedule.entity.Schedule;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByFamilySpaceAndDateBetween(FamilySpace familySpace, LocalDate startDate, LocalDate endDate,
                                                      PageRequest pageRequest);

    Page<Schedule> findAllByFamilySpaceAndDateAndIdLessThanOrderByIdDesc(FamilySpace familySpace, LocalDate date,
                                                                         Long cursor, PageRequest pageRequest);

    @Query("SELECT s.date FROM Schedule s WHERE s.familySpace.id = :familySpaceId AND MONTH(s.date) = :month")
    List<LocalDate> findDatesWithSchedules(Long familySpaceId, int month);
}
