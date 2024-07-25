package backend.like_house.domain.schedule.entity;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.global.common.BaseEntity;
import backend.like_house.global.common.enums.ScheduleType;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_space_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FamilySpace familySpace;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleType dtype;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
