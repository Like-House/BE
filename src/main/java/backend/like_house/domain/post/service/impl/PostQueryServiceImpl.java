package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import backend.like_house.domain.post.repository.*;
import backend.like_house.domain.post.service.PostQueryService;
import backend.like_house.domain.schedule.repository.ScheduleRepository;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import backend.like_house.domain.user_management.repository.ContactRepository;
import backend.like_house.domain.user_management.repository.CustomRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final CustomRepository customRepository;
    private final ContactRepository contactRepository;
    private final UserPostTagRepository userPostTagRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, User user, Integer page, Integer size) {
        List<Post> posts = postRepository.findPostsByFamilySpaceId(familySpaceId, PageRequest.of(page, size));
        List<LocalDate> scheduledDates = scheduleRepository.findDatesWithSchedules(familySpaceId, LocalDate.now().getMonthValue());

        return posts.stream().map(post -> {
            String authorNickname = getAuthorNickname(user, post.getUser());
            String profileImage = post.getUser().getProfileImage();
            int likeCount = postLikeRepository.countByPostId(post.getId());
            int commentCount = commentRepository.countByPostId(post.getId());
            List<String> imageUrls = postImageRepository.findByPostId(post.getId())
                    .stream().map(PostImage::getFilename).collect(Collectors.toList());
            boolean owner = post.getUser().getId().equals(user.getId());
            return PostConverter.toGetPostListResponse(post, authorNickname, profileImage, likeCount, commentCount, imageUrls, owner, scheduledDates);
        }).collect(Collectors.toList());
    }

    @Override
    public GetPostDetailResponse getPostDetail(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        String authorNickname = getAuthorNickname(user, post.getUser());
        String profileImage = post.getUser().getProfileImage();
        int likeCount = postLikeRepository.countByPostId(post.getId());
        int commentCount = commentRepository.countByPostId(post.getId());
        List<String> imageUrls = postImageRepository.findByPostId(post.getId())
                .stream().map(PostImage::getFilename).collect(Collectors.toList());
        List<FamilyTagResponse> taggedUsers = userPostTagRepository.findByPostId(post.getId())
                .stream().map(tag -> new FamilyTagResponse(tag.getUser().getId(), tag.getUser().getName()))
                .collect(Collectors.toList());

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<GetCommentResponse> commentResponses = comments.stream()
                .map(comment -> {
                    String userNickname = getAuthorNickname(user, comment.getUser());
                    return CommentConverter.toGetCommentResponse(comment, userNickname);
                })
                .collect(Collectors.toList());

        return PostConverter.toGetPostDetailResponse(post, authorNickname, profileImage, likeCount, commentCount, imageUrls, taggedUsers, commentResponses);
    }

    @Override
    public MyPostCursorDataListResponse getMyPosts(User user, Long cursor, Integer size) {
        Page<Post> postPage = postRepository.findByUserIdAndIdLessThanOrderByIdDesc(
                user.getId(), cursor, PageRequest.of(0, size + 1)
        );

        List<Post> posts = postPage.getContent();
        List<GetMyPostListResponse> postResponses = posts.stream()
                .limit(size)
                .map(post -> {
                    List<FamilyTagResponse> taggedUsers = userPostTagRepository.findByPostId(post.getId())
                            .stream().map(tag -> new FamilyTagResponse(tag.getUser().getId(), tag.getUser().getName()))
                            .collect(Collectors.toList());
                    List<String> imageUrls = postImageRepository.findByPostId(post.getId())
                            .stream().map(PostImage::getFilename)
                            .collect(Collectors.toList());
                    return PostConverter.toGetMyPostListResponse(post, taggedUsers, imageUrls);
                })
                .collect(Collectors.toList());

        Long nextCursor = posts.size() > size ? posts.get(size - 1).getId() : -1L;

        return PostConverter.toMyPostCursorDataListResponse(postResponses, nextCursor);
    }

    private String getAuthorNickname(User user, User postUser) {
        Optional<Contact> contact = contactRepository.findByUserIdAndProfileId(user.getId(), postUser.getId());
        if (contact.isPresent()) {
            Optional<Custom> custom = customRepository.findByContactId(contact.get().getId());
            if (custom.isPresent()) {
                return custom.get().getNickname();
            }
        }
        return postUser.getName();
    }
}
