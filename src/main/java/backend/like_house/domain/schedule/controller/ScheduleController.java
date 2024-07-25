package backend.like_house.domain.schedule.controller;

import backend.like_house.domain.schedule.converter.ScheduleConverter;
import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleRequest.*;
import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleResponse.*;
import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.schedule.service.ScheduleCommandService;
import backend.like_house.domain.schedule.service.ScheduleQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.ExistSchedule;
import backend.like_house.global.validation.annotation.HasFamilySpaceUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v0/schedules")
@Tag(name = "일정 공유 관련 컨트롤러")
public class ScheduleController {

    private final ScheduleQueryService scheduleQueryService;
    private final ScheduleCommandService scheduleCommandService;

    @GetMapping("/month")
    @Operation(summary = "달별 일정 조회 API", description = "특정 달의 일정들을 조회하는 API입니다. "
            + ", 페이징을 포함합니다. query string 으로 yearMonth와 page 번호를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    @Parameters({
            @Parameter(name = "yearMonth", description = "연도와 월, yyyy-MM 형식입니다. query string 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다. query string 입니다.")
    })
    public ApiResponse<ScheduleDataListResponse> getScheduleByMonth(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @RequestParam(name = "yearMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestParam(name = "page") Integer page) {
        // TODO 일정 조회 (Month) + 페이지네이션
        // TODO checkPage 어노테이션 만들기
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/date")
    @Operation(summary = "날짜별 일정 조회 API", description = "특정 날짜의 일정들을 조회하는 API입니다. "
            + "스크롤을 포함합니다. query string 으로 특정 날짜, 커서, 가져올 개수를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    @Parameters({
            @Parameter(name = "date", description = "날짜, yyyy-MM-dd 형식입니다. query string 입니다."),
            @Parameter(name = "cursor", description = "커서, 마지막으로 받은 일정의 ID입니다. query string 입니다."),
            @Parameter(name = "take", description = "가져올 일정의 개수입니다. query string 입니다."),
    })
    public ApiResponse<ScheduleDataListResponse> getScheduleByDay(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "cursor") Integer cursor,
            @RequestParam(name = "take") Integer take) {
        // TODO 일정 조회 (Day) + 무한 스크롤
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "일정 단건 조회 API", description = "일정 수정을 누르면 해당하는 일정의 정보를 보여주는 API입니다. "
            + "또한 특정 id의 일정을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디, path variable 입니다.")
    })
    public ApiResponse<ScheduleDataResponse> getScheduleData(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @PathVariable(name = "scheduleId") @ExistSchedule Long scheduleId) {
        Optional<Schedule> schedule = scheduleQueryService.findSchedule(scheduleId);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleDataResponse(schedule.get()));
    }

    @PatchMapping("/{scheduleId}")
    @Operation(summary = "일정 수정 API", description = "일정 수정 완료하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디, path variable 입니다.")
    })
    public ApiResponse<SaveScheduleResponse> updateSchedule(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @PathVariable(name = "scheduleId") @ExistSchedule Long scheduleId,
            @RequestBody @Valid ModifyScheduleRequest request) {
        Schedule schedule = scheduleCommandService.updateSchedule(scheduleId, request);
        return ApiResponse.onSuccess(ScheduleConverter.toSaveScheduleResponse(schedule));
    }

    @PostMapping("")
    @Operation(summary = "일정 저장 API", description = "새로운 일정을 저장하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<SaveScheduleResponse> saveSchedule(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @RequestBody @Valid SaveScheduleRequest request) {
        Schedule schedule = scheduleCommandService.generateNewSchedule(request, user);
        return ApiResponse.onSuccess(ScheduleConverter.toSaveScheduleResponse(schedule));
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "일정 삭제 API", description = "일정을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디, path variable 입니다.")
    })
    public ApiResponse<String> deleteSchedule(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user,
            @PathVariable(name = "scheduleId") @ExistSchedule Long scheduleId) {
        scheduleCommandService.deleteSchedule(scheduleId);
        return ApiResponse.onSuccess("Schedule successfully deleted.");
    }
}
