package coen.cloud.computing.newsfeed.repository;

import coen.cloud.computing.newsfeed.model.common.TopicArticleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicArticleMappingRepository extends JpaRepository<TopicArticleMapping, Integer> {}

