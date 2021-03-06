package coen.cloud.computing.newsfeed.helper;

import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.newsapi.NewsApiArticle;
import coen.cloud.computing.newsfeed.model.nytimes.DocArticle;
import coen.cloud.computing.newsfeed.model.nytimes.Multimedia;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class ArticleMapper {

    public static Article mapNyTimesToArticle(DocArticle docArticle){
      System.out.println("Received document :"+docArticle);
      Article mappedArticle = new Article();
      mappedArticle.setTitle(docArticle.getHeadline().getMain());


      mappedArticle.setDescription(docArticle.getArticleAbstract());
      mappedArticle.setWebUrl(docArticle.getWebUrl());
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
      try {
        mappedArticle.setPublishDate(formatter.parse(docArticle.getPub_date()));
      } catch (ParseException e) {
        System.out.println("Unable to parse document date. Setting Date as default");
        mappedArticle.setPublishDate(new Date());
      }
      mappedArticle.setAuthor(docArticle.getByline().getOriginal() == null?
              Constants.DEFAULT_AUTHOR : docArticle.getByline().getOriginal().substring(docArticle.getByline().getOriginal().indexOf(" ") + 1));
      Optional<String> imageUrl = docArticle.getMultimedia().stream().filter(multimedia -> multimedia.getType().equalsIgnoreCase("image")
              && multimedia.getSubType().equalsIgnoreCase("xlarge")).findFirst().map(Multimedia::getUrl);
      if(imageUrl.isPresent()){
        StringBuilder sb = new StringBuilder("https://static01.nyt.com/");
        sb.append(imageUrl.get());
        mappedArticle.setImageUrl(sb.toString());
      } else
      {
        mappedArticle.setImageUrl(Constants.DEFAULT_IMAGE_URL);
      }
      StringBuilder sb = new StringBuilder(mappedArticle.getTitle())
              .append("#").append(mappedArticle.getAuthor())
              .append("#").append(mappedArticle.getPublishDate().toString());
      mappedArticle.setGeneratedId(getHashedId(sb.toString()));
      mappedArticle.setSourceId(1);
      mappedArticle.setCreatedAt(new Date());
      System.out.println("Mapped article :"+mappedArticle);
      return mappedArticle;
    }

  public static Article mapNewsApiArticleToArticle(NewsApiArticle newsApiArticle){
    System.out.println("Received document :"+newsApiArticle);
    Article mappedArticle = new Article();
    mappedArticle.setTitle(newsApiArticle.getTitle());
    mappedArticle.setDescription(newsApiArticle.getDescription());
    mappedArticle.setWebUrl(newsApiArticle.getWebUrl());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
    try {
      formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      mappedArticle.setPublishDate(formatter.parse(newsApiArticle.getPublishedAt()));
    } catch (ParseException e) {
      System.out.println("Unable to parse document date. Setting Date as default");
      mappedArticle.setPublishDate(new Date());
    }
    mappedArticle.setImageUrl(newsApiArticle.getImageUrl() == null || newsApiArticle.
            getImageUrl().trim().isEmpty()?Constants.DEFAULT_IMAGE_URL: newsApiArticle.getImageUrl());
    mappedArticle.setAuthor(newsApiArticle.getAuthor() == null || newsApiArticle.getAuthor().trim().isEmpty()?
            Constants.DEFAULT_AUTHOR : newsApiArticle.getAuthor());
    StringBuilder sb = new StringBuilder(mappedArticle.getTitle())
            .append("#").append(mappedArticle.getAuthor())
            .append("#").append(mappedArticle.getPublishDate().toString());
    mappedArticle.setGeneratedId(getHashedId(sb.toString()));
    mappedArticle.setSourceId(22);
    mappedArticle.setCreatedAt(new Date());
    System.out.println("Mapped article :"+mappedArticle);
    return mappedArticle;
  }

    public static String getHashedId(String input){
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedString = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(hashedString).toUpperCase();
      } catch (NoSuchAlgorithmException e) {
        return input;
      }
    }
}
