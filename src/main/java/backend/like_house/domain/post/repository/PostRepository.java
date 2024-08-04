package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.familySpace.id = :familySpaceId " +
            "ORDER BY p.id DESC")
    List<Post> findPostsByFamilySpaceId(@Param("familySpaceId") Long familySpaceId,
                                        Pageable pageable);

    List<Post> findByUserId(Long userId);

    List<Post> findByFamilySpaceId(Long familySpaceId);

    @Query("SELECT p FROM Post p WHERE p.familySpace.id = :familySpaceId AND DATE(p.createdAt) = :date")
    List<Post> findByFamilySpaceIdAndCreatedAt(@Param("familySpaceId") Long familySpaceId, @Param("date") LocalDate date);

    @Query("SELECT p FROM Post p WHERE p.familySpace.id = :familySpaceId AND DATE(p.createdAt) = :date AND p.id IN :postIds")
    List<Post> findByFamilySpaceIdAndCreatedAtAndPostIds(@Param("familySpaceId") Long familySpaceId, @Param("date") LocalDate date, @Param("postIds") List<Long> postIds);

    @Query("SELECT p FROM Post p WHERE p.familySpace.id = :familySpaceId AND p.id IN :postIds")
    List<Post> findByFamilySpaceIdAndPostIds(@Param("familySpaceId") Long familySpaceId, @Param("postIds") List<Long> postIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.user = :user")
    void deleteByUser(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE NOT EXISTS (SELECT upt FROM UserPostTag upt WHERE upt.post.id = p.id)")
    void deletePostsWithoutTags();
}

