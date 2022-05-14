package coen.cloud.computing.newsfeed.repository;

import coen.cloud.computing.newsfeed.model.common.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> { }
