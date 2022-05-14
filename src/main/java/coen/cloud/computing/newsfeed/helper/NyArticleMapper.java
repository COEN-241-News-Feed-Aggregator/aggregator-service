package coen.cloud.computing.newsfeed.helper;

import coen.cloud.computing.newsfeed.model.common.Article;
import coen.cloud.computing.newsfeed.model.nytimes.DocArticle;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class NyArticleMapper {

    public static Article mapToArticle(DocArticle docArticle){
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
      StringBuilder sb = new StringBuilder(mappedArticle.getTitle())
              .append("#").append(mappedArticle.getAuthor())
              .append("#").append(mappedArticle.getPublishDate().toString());
      mappedArticle.setGeneratedId(getHashedId(sb.toString()));
      mappedArticle.setSourceId(1);
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
