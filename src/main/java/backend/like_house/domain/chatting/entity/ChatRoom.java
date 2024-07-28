package backend.like_house.domain.chatting.entity;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomRequest;
import backend.like_house.global.common.BaseEntity;
import backend.like_house.global.common.enums.ChatRoomType;
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
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_space_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FamilySpace familySpace;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatRoomType dtype;

    @Column(nullable = false)
    private String imageUrl;

    public void updateChatRoom(UpdateChatRoomRequest updateChatRoomRequest) {
        this.title = updateChatRoomRequest.getTitle();
        this.imageUrl = updateChatRoomRequest.getImageUrl();
    }
}
