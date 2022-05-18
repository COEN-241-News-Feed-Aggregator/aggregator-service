package coen.cloud.computing.newsfeed.service;

import coen.cloud.computing.newsfeed.client.NewsApClient;
import coen.cloud.computing.newsfeed.client.NyTimesApiClient;
import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.common.Topic;
import coen.cloud.computing.newsfeed.model.common.TopicArticleMapping;
import coen.cloud.computing.newsfeed.repository.ArticleRepository;
import coen.cloud.computing.newsfeed.repository.TopicArticleMappingRepository;
import coen.cloud.computing.newsfeed.repository.TopicRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NyTimesArticleAggregator implements ArticleListAggregator {

  @Autowired
  private NyTimesApiClient nyTimesClient;

  @Autowired
  private NewsApClient newsApiClient;

  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private TopicArticleMappingRepository topicArticleMappingRepository;

  @Override
  public void upsertArticlesInDatabase() {

    List<Topic> topics = getAllTopics();

    List<Article> allArticles = new ArrayList<>();
    for (Topic topic : topics) {
      allArticles.addAll(nyTimesClient.getArticlesForTopic(topic.getTopicName()));

      allArticles.addAll(newsApiClient.getArticlesForTopic(topic.getTopicName()));

      List<Article> existingArticles = articleRepository.findByGeneratedIdIn(allArticles.stream().map(Article::getGeneratedId).collect(Collectors.toList()));
      List<String> existingArticleGeneratedIds = existingArticles.stream().map(Article::getGeneratedId).collect(Collectors.toList());

      List<Article> freshArticles = allArticles.stream().filter(a -> !existingArticleGeneratedIds.contains(a.getGeneratedId())).collect(Collectors.toList());

      System.out.println("Persisting fresh articles");
      articleRepository.saveAll(freshArticles);
      topicArticleMappingRepository.saveAll(freshArticles.stream()
              .map(article -> new TopicArticleMapping(topic.getId(), article.getId())).collect(Collectors.toList()));
      System.out.println(freshArticles.size() + " articles added to topic "+topic.getTopicName());

      System.out.println("Updating existing article associations");
      existingArticles.forEach(article -> {
        try {
          topicArticleMappingRepository.save(new TopicArticleMapping(topic.getId(), article.getId()));
        }
        catch (ConstraintViolationException constraintViolationException){
          System.out.println("Association already exists: "+ constraintViolationException.getMessage());
        }
        catch (Exception exception){
          System.out.println("Exception in adding association: "+ exception.getMessage());
        }
      });

    }
  }


  public List<Topic> getAllTopics() {
    List<Topic> topics = topicRepository.findAll();
    System.out.println("All topics: " + topics);
    return topics;
  }
}
