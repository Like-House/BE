package backend.like_house.domain.post.repository;

import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.UserPostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPostTagRepository extends JpaRepository<UserPostTag, Long> {
    void deleteByPost(Post post);
    List<UserPostTag> findByPostId(Long postId);
}
