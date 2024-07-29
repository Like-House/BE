package backend.like_house.domain.post.service.impl;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.entity.*;
import backend.like_house.domain.post.repository.PostImageRepository;
import backend.like_house.domain.post.repository.PostLikeRepository;
import backend.like_house.domain.post.repository.PostRepository;
import backend.like_house.domain.post.repository.UserPostTagRepository;
import backend.like_house.domain.post.service.PostCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilySpaceException;
import backend.like_house.global.error.handler.PostException;
import backend.like_house.global.s3.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final S3Manager s3Manager;
    private final PostRepository postRepository;
    private final FamilySpaceRepository familySpaceRepository;
    private final PostImageRepository postImageRepository;
    private final UserPostTagRepository userPostTagRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest createPostRequest, List<MultipartFile> files, User user) {
        List<String> imageUrls = s3Manager.uploadFiles(files);

        FamilySpace familySpace = familySpaceRepository.findById(createPostRequest.getFamilySpaceId())
                .orElseThrow(() -> new FamilySpaceException(ErrorStatus.FAMILY_SPACE_NOT_FOUND));

        Post post = Post.builder()
                .familySpace(familySpace)
                .user(user)
                .content(createPostRequest.getContent())
                .build();

        post = postRepository.save(post);

        Post finalPost = post;
        List<PostImage> postImages = imageUrls.stream()
                .map(url -> PostImage.builder().post(finalPost).filename(url).build())
                .collect(Collectors.toList());

        postImageRepository.saveAll(postImages);

        List<UserPostTag> userPostTags = createPostRequest.getTaggedUserIds().stream()
                .map(taggedUser -> UserPostTag.builder()
                        .user(User.builder().id(taggedUser.getUserId()).build())
                        .post(finalPost)
                        .build())
                .collect(Collectors.toList());

        userPostTagRepository.saveAll(userPostTags);

        return PostConverter.toCreatePostResponse(post);
    }

    @Transactional
    @Override
    public CreatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest, List<MultipartFile> files, User user) {
        List<String> imageUrls = s3Manager.uploadFiles(files);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostException(ErrorStatus.INVALID_ACCESS);
        }

        post.setContent(updatePostRequest.getContent());

        // 기존 이미지 및 태그 삭제
        postImageRepository.deleteByPost(post);
        userPostTagRepository.deleteByPost(post);

        List<PostImage> postImages = imageUrls.stream()
                .map(url -> PostImage.builder().post(post).filename(url).build())
                .collect(Collectors.toList());

        postImageRepository.saveAll(postImages);

        List<UserPostTag> userPostTags = updatePostRequest.getTaggedUserIds().stream()
                .map(taggedUser -> UserPostTag.builder()
                        .user(User.builder().id(taggedUser.getUserId()).build())
                        .post(post)
                        .build())
                .collect(Collectors.toList());

        userPostTagRepository.saveAll(userPostTags);

        return PostConverter.toCreatePostResponse(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        if (!post.getUser().equals(user)) {
            throw new PostException(ErrorStatus.INVALID_ACCESS);
        }

        postRepository.delete(post);
    }

    @Transactional
    @Override
    public void likePost(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        boolean alreadyLiked = postLikeRepository.existsByUserAndPost(user, post);
        if (alreadyLiked) {
            throw new PostException(ErrorStatus.ALREADY_LIKED);
        }

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

        postLikeRepository.save(postLike);
    }
  
    @Transactional
    @Override
    public void unlikePost(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new PostException(ErrorStatus.NOT_LIKED));

        postLikeRepository.delete(postLike);
    }

    @Transactional
    @Override
    public void togglePostAlarm(User user, Long postId, Boolean enable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostException(ErrorStatus.INVALID_ACCESS);
        }

        post.setPostAlarm(enable);
        postRepository.save(post);
    }
}
