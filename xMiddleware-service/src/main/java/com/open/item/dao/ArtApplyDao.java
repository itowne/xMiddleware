package com.open.item.dao;

import java.util.List;

import com.open.item.entity.ArtApply;
import com.open.item.entity.Page;

public interface ArtApplyDao extends BaseDao {

    public List<ArtApply> findListByArtId(String artId);

    public Page<ArtApply> findArtApplyPage(Integer start, Integer pagesize, String articleId);

    public ArtApply findByArtcleIdAndMobile(String articleId, String mobile);

}
