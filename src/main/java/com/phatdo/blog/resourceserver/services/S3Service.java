package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.images.Image;
import com.phatdo.blog.resourceserver.repositories.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class S3Service {
    private final List<String> acceptedContentTypes = List.of("image/jpg", "image/jpeg", "image/png");

    private final S3Client s3Client;
    private final String bucketName;
    private final String bucketUrl;
    private final ImageRepository imageRepository;

    public S3Service(S3Client s3Client,
                     ImageRepository imageRepository,
                     @Value("${aws.s3.bucketName}") String bucketName,
                     @Value("${aws.s3.bucketUrl}") String bucketUrl) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.bucketUrl = bucketUrl;
        this.imageRepository = imageRepository;
    }

    public CompletableFuture<List<Image>> upload(Blog blog, List<MultipartFile> files) throws CustomException {
        List<CompletableFuture<Image>> futures = new ArrayList<>();
        for (MultipartFile file : files)
            futures.add(uploadFile(file, blog));
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).toList());
    }

    @Async
    public CompletableFuture<Image> uploadFile(MultipartFile file, Blog blog) throws CustomException {
        if (acceptedContentTypes.contains(file.getContentType())) {
            String key = file.getOriginalFilename();
            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(
                        PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build(),
                        RequestBody.fromInputStream(inputStream, file.getSize())
                );
            } catch (IOException e) {
                throw new RuntimeException("Error uploading file to S3", e);
            }

            return CompletableFuture.completedFuture(imageRepository.save(
                    new Image(getFileUrl(key), blog)
            ));
        }
        else throw new CustomException(CustomError.INVALID_FILE_CONTENT_TYPE);
    }

    private String getFileUrl(String key) {
        return String.format("%s/%s", bucketUrl, key).replace(' ', '+');
    }
}
