package com.pravvich.service;

import com.pravvich.model.Article;
import com.pravvich.model.Author;
import com.pravvich.repository.ArticleRepository;
import com.pravvich.repository.specification.ArticleSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Pavel Ravvich.
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleSpecificationFactory articleSpecificationFactory;

    @Override
    public List<Article> findAllArticlesWithTag(String tag) {
        return articleRepository
                .findAll(articleSpecificationFactory.allArticlesWithTag(tag));
    }

    @Override
    public Article findArticleByTitle(String title) {
        return articleRepository
                .findOne(articleSpecificationFactory.byTitle(title))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Article> findArticlesByTitlePart(String titlePart) {
        return articleRepository
                .findAll(articleSpecificationFactory.byTitlePart(titlePart));
    }

    @Override
    public List<Article> findAllWrittenBy(Author author) {
        return articleRepository.findAllWrittenBy(author);
    }

    @Override
    public List<String> findAllTagsOfArticle(String title) {
        return articleRepository.findAllTagsOfArticle(title);
    }

    @Override
    public List<Article> findByAuthorOrTitle(Author author, String title) {
        return articleRepository.findByAuthorOrTitle(author, title);
    }

    @Override
    public Author findAuthorOfArticleByTitle(String title) {
        return articleRepository
                .findAuthorOfArticleByTitle(title)
                .orElseThrow(NoSuchElementException::new);
    }
}
