package coen.cloud.computing.newsfeed.helper;

import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.nytimes.DocArticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NyArticleMapper {

    public static Article mapToArticle(DocArticle docArticle){
      System.out.println("Received document :"+docArticle);
      Article mappedArticle = new Article();
      mappedArticle.setTitle(docArticle.getHeadline().getMain());
      mappedArticle.setArticleSourceId(docArticle.getWebUrl());

      mappedArticle.setDescription(docArticle.getArticleAbstract());
      mappedArticle.setUrl(docArticle.getWebUrl());
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
      try {
        mappedArticle.setPublishDate(formatter.parse(docArticle.getPub_date()));
      } catch (ParseException e) {
        System.out.println("Unable to parse document date. Setting Date as default");
        mappedArticle.setPublishDate(new Date());
      }
      mappedArticle.setAuthor(docArticle.getByline().getOriginal() == null?
              Constants.DEFAULT_AUTHOR : docArticle.getByline().getOriginal().substring(docArticle.getByline().getOriginal().indexOf(" ") + 1));
      System.out.println("Mapped article :"+mappedArticle);
      return mappedArticle;
    }
}