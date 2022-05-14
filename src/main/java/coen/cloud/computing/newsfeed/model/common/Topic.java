package coen.cloud.computing.newsfeed.model.common;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "Topics")
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "Name")
  private  String topicName;
}
