package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.UserPostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPostTagRepository extends JpaRepository<UserPostTag, Long> {
    void deleteByPost(Post post);
    List<UserPostTag> findByPostId(Long postId);

    @Query("SELECT tag.post.id FROM UserPostTag tag WHERE tag.user.id IN :userIds")
    List<Long> findPostIdsByUserIds(@Param("userIds") List<Long> userIds);

    @Query("SELECT tag.user.id FROM UserPostTag tag WHERE tag.post.id = :postId")
    List<Long> findUserIdsByPostId(@Param("postId") Long postId);
}

