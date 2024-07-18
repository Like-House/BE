package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCommandService {
    PostDTO.PostResponse.CreatePostResponse createPost(PostDTO.PostRequest.CreatePostRequest createPostRequest, List<MultipartFile> files);
    PostDTO.PostResponse.CreatePostResponse updatePost(Long postId, PostDTO.PostRequest.UpdatePostRequest updatePostRequest, List<MultipartFile> files);
    void deletePost(Long postId, Long userId);
}
