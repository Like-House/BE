package backend.like_house.global.s3.controller;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.s3.dto.AwsDTO;
import backend.like_house.global.s3.dto.AwsDTO.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/s3")
public class S3Controller {

    @Operation(summary = "Upload용 Presigned URL 생성", description = "업로드를 위한 Presigned URL을 생성한다")
    @GetMapping("/presigned/upload")
    public ApiResponse<PresignedUrlResponse> getPresignedUrlToUpload(@RequestParam(value = "filename") String fileName) {
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "Download용 Presigned URL 생성", description = "다운로드를 위한 Presigned URL을 생성한다")
    @GetMapping("/presigned/download")
    public ApiResponse<PresignedUrlResponse> getPresignedUrlToDownload(@RequestParam(value = "filename") String fileName) {
        return ApiResponse.onSuccess(null);
    }
}
