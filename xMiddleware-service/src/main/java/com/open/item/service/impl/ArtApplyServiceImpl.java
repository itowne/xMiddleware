package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.ArtApplyDao;
import com.open.item.entity.ArtApply;
import com.open.item.entity.Page;
import com.open.item.service.ArtApplyService;

@Service("artApplyService")
public class ArtApplyServiceImpl implements ArtApplyService {

    @Resource(name = "artApplyDao")
    private ArtApplyDao artApplyDao;

    @Transactional
    @Override
    public void save(ArtApply artApply) {
        artApplyDao.save(artApply);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArtApply> findListByArtId(String artId) {
        return artApplyDao.findListByArtId(artId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArtApply> findArtApplyPage(Integer start, Integer pagesize, String articleId) {
        return artApplyDao.findArtApplyPage(start, pagesize, articleId);
    }

    @Transactional(readOnly = true)
    @Override
    public ArtApply findByArtcleIdAndMobile(String articleId, String mobile) {
        return artApplyDao.findByArtcleIdAndMobile(articleId, mobile);
    }

}
