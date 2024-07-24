package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCommandService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest, List<MultipartFile> files, User user);
    CreatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest, List<MultipartFile> files, User user);
    void deletePost(Long postId, User user);
}
