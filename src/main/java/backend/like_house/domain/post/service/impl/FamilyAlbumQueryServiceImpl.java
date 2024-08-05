package backend.like_house.domain.post.service.impl;
import backend.like_house.domain.post.dto.FamilyAlbumDTO.*;
import backend.like_house.domain.post.converter.FamilyAlbumConverter;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import backend.like_house.domain.post.repository.PostImageRepository;
import backend.like_house.domain.post.repository.PostRepository;
import backend.like_house.domain.post.repository.UserPostTagRepository;
import backend.like_house.domain.post.service.FamilyAlbumQueryService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyAlbumQueryServiceImpl implements FamilyAlbumQueryService {

    private final PostRepository postRepository;
    private final UserPostTagRepository userPostTagRepository;
    private final PostImageRepository postImageRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AlbumPhotoResponse> viewFamilyAlbum(Long familySpaceId, LocalDate date, List<Long> taggedUserIds) {
        List<Post> posts;

        if (date == null && (taggedUserIds == null || taggedUserIds.isEmpty())) {
            // 1. 날짜X 태그X -> 모든 게시글 사진 조회 (디폴트)
            posts = postRepository.findByFamilySpaceId(familySpaceId);
        } else if (date != null && (taggedUserIds == null || taggedUserIds.isEmpty())) {
            // 2. 날짜O 태그X -> 해당 날짜에 대한 게시글 사진 조회
            posts = postRepository.findByFamilySpaceIdAndCreatedAt(familySpaceId, date);
        } else if (date != null) {
            // 3. 날짜O 태그O -> 해당 조건에 맞는 게시글 사진 조회
            List<Long> postIds = userPostTagRepository.findPostIdsByUserIds(taggedUserIds);
            posts = postRepository.findByFamilySpaceIdAndCreatedAtAndPostIds(familySpaceId, date, postIds);
            posts = filterPostsByAllTags(posts, taggedUserIds);
        } else {
            // 4. 날짜X 태그O -> 해당 태그에 대한 게시글 사진 조회
            List<Long> postIds = userPostTagRepository.findPostIdsByUserIds(taggedUserIds);
            posts = postRepository.findByFamilySpaceIdAndPostIds(familySpaceId, postIds);
            posts = filterPostsByAllTags(posts, taggedUserIds);
        }

        // 각 게시글의 대표 이미지를 AlbumPhotoResponse로 변환하여 반환
        return posts.stream()
                .map(post -> {
                    List<PostImage> postImages = postImageRepository.findFirstByPostId(post.getId());
                    PostImage representativeImage = postImages.isEmpty() ? null : postImages.get(0);
                    String representativeImageUrl = (representativeImage != null) ? representativeImage.getFilename() : null;
                    return FamilyAlbumConverter.toAlbumPhotoResponse(post, representativeImageUrl);
                })
                .collect(Collectors.toList());
    }

    private List<Post> filterPostsByAllTags(List<Post> posts, List<Long> taggedUserIds) {
        return posts.stream()
                .filter(post -> {
                    List<Long> postTagUserIds = userPostTagRepository.findUserIdsByPostId(post.getId());
                    return taggedUserIds.stream().allMatch(postTagUserIds::contains);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostPreviewResponse getPostPreview(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));
        String authorNickname = post.getUser().getName();
        String profileImage = post.getUser().getProfileImage();
        List<String> imageUrls = postImageRepository.findByPostId(post.getId()).stream()
                .map(PostImage::getFilename)
                .collect(Collectors.toList());

        return FamilyAlbumConverter.toPostPreviewResponse(post, authorNickname, profileImage, imageUrls);
    }
}

