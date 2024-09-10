package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.dto.requests.BlogFilter;
import com.phatdo.blog.resourceserver.dto.requests.CreateBlogDTO;
import com.phatdo.blog.resourceserver.dto.requests.UpdateBlogDTO;
import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.blogs.BlogSpecs;
import com.phatdo.blog.resourceserver.models.tags.Tag;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import com.phatdo.blog.resourceserver.repositories.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    public BlogService(BlogRepository blogRepository, TagRepository tagRepository) {
        this.blogRepository = blogRepository;
        this.tagRepository = tagRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog saveBlog(CreateBlogDTO form, User user) {
        Blog blog = new Blog(user);
        List<Tag> existedTag = tagRepository.findByListName(form.tags());
        List<String> existedTagString = existedTag.stream().map(Tag::getName).toList();
        form.tags().stream()
                .filter(t -> !existedTagString.contains(t))
                .forEach(tag -> existedTag.add(tagRepository.save(new Tag(tag))));
        blog.getTags().addAll(existedTag);
        blog.setTitle(form.title());
        blog.setContent(form.content());
        user.getBlogs().add(blog);

        return blogRepository.save(blog);
    }

    public Blog getBlog(Long id) throws CustomException {
        return blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }

    public Page<Blog> getAllBlogs(BlogFilter filter, Pageable pageable) {
        Specification<Blog> specs = BlogSpecs.filterByName(filter.name())
                .and(BlogSpecs.filterByTags(filter.tags()))
                .and(BlogSpecs.orderByCreatedDate(filter.direction()));
        return blogRepository.findAll(specs, pageable);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog updateBlog(Long id,UpdateBlogDTO dto) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(id);
        if (optBlog.isPresent()) {
            Blog blog = optBlog.get();
            if (!dto.title().isEmpty())
                blog.setTitle(dto.title());
            if (!dto.content().isEmpty())
                blog.setContent(dto.content());
            return blogRepository.save(blog);
        }
        else
            throw new CustomException(CustomError.BLOG_NOT_FOUND);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteBlog(Long id, User user) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(id);
        if (optBlog.isPresent()) {
            user.getBlogs().remove(optBlog.get());
            blogRepository.deleteById(id);
        }
        else
            throw new CustomException(CustomError.BLOG_NOT_FOUND);
    }
}
