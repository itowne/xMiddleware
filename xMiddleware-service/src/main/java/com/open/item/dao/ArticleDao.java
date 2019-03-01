package com.open.item.dao;

import java.util.List;

import com.open.item.entity.Article;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.BooleanEnum;
import com.open.item.entity.enumObject.ViewTypeEnum;

public interface ArticleDao extends BaseDao {
    public Page<Article> findArtPage(Integer start, Integer pagesize);

    public Page<Article> findArtPage4View(Integer start, Integer pagesize, ViewTypeEnum vte);

    public List<Article> findArtList();

    public Article findById(String id);

    public List<Article> findByIsTop(BooleanEnum isTop);

    public List<Article> findByType(ViewTypeEnum vte);
}
