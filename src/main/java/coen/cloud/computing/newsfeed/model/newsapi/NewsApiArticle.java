package coen.cloud.computing.newsfeed.model.newsapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter @ToString
public class NewsApiArticle {
  private String author;
  private String title;
  private String description;
  @JsonProperty("url")
  private String webUrl;
  private String publishedAt;
  @JsonProperty("urlToImage")
  private String imageUrl;
}
