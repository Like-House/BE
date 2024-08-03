package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.user.entity.User;

public interface PostCommandService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest, User user);
    CreatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest, User user);
    void deletePost(Long postId, User user);
    void likePost(User user, Long postId);
    void unlikePost(User user, Long postId);
    void togglePostAlarm(User user, Long postId, Boolean enable);
}

