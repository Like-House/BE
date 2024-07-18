package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.PostDTO;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.service.PostCommandService;
import backend.like_house.global.s3.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final S3Manager s3Manager;

    @Transactional
    @Override
    public PostDTO.PostResponse.CreatePostResponse createPost(PostDTO.PostRequest.CreatePostRequest createPostRequest, List<MultipartFile> files) {
        List<String> imageUrls = s3Manager.uploadFiles(files);
        Post post = null;
        // TODO: 새로운 게시글을 작성하는 로직
        // 1. 파일을 S3에 업로드하고 파일 URL 목록 반환
        // 2. createPostRequest 객체를 사용하여 게시글 생성
        // 2. 생성한 게시글을 저장하고 저장된 게시글의 상세 정보 반환
        return PostConverter.toCreatePostResponse(post);
    }

    @Transactional
    @Override
    public PostDTO.PostResponse.CreatePostResponse updatePost(Long postId, PostDTO.PostRequest.UpdatePostRequest updatePostRequest, List<MultipartFile> files) {
        List<String> imageUrls = s3Manager.uploadFiles(files);
        Post post = null;
        // TODO: 특정 게시글을 수정하는 로직
        // 1. 파일을 S3에 업로드하고 파일 URL 목록 반환
        // 2. updatePostRequest 데이터로 게시글 수정
        // 3. 수정된 게시글을 저장하고 저장된 게시글의 상세 정보 반환
        return PostConverter.toCreatePostResponse(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId, Long userId) {
        // TODO: 특정 게시글을 삭제하는 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회한 게시글 삭제
    }
}
