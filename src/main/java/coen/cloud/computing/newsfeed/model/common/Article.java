package coen.cloud.computing.newsfeed.model.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter @Setter
public class Article {

  private String title;
  private String author;
  private Date publishDate;
  private String description;
  private String url;
  private String articleSourceId;
}
