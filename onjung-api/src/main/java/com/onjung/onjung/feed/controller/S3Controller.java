package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.service.S3Uploader;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/{feefId}/image")
    public void updateFeedImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            System.out.println("multipartFile = " + multipartFile);
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) {
            return;
        }
        return;
    }
}
