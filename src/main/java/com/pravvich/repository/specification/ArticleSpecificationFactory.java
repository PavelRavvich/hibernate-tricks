package com.pravvich.repository.specification;

import com.pravvich.model.Article;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

/**
 * @author Pavel Ravvich.
 */
@Service
public class ArticleSpecificationFactory {

    public Specification<Article> allArticlesWithTag(String tag) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (Objects.nonNull(tag) && !tag.isBlank()) {
                predicate.getExpressions().add(
                        builder.isMember(tag, root.get("tags")));
            }
            return predicate;
        };
    }

    public Specification<Article> byTitle(String title) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (Objects.nonNull(title) && !title.isBlank()) {
                predicate.getExpressions().add(
                        builder.equal(builder.lower(root.get("title")), title));
            }
            return predicate;
        };
    }

    public Specification<Article> byTitlePart(String titlePart) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (Objects.nonNull(titlePart) && !titlePart.isBlank()) {
                String pattern = "%s%%".formatted(titlePart.trim().toLowerCase());
                predicate.getExpressions().add(
                        builder.like(builder.lower(root.get("title")), pattern));
            }
            return predicate;
        };
    }
}
