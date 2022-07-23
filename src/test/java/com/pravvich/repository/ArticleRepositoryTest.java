package com.pravvich.repository;

import com.pravvich.config.TestConfig;
import com.pravvich.model.Article;
import com.pravvich.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Ravvich.
 */
@SpringBootTest(classes = TestConfig.class)
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    Article article;

    Author author;

    @BeforeEach
    public void before() {
        article = new Article();
        article.setTitle("title");
        article.setContent("content");
        article.setTags(List.of("tag"));
        article.setAuthor(author =
                new Author("name", "login"));
        articleRepository.deleteAll();
    }

    @Test
    public void whenArticleWrittenByAuthorIsExistThenReturnArticleList() {
        // given
        Article expected = articleRepository.save(article);

        // when
        List<Article> result = articleRepository.findAllWrittenBy(author);

        // then
        Assertions.assertEquals(expected.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(expected.getAuthor(), result.get(0).getAuthor());
        Assertions.assertEquals(expected.getContent(), result.get(0).getContent());
        Assertions.assertIterableEquals(expected.getTags(), result.get(0).getTags());
    }

    @Test
    public void whenArticleWithTitleHasTagsThenReturnTags() {
        // given
        Article expected = articleRepository.save(article);

        // when
        List<String> result = articleRepository.findAllTagsOfArticle(article.getTitle());

        // then
        Assertions.assertIterableEquals(expected.getTags(), result);
    }

    @Test
    public void whenArticleWrittenByAuthorOrWithTitleIsExistThenReturnArticleList() {
        // given
        List<Article> expected = articleRepository.saveAll(List.of(article));

        // when
        List<Article> result = articleRepository.findByAuthorOrTitle(author, article.getTitle());

        // then
        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expected.get(0).getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(expected.get(0).getAuthor(), result.get(0).getAuthor());
        Assertions.assertEquals(expected.get(0).getContent(), result.get(0).getContent());
        Assertions.assertIterableEquals(expected.get(0).getTags(), result.get(0).getTags());
    }

    @Test
    public void whenArticleWithTitleIsExistThenReturnAuthor() {
        // given
        Article expected = articleRepository.save(article);

        // when
        Optional<Author> result = articleRepository.findAuthorOfArticleByTitle(article.getTitle());

        Assertions.assertEquals(Optional.of(expected.getAuthor()), result);
    }
}