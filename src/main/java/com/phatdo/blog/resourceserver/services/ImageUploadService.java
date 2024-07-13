package com.phatdo.blog.resourceserver.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.phatdo.blog.resourceserver.configuration.properties.GoogleDriveProperties;
import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.images.Image;
import com.phatdo.blog.resourceserver.repositories.ImageRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
@Service
@Slf4j
public class ImageUploadService {
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String SERVICE_ACCOUNT_KEY_PATH;
    private final List<String> acceptedContentTypes = List.of("image/jpg", "image/jpeg", "image/png");

    private final ImageRepository imageRepository;
    private final GoogleDriveProperties properties;

    public ImageUploadService(ImageRepository imageRepository, GoogleDriveProperties properties) {
        this.imageRepository = imageRepository;
        this.properties = properties;
        SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();
    }

    private String getPathToGoogleCredentials() {
        String currentDir = System.getProperty("user.dir");
        Path filePath = Path.of(currentDir, properties.getCredential());
        return filePath.toString();
    }

    public CompletableFuture<List<Image>> upload(Blog blog, List<MultipartFile> files) throws GeneralSecurityException, IOException, CustomException {
        List<CompletableFuture<Image>> futures = new ArrayList<>();
        for (MultipartFile file : files)
            futures.add(uploadAsync(file, blog));
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).toList());
    }

    @Async
    public CompletableFuture<Image> uploadAsync(MultipartFile file, Blog blog) throws IOException, GeneralSecurityException, CustomException {
        if (acceptedContentTypes.contains(file.getContentType())) {
            java.io.File tempFile = convertMultipartFileToFile(file);
            try {
                Drive drive = createDriveService();
                File fileMetaData = new File();
                fileMetaData.setName(file.getOriginalFilename());
                fileMetaData.setParents(Collections.singletonList(properties.getFolderId()));

                FileContent mediaContent = new FileContent(file.getContentType(), tempFile);

                File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                        .setFields("id")
                        .execute();
                String imageURL = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
                return CompletableFuture.completedFuture(imageRepository.save(new Image(imageURL, blog)));
            } finally {
                if (tempFile.exists())
                    tempFile.delete();
            }
        } else
            throw new CustomException(CustomError.INVALID_FILE_CONTENT_TYPE);
    }

    private Drive createDriveService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).build();
    }

    private java.io.File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        java.io.File convFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }

}
