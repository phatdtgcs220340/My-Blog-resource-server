package com.phatdo.blog.resourceserver.models.blogs;

import com.phatdo.blog.resourceserver.models.tags.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BlogSpecs {
    public static Specification<Blog> filterByName(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.isEmpty(name))
                return cb.like(root.get("title"), "%" + name + "%");
            else
                return cb.conjunction();
        };
    }

    public static Specification<Blog> filterByTags(List<String> tags) {
        return (root, query, cb) -> {
            if (tags == null || tags.isEmpty())
                return cb.conjunction();
            Join<Blog, Tag> tagJoin = root.join("tags", JoinType.INNER);
            return tagJoin.get("name").in(tags);
        };
    }

    public static Specification<Blog> orderByCreatedDate(Sort.Direction direction) {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class) {
                query.orderBy(direction == null || direction.isAscending()
                        ? cb.asc(root.get("createdDate"))
                        : cb.desc(root.get("createdDate")));
            }
            return cb.conjunction();
        };
    }
}
