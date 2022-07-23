package com.pravvich.repository;

import com.pravvich.model.Article;
import com.pravvich.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Ravvich.
 */
@Repository
public interface ArticleRepository
        extends JpaRepository<Article, Long>,
        JpaSpecificationExecutor<Article> {

    @Query("SELECT a FROM Article a WHERE a.author = :author")
    List<Article> findAllWrittenBy(Author author);

    @Query("SELECT t FROM Article a JOIN a.tags t WHERE a.title = :title")
    List<String> findAllTagsOfArticle(String title);

    @Query("SELECT a FROM Article a WHERE a.author = :author OR a.title = :title")
    List<Article> findByAuthorOrTitle(Author author, String title);

    @Query("SELECT a.author FROM Article a WHERE a.title = :title")
    Optional<Author> findAuthorOfArticleByTitle(String title);
}
