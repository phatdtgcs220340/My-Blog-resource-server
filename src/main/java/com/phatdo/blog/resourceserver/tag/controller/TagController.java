package com.phatdo.blog.resourceserver.tag.controller;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_TAG;


@RestController
@RequestMapping(path = API_TAG)
public class TagController {
    private TagService tagService;
    private DTOMapperFactory factory;

    public TagController(TagService tagService, DTOMapperFactory factory) {
        this.tagService = tagService;
        this.factory = factory;
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Page<TypeDTO>> findBySearch(@RequestParam String name,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(tagService.findBySearchName(name, pageRequest)
                .map(factory.getMapper(DTOMapperE.TAG)::toDTO));
    }

    @GetMapping
    public ResponseEntity<Page<TypeDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(tagService.findAll(pageRequest)
                .map(factory.getMapper(DTOMapperE.TAG)::toDTO));
    }
}
