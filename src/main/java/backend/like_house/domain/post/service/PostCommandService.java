package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCommandService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest, List<MultipartFile> files, Long userId);
    CreatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest, List<MultipartFile> files, Long userId);
    void deletePost(Long postId, Long userId);
}
