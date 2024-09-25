package com.phatdo.blog.resourceserver.image.service;

import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.phatdo.blog.resourceserver.utils.commons.exception.CustomError.INVALID_FILE_CONTENT_TYPE;

@Service
public class ResizeImageService {

    /**
     *
     * @return An resized image
     */
    public byte[] resize(MultipartFile imageFile) throws CustomException, IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

        if (bufferedImage == null) {
            throw new CustomException(INVALID_FILE_CONTENT_TYPE);
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        int resizedWidth;
        int resizeHeight;

        if (width > height && width > 200) {
            resizedWidth = 200;
            resizeHeight = (200 * height) / width;
        } else if (width < height && height > 200) {
            resizeHeight = 200;
            resizedWidth = (200 * width) / height;
        } else
            return imageFile.getBytes();

        try (InputStream inputStream = imageFile.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            String contentType = Objects.requireNonNull(imageFile.getContentType()).replace("image/", "");
            Thumbnails.of(inputStream)
                    .size(resizedWidth, resizeHeight)
                    .outputFormat(contentType)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        }
    }
}
