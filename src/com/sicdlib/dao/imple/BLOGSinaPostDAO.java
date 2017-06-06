package com.sicdlib.dao.imple;

import com.sicdlib.dao.IBLOGSinaPostDAO;
import com.sicdlib.dao.IBaseDAO;
import com.sicdlib.dto.entity.BlogSinaPostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by init on 2017/6/3.
 */
@Repository("blogSinaPostDAO")
public class BLOGSinaPostDAO  implements IBLOGSinaPostDAO{
    @Autowired
    private IBaseDAO baseDAO;
    @Override
    public Boolean saveBLOGSinaPost(BlogSinaPostEntity blogSinaPost) {
      try{
          baseDAO.save(blogSinaPost);
          return true;
      }catch (Exception e){
          e.printStackTrace();
          return false;
      }
    }
}
