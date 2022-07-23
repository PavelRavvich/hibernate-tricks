package com.pravvich.service;

import com.pravvich.config.TestConfig;
import com.pravvich.model.Article;
import com.pravvich.model.Author;
import com.pravvich.repository.ArticleRepository;
import com.pravvich.repository.specification.ArticleSpecificationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Pavel Ravvich.
 */
@SpringBootTest(classes = TestConfig.class)
public class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @MockBean
    ArticleRepository articleRepository;

    @MockBean
    ArticleSpecificationFactory articleSpecificationFactory;

    String tag = "tag";
    String title = "title";
    Author author = new Author("name", "login");
    Article article = new Article(1L, author, title, "content", List.of(tag));

    @Test
    public void whenArticleWithTagIsExistThenReturnListArticles() {
        // given
        List<Article> expected = List.of(article);
        Specification<Article> spec = new ArticleSpecificationFactory().allArticlesWithTag(tag);
        given(articleSpecificationFactory.allArticlesWithTag(tag)).willReturn(spec);
        given(articleRepository.findAll(spec)).willReturn(expected);

        // when
        List<Article> result = articleService.findAllArticlesWithTag(tag);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findAll(spec);
        verify(articleSpecificationFactory, times(1)).allArticlesWithTag(tag);
    }

    @Test
    public void whenArticleWithTitleIsExistThenReturnArticlesList() {
        // given
        Specification<Article> spec = new ArticleSpecificationFactory().byTitle(title);
        given(articleSpecificationFactory.byTitle(title)).willReturn(spec);
        given(articleRepository.findOne(spec)).willReturn(Optional.of(article));

        // when
        Article result = articleService.findArticleByTitle(title);

        // then
        Assertions.assertEquals(article, result);
        verify(articleRepository, times(1)).findOne(spec);
        verify(articleSpecificationFactory, times(1)).byTitle(title);
    }

    @Test
    public void whenArticleWithTitleNotExistThenThrowNoSuchElementException() {
        // given
        Specification<Article> spec = new ArticleSpecificationFactory().byTitle(title);
        given(articleSpecificationFactory.byTitle(title)).willReturn(spec);
        given(articleRepository.findOne(spec)).willReturn(Optional.empty());

        // then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> // given
                        articleService.findArticleByTitle(title));
    }

    @Test
    public void whenArticleWithTitlePartIsExistThenReturnArticlesList() {
        // given
        List<Article> expected = List.of(article);
        Specification<Article> spec = new ArticleSpecificationFactory().byTitlePart(title);
        given(articleSpecificationFactory.byTitlePart(title)).willReturn(spec);
        given(articleRepository.findAll(spec)).willReturn(expected);

        // when
        List<Article> result = articleService.findArticlesByTitlePart(title);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findAll(spec);
        verify(articleSpecificationFactory, times(1)).byTitlePart(title);
    }

    @Test
    public void whenArticlesWrittenByAuthorIsExistThenReturnArticles() {
        // given
        List<Article> expected = List.of(article);
        given(articleRepository.findAllWrittenBy(author)).willReturn(expected);

        // when
        List<Article> result = articleService.findAllWrittenBy(author);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findAllWrittenBy(author);
    }

    @Test
    public void whenArticleWithTitleHasTagsReturnTagList() {
        // given
        List<String> expected = List.of("tag");
        given(articleRepository.findAllTagsOfArticle(title)).willReturn(expected);

        // when
        List<String> result = articleService.findAllTagsOfArticle(title);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findAllTagsOfArticle(title);
    }

    @Test
    public void whenArticlesWrittenByAuthorOfWithTitleIsExistThenReturnArticleList() {
        // given
        List<Article> expected = List.of(article);
        given(articleRepository.findByAuthorOrTitle(author, title)).willReturn(expected);

        // when
        List<Article> result = articleService.findByAuthorOrTitle(author, title);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findByAuthorOrTitle(author, title);
    }

    @Test
    public void whenArticleWithTitleIsExistReturnAuthor() {
        // given
        Author expected = new Author(article.getAuthor().getName(), article.getAuthor().getLogin());
        given(articleRepository.findAuthorOfArticleByTitle(title)).willReturn(Optional.of(expected));

        // when
        Author result = articleService.findAuthorOfArticleByTitle(title);

        // then
        Assertions.assertEquals(expected, result);
        verify(articleRepository, times(1)).findAuthorOfArticleByTitle("title");
    }

    @Test
    public void whenArticleWithTitleNotExistThrowNoSuchElementException() {
        // given
        given(articleRepository.findAuthorOfArticleByTitle(title)).willReturn(Optional.empty());

        // then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> // given
                        articleService.findAuthorOfArticleByTitle(title));
    }
}