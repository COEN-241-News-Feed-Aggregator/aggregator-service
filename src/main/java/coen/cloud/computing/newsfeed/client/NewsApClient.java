package coen.cloud.computing.newsfeed.client;

import coen.cloud.computing.newsfeed.config.ApplicationConfiguration;
import coen.cloud.computing.newsfeed.helper.ArticleMapper;
import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.newsapi.NewsApiResponse;
import coen.cloud.computing.newsfeed.model.nytimes.NyTimesApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NewsApClient {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ApplicationConfiguration appConfig;


  public List<Article> getArticlesForTopic(String topicName) {
    System.out.println("Fetching all articles from NewsApi for topicName " + topicName);
    List<Article> result = new ArrayList<>();
    String uri = buildUrl(topicName).toString();
    System.out.println("Url formed: " + uri);
    ResponseEntity<NewsApiResponse> responseEntity = restTemplate.getForEntity(uri, NewsApiResponse.class);
    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
      System.out.println("Error in fetching details from News API times");
      if (responseEntity.getBody() != null && responseEntity.getBody().getStatus() != null)
        System.out.println("Error returned: " + responseEntity.getBody().getMessage());
      return result;
    }
    responseEntity.getBody().getArticles().forEach(a -> result.add(ArticleMapper.mapNewsApiArticleToArticle(a)));
    System.out.println("Fetched " + result.size() + " articles for topic " + topicName);
    return result;
  }

  private URI buildUrl(String query) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(appConfig.getNewsApi().getBaseUrl());
    for (Map.Entry<String, String> queryParam : appConfig.getNewsApi().getQueryParameters().entrySet()) {
      builder.queryParam(queryParam.getKey(), queryParam.getValue());
    }
    builder.queryParam("q", query);
    return builder.build().toUri();
  }


}
