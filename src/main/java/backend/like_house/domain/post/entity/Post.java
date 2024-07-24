package backend.like_house.domain.post.entity;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.BaseEntity;
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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_space_id")
    private FamilySpace familySpace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post_image", cascade = CascadeType.REMOVE)
    private List<PostImage> postImages = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post_like", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user_post_tag", cascade = CascadeType.REMOVE)
    private List<UserPostTag> userPostTags = new ArrayList<>();
}
