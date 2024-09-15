package com.phatdo.blog.resourceserver.image.controller;

import com.phatdo.blog.resourceserver.image.service.FileService;
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

import java.util.List;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_IMAGE;

/**
 * Image controller for managing files
 * @author phatdo 15/09/2024
 */
@RestController
@RequestMapping(path = API_IMAGE)
public class FileController {
    private final FileService imageService;
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public FileController(FileService imageService, DTOMapperFactory mapperFactory) {
        this.imageService = imageService;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping(value = "/upload_multiple", consumes = "multipart/form-data")
    public ResponseEntity<List<TypeDTO>> uploadFiles(@ModelAttribute List<MultipartFile> files) throws CustomException {
        return ResponseEntity.ok(imageService.upload(files).join().stream()
                .map(mapperFactory.getMapper(DTOMapperE.FILE_DETAIL)::toDTO)
                .toList());
    }
}