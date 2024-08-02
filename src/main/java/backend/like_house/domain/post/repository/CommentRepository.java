package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByPostId(Long postId);
    List<Comment> findByPostId(Long postId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.user = :user")
    void deleteByUser(@Param("user") User user);
}
