package backend.like_house.domain.user.entity;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.schedule.dto.ScheduleDTO;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.global.common.BaseEntity;
import backend.like_house.global.common.enums.ScheduleType;
import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_space_id")
    private FamilySpace familySpace;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    private LocalDate birthDate;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인 : null)

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean chatAlarm;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean commentAlarm;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean commentReplyAlarm;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean eventAlarm;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRoomManager;

    private String fcmToken;

    public void setFamilySpace(FamilySpace familySpace) {
        this.familySpace = familySpace;
    }

    public void setIsRoomManager(Boolean isRoomManager) {
        this.isRoomManager = isRoomManager;
    }

    public void setUpdateUserProfile(UserDTO.UpdateProfileRequest request) {
        this.name = request.getName() != null ? request.getName() : this.name;
        this.profileImage = request.getImageKeyName() != null ? request.getImageKeyName() : this.getProfileImage();
        this.birthDate = request.getBirthDate() != null ? request.getBirthDate() : this.getBirthDate();
    }

    public void setUpdateUserPassword(String newPassword) {
        this.password = newPassword;
    }

    public void commentAlarmSetting() {
        this.commentAlarm = !this.commentAlarm;
    }

    public void commentReplyAlarmSetting() {
        this.commentReplyAlarm = !this.commentReplyAlarm;
    }

    public void eventAlarmSetting() {
        this.eventAlarm = !this.eventAlarm;
    }

    public void chatAlarmSetting() {
        this.chatAlarm = !this.chatAlarm;
    }
}
