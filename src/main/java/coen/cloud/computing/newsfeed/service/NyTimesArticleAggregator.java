package coen.cloud.computing.newsfeed.service;

import coen.cloud.computing.newsfeed.client.NyTimesApiClient;
import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.common.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Component
public class NyTimesArticleAggregator implements ArticleListAggregator {

  @Autowired
  private NyTimesApiClient nyTimesClient;

  @Override
  public List<Article> getAllArticles() {
    List<Topic> topics = getAllTopics();

    List<Article> result = new ArrayList<>();
    for (Topic topic : topics)
      result.addAll(nyTimesClient.getArticlesForTopic(topic.getTopicName()));
    return result.stream().sorted((a1, a2) -> a2.getPublishDate().compareTo(a1.getPublishDate()))
            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Article::getArticleSourceId))),
                    ArrayList::new));

  }

  public List<Topic> getAllTopics() {
    List<Topic> topics = new ArrayList<>();
    topics.add(new Topic(1, "Technology"));
    topics.add(new Topic(2, "Science"));
    topics.add(new Topic(3, "Politics"));
    topics.add(new Topic(4, "Sports"));
    topics.add(new Topic(5, "Arts"));
    topics.add(new Topic(6, "Movies"));
    return topics;
  }
}
