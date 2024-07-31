package backend.like_house.global.s3.controller;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.s3.dto.AwsDTO.PresignedUrlDownLoadResponse;
import backend.like_house.global.s3.dto.AwsDTO.PresignedUrlUploadResponse;
import backend.like_house.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "Upload용 Presigned URL 생성", description = "업로드를 위한 Presigned URL을 생성한다")
    @GetMapping("/presigned/upload")
    public ApiResponse<PresignedUrlUploadResponse> getPresignedUrlToUpload(@RequestParam(value = "filename") String fileName) {
        PresignedUrlUploadResponse presignedUrlToUpload = s3Service.getPresignedUrlToUpload(fileName);
        return ApiResponse.onSuccess(presignedUrlToUpload);
    }

    @Operation(summary = "Download용 Presigned URL 생성", description = "다운로드를 위한 Presigned URL을 생성한다")
    @GetMapping("/presigned/download")
    public ApiResponse<PresignedUrlDownLoadResponse> getPresignedUrlToDownload(@RequestParam(value = "keyName") String keyName) {
        PresignedUrlDownLoadResponse presignedUrlToDownload = s3Service.getPresignedUrlToDownload(keyName);
        return ApiResponse.onSuccess(presignedUrlToDownload);
    }
}
