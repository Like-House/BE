package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteByPost(Post post);
    List<PostImage> findByPostId(Long postId);

    // 특정 게시글의 첫 번째 이미지를 가져오는 메소드
    @Query("SELECT pi FROM PostImage pi WHERE pi.post.id = :postId")
    Optional<PostImage> findFirstByPostId(Long postId);
}

