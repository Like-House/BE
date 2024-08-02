package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostLike;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPostId(Long postId);
    boolean existsByUserAndPost(User user, Post post);
    Optional<PostLike> findByUserAndPost(User user, Post post);

    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.user = :user")
    void deleteByUser(@Param("user") User user);
}
