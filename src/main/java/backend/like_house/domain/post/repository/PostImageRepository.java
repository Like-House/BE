package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteByPost(Post post);
    List<PostImage> findByPostId(Long postId);
}
