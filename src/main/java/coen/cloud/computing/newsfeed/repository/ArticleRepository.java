package coen.cloud.computing.newsfeed.repository;

import coen.cloud.computing.newsfeed.model.common.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

  List<Article> findByGeneratedIdIn(List<String> generatedIds);
}

