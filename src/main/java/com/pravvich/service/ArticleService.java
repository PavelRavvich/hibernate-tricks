package com.pravvich.service;

import com.pravvich.model.Article;
import com.pravvich.model.Author;

import java.util.List;

/**
 * @author Pavel Ravvich.
 */
public interface ArticleService {

    List<Article> findAllArticlesWithTag(String tag);

    Article findArticleByTitle(String title);

    List<Article> findArticlesByTitlePart(String titlePart);

    List<Article> findAllWrittenBy(Author author);

    List<String> findAllTagsOfArticle(String title);

    List<Article> findByAuthorOrTitle(Author author, String title);

    Author findAuthorOfArticleByTitle(String title);
}
