package com.sicdlib.service;

import com.sicdlib.dto.DoubanGroupAuthorEntity;
import com.sicdlib.dto.UserEntity;

public interface IDoubanGroupAuthorService {
    //新增or更新豆瓣小组作者
    public boolean saveDoubanGroupAuthor(DoubanGroupAuthorEntity doubanGroupAuthor);
}