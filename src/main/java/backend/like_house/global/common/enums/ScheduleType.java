package backend.like_house.global.common.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ScheduleType {
    BIRTHDAY("생일"),
    FAMILY_EVENT("가족 행사"),
    PERSONAL("개인 일정"),
    MEMORIAL_DAY("기일");

    private final String koreanName;

    ScheduleType(String koreanName) {
        this.koreanName = koreanName;
    }

    private static final Map<String, ScheduleType> BY_KOREAN_NAME =
            Stream.of(values()).collect(Collectors.toMap(ScheduleType::getKoreanName, e -> e));

    public static ScheduleType valueOfKoreanName(String koreanName) {
        return BY_KOREAN_NAME.get(koreanName);
    }
}
