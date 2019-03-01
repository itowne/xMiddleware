package com.open.item.service;

import java.util.List;

import com.open.item.entity.Article;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.BooleanEnum;
import com.open.item.entity.enumObject.ViewTypeEnum;

public interface ArticleService {

    public void save(Article article);

    public Page<Article> findArtPage(Integer start, Integer pagesize);

    public Article findById(String id);

    public void delArticle(Article article);

    public void update(Article article);

    public List<Article> findAll();

    public List<Article> findByIsTop(BooleanEnum isTop);

    public List<Article> findByType(ViewTypeEnum vte);

    public Page<Article> findArtPage4View(Integer start, Integer pagesize, ViewTypeEnum vte);
}
