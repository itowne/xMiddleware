package com.open.item.service;

import com.open.item.entity.Access;

public interface AccessService {

    public void save(Access access);

    public void update(Access access);

    public int findAccessCountByArtId(String articleId);

}
