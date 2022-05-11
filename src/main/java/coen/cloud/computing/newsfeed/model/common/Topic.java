package coen.cloud.computing.newsfeed.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
@AllArgsConstructor
public class Topic {

  int id;
  String topicName;
}
