package backend.like_house.domain.family_space.entity;

import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.entity.BlockUser;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.global.common.BaseEntity;
import backend.like_house.domain.user_management.entity.RemoveUser;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FamilySpace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<FamilyEmoticon> familyEmoticons = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<BlockUser> blockUsers = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<RemoveUser> removeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "familySpace", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();
}
