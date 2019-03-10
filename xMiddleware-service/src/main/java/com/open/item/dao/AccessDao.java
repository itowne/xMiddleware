package com.open.item.dao;

public interface AccessDao extends BaseDao {

    public int findAccessCountByArtId(String articleId);

}
