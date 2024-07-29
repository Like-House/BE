package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.familySpace.id = :familySpaceId AND (:cursor IS NULL OR p.id < :cursor) AND p.user.id NOT IN :removedUserIds ORDER BY p.id DESC")
    List<Post> findPostsByFamilySpaceIdAndUserIdNotIn(@Param("familySpaceId") Long familySpaceId, @Param("removedUserIds") List<Long> removedUserIds, @Param("cursor") Long cursor, Pageable pageable);

    List<Post> findByUserId(Long userId);
}