package coen.cloud.computing.newsfeed.model.newsapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewsApiResponse {

  private String status;
  private Integer totalResults;
  private String message;
  private List<NewsApiArticle> articles;

}
