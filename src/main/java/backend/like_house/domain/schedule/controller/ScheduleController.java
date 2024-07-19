package backend.like_house.domain.schedule.controller;

import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleRequest.*;
import backend.like_house.domain.schedule.dto.ScheduleDTO.ScheduleResponse.*;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/schedule")
@Tag(name = "일정 공유 관련 컨트롤러")
public class ScheduleController {

    // 일정 조회 (Month) + 페이지네이션

    // 일정 조회 (Day) + 무한 스크롤

    @GetMapping("/{scheduleId}")
    @Operation(summary = "일정 단건 조회 API", description = "일정 수정을 누르면 해당하는 일정의 정보를 보여주는 API입니다. "
            + "또한 특정 id의 일정을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디, path variable 입니다.")
    })
    public ApiResponse<ScheduleDataResponse> getScheduleData(@PathVariable(name = "scheduleId") Long scheduleId) {
        // TODO id를 통해 정보 가져오기
        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/modify")
    @Operation(summary = "일정 수정 API", description = "일정 수정 완료하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    public ApiResponse<SaveScheduleResponse> updateSchedule(@RequestBody ModifyScheduleRequest request) {
        // TODO 일정 수정
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/save")
    @Operation(summary = "일정 저장 API", description = "일정을 저장하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<SaveScheduleResponse> saveSchedule(@RequestBody SaveScheduleRequest request) {
        // TODO 일정 저장
        return ApiResponse.onSuccess(null);
    }


    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "일정 삭제 API", description = "일정을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE4001", description = "존재하지 않는 일정 입니다.")
    })
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디, path variable 입니다.")
    })
    public ApiResponse<Void> deleteSchedule(@PathVariable(name = "scheduleId") Long scheduleId) {
        // TODO 일정 삭제
        return ApiResponse.onSuccess(null);
    }
}
