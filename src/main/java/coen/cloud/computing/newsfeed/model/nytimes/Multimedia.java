package coen.cloud.computing.newsfeed.model.nytimes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Multimedia {

  @JsonProperty("subtype")
  String subType;
  String type;
  String url;
}