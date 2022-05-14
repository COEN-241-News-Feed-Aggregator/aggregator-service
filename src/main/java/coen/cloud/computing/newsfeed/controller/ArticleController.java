package coen.cloud.computing.newsfeed.controller;

import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.service.ArticleListAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

  @Autowired
  private ArticleListAggregator aggregator;

  @GetMapping("/articles")
  public void getAllArticles() {
    aggregator.upsertArticlesInDatabase();
  }
}
