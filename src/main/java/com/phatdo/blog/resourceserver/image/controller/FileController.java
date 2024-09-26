package com.phatdo.blog.resourceserver.image.controller;

import com.phatdo.blog.resourceserver.image.service.FileService;
import com.phatdo.blog.resourceserver.image.service.ResizeImageService;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.phatdo.blog.resourceserver.utils.commons.exception.CustomError.INVALID_FILE_CONTENT_TYPE;
import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_IMAGE;

/**
 * Image controller for managing files
 * @author phatdo 15/09/2024
 */
@RestController
@RequestMapping(path = API_IMAGE)
public class FileController {
    private final FileService fileService;
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public FileController(FileService fileService, DTOMapperFactory mapperFactory) {
        this.fileService = fileService;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping(value = "/upload_multiple", consumes = "multipart/form-data")
    public ResponseEntity<List<TypeDTO>> uploadFiles(@ModelAttribute List<MultipartFile> files) throws CustomException, IOException {
        if (files == null || files.isEmpty()) {
            throw new CustomException(INVALID_FILE_CONTENT_TYPE);
        }
        return ResponseEntity.ok(fileService.upload(files).join().stream()
                .map(mapperFactory.getMapper(DTOMapperE.FILE_DETAIL)::toDTO)
                .toList());
    }
}