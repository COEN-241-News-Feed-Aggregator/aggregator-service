package coen.cloud.computing.newsfeed.model.common;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String author;
  @Column(name = "publish_date")
  private Date publishDate;
  private String description;
  @Column(name = "web_url")
  private String webUrl;
  @Column(name = "source_id")
  private int sourceId;
  @Column(name = "unique_generated_id")
  private String generatedId;
  @Column(name = "created_at")
  private Date createdAt;
}
