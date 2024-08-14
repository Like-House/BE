package backend.like_house.global.s3.controller;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.s3.dto.AwsDTO.*;
import backend.like_house.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "Upload용 Presigned URL 생성", description = "업로드를 위한 Presigned URL을 생성한다")
    @PostMapping("/presigned/upload")
    public ApiResponse<PresignedUrlUploadResponse> getPresignedUrlToUpload(@RequestParam(value = "filename") String fileName) {
        PresignedUrlUploadResponse presignedUrlToUpload = s3Service.getPresignedUrlToUpload(fileName);
        return ApiResponse.onSuccess(presignedUrlToUpload);
    }

    @Operation(summary = "Download용 Presigned URL 생성", description = "다운로드를 위한 Presigned URL을 생성한다")
    @PostMapping("/presigned/download")
    public ApiResponse<PresignedUrlDownLoadResponse> getPresignedUrlToDownload(@RequestParam(value = "keyName") String keyName) {
        PresignedUrlDownLoadResponse presignedUrlToDownload = s3Service.getPresignedUrlToDownload(keyName);
        return ApiResponse.onSuccess(presignedUrlToDownload);
    }

    @Operation(summary = "여러 개 Upload용 Presigned URL 생성", description = "업로드를 위한 Presigned URL를 여러 개 생성한다")
    @PostMapping("/presigned/upload/list")
    public ApiResponse<?> getPresignedUrlToUploadList(@RequestBody PresignedUploadRequest presignedUploadRequest) {
        PresignedUrlUploadResponseList presignedUrlToUploadList = s3Service.getPresignedUrlToUploadList(presignedUploadRequest);
        return ApiResponse.onSuccess(presignedUrlToUploadList);
    }

    @Operation(summary = "여러 개 Download용 Presigned URL 생성", description = "다운로드를 위한 Presigned URL를 여러 개 생성한다")
    @PostMapping("/presigned/download/list")
    public ApiResponse<PresignedUrlDownLoadResponseList> getPresignedUrlToDownloadList(@RequestBody DownLoadRequestList downLoadRequestList) {
        System.out.println("sewfewf");
        PresignedUrlDownLoadResponseList presignedUrlDownLoadResponseList  = s3Service.getPresignedUrlToDownloadList(downLoadRequestList);
        return ApiResponse.onSuccess(presignedUrlDownLoadResponseList);
    }
}
