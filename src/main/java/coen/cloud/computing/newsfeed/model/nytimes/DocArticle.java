package coen.cloud.computing.newsfeed.model.nytimes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class DocArticle {

  @JsonProperty("abstract")
  String articleAbstract;
  @JsonProperty("web_url")
  String webUrl;
  String pub_date;
  @JsonProperty("_id")
  String articleSourceId;
  Byline byline;
  Headline headline;
  List<Multimedia> multimedia;

}
