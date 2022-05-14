package coen.cloud.computing.newsfeed.model.common;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Topic_article_mapping")
public class TopicArticleMapping {

  public TopicArticleMapping(int topicId, int articleId){
    this.topicId=topicId;
    this.articleId=articleId;
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "topic_id")
  private  int topicId;
  @Column(name = "article_id")
  private  int articleId;
}
