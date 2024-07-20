package backend.like_house.domain.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScheduleDTO {

    public static class ScheduleRequest {

        @Getter
        public static class ModifyScheduleRequest {
            @NotNull
            @Schema(description = "수정할 일정 아이디", example = "1")
            private Long id;
            @Schema(description = "수정할 일정 날짜")
            private LocalDate date;
            @Schema(description = "수정할 일정 유형", example = "생일")
            private String dtype;
            @Schema(description = "수정할 일정 제목", example = "제목")
            private String title;
            @Schema(description = "수정할 일정 내용", example = "내용")
            private String content;
        }

        @Getter
        public static class SaveScheduleRequest {
            @NotNull
            @Schema(description = "저장할 일정 날짜")
            private LocalDate date;
            @NotBlank
            @Schema(description = "저장할 일정 유형", example = "생일")
            private String dtype;
            @NotBlank
            @Schema(description = "저장할 일정 제목", example = "제목")
            private String title;
            @NotBlank
            @Schema(description = "저장할 일정 내용", example = "내용")
            private String content;
        }
    }

    public static class ScheduleResponse {

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ScheduleDataListResponse {
            private List<ScheduleDataResponse> scheduleDataResponseList;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ScheduleDataResponse {
            @Schema(description = "조회한 일정 아이디", example = "1")
            private Long id;
            @Schema(description = "조회한 일정 날짜")
            private LocalDate date;
            @Schema(description = "조회한 일정 타입", example = "생일")
            private String dtype;
            @Schema(description = "조회한 일정 제목", example = "제목")
            private String title;
            @Schema(description = "조회한 일정 내용", example = "내용")
            private String content;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class SaveScheduleResponse {
            @Schema(description = "저장/수정한 일정 아이디", example = "1")
            private Long id;
            @Schema(description = "일정이 만들어진 시간")
            private LocalDateTime createdAt;
        }
    }
}
