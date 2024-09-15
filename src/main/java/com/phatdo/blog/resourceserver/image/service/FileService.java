package com.phatdo.blog.resourceserver.image.service;

import com.phatdo.blog.resourceserver.blog.model.Blog;
import com.phatdo.blog.resourceserver.blog.request.FileDetail;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomError;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.image.model.Image;
import com.phatdo.blog.resourceserver.image.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService {
    private final List<String> acceptedContentTypes = List.of("image/jpg", "image/jpeg", "image/png");

    private final S3Client s3Client;
    private final String bucketName;
    private final String bucketUrl;
    private final ImageRepository imageRepository;

    public FileService(S3Client s3Client,
                       ImageRepository imageRepository,
                       @Value("${aws.s3.bucketName}") String bucketName,
                       @Value("${aws.s3.bucketUrl}") String bucketUrl) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.bucketUrl = bucketUrl;
        this.imageRepository = imageRepository;
    }

    public CompletableFuture<List<Image>> upload(List<MultipartFile> files) throws CustomException {
        ConcurrentHashMap<Integer, CompletableFuture<Image>> futureMap = new ConcurrentHashMap<>();
        for (int i = 0; i < files.size(); i++)
            futureMap.put(i, uploadFile(files.get(i)));

        return CompletableFuture.allOf(futureMap.values().toArray(new CompletableFuture[0]))
                .thenApply(v -> futureMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(entry -> entry.getValue().join())
                        .collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<Image> uploadFile(MultipartFile file) throws CustomException {
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
                    new Image(getFileUrl(key))
            ));
        }
        else throw new CustomException(CustomError.INVALID_FILE_CONTENT_TYPE);
    }

    public List<Image> linkFileToBlog(List<FileDetail> fileDetails, Blog blog) {
        return fileDetails.stream().map(file -> {
            Optional<Image> optImage = imageRepository.findById(file.id());
            return optImage.map(image -> {
                image.setBlog(blog);
                if (!StringUtils.isEmpty(file.description()))
                    image.setDescription(file.description());
                return imageRepository.save(image);
            }).orElseThrow();
        }).toList();
    }
    private String getFileUrl(String key) {
        return String.format("%s/%s", bucketUrl, key).replace(' ', '+');
    }
}
