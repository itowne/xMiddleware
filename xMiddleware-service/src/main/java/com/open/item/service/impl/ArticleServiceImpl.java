package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.ArticleDao;
import com.open.item.entity.Article;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.BooleanEnum;
import com.open.item.entity.enumObject.ViewTypeEnum;
import com.open.item.service.ArticleService;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource(name = "articleDao")
    private ArticleDao articleDao;

    @Transactional
    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> findArtPage(Integer start, Integer pagesize) {
        return articleDao.findArtPage(start, pagesize);
    }

    @Transactional(readOnly = true)
    @Override
    public Article findById(String id) {
        return articleDao.findById(id);
    }

    @Transactional
    @Override
    public void delArticle(Article article) {
        articleDao.delete(article);
    }

    @Transactional
    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> findAll() {
        return articleDao.findArtList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> findByType(ViewTypeEnum vte) {
        return articleDao.findByType(vte);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> findArtPage4View(Integer start, Integer pagesize, ViewTypeEnum vte) {
        return articleDao.findArtPage4View(start, pagesize, vte);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> findByIsTop(BooleanEnum isTop) {
        return articleDao.findByIsTop(isTop);
    }

}
