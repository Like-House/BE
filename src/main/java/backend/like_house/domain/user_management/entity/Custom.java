package backend.like_house.domain.user_management.entity;

import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.global.common.BaseEntity;
import jakarta.persistence.*;
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
public class Custom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    private String nickname;

    private String memo;

    public void setUpdateCustom(ModifyFamilyDataRequest request) {
        this.nickname = request.getNickname();
        this.memo = request.getMemo();
    }
}
