package backend.like_house.domain.user.entity;

import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.UserChatRoom;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.UserPostTag;
import backend.like_house.domain.user_management.entity.BlockUser;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.global.common.BaseEntity;
import backend.like_house.domain.user_management.entity.RemoveUser;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String profileImage;

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

    private String socialName;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "block_user", cascade = CascadeType.REMOVE)
    private List<BlockUser> blockUsers = new ArrayList<>();

    @OneToMany(mappedBy = "remove_user", cascade = CascadeType.REMOVE)
    private List<RemoveUser> removeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user_post_tag", cascade = CascadeType.REMOVE)
    private List<UserPostTag> userPostTags = new ArrayList<>();

    @OneToMany(mappedBy = "user_chat_room", cascade = CascadeType.REMOVE)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    public void setFamilySpace(FamilySpace familySpace) {
        this.familySpace = familySpace;
    }

    public void setIsRoomManager(Boolean isRoomManager) {
        this.isRoomManager = isRoomManager;
    }
}
