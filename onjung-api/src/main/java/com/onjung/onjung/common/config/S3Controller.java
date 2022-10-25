package com.onjung.onjung.common.config;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/{userId}/image")
    public void updateUserImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) {
            return;
        }
        return;
    }
}
