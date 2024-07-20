package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.post.converter.FamilyAlbumConverter;
import backend.like_house.domain.post.dto.FamilyAlbumDTO;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.service.FamilyAlbumQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyAlbumQueryServiceImpl implements FamilyAlbumQueryService {
    @Transactional(readOnly = true)
    @Override
    public List<FamilyAlbumDTO.AlbumPhotoResponse> viewFamilyAlbum(Long familySpaceId, LocalDate date, List<Long> taggedUserIds) {
        List<Post> posts = null;

        // TODO: 가족 앨범 보기 로직
        // 1. familySpaceId와 date를 기반으로 해당 날짜에 작성된 게시글 조회
        // 2. taggedUserIds가 존재하는 경우, 해당 사용자가 태그된 게시글 필터링
        // 3. 각 게시글의 대표 이미지를 AlbumPhotoResponse로 변환하여 반환
        return posts.stream()
                .map(post -> FamilyAlbumConverter.toAlbumPhotoResponse(post, null)) // 이미지 URL은 null로 설정
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyAlbumDTO.PostPreviewResponse getPostPreview(Long postId) {
        Post post = null;
        String authorNickname = null;
        String profileImage = null;
        List<String> imageUrls = null;

        // TODO: 게시글 미리보기 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회된 게시글의 상세 정보 조회
        // 3. PostPreviewResponse로 변환하여 반환
        return FamilyAlbumConverter.toPostPreviewResponse(post, authorNickname, profileImage, imageUrls);
    }
}
