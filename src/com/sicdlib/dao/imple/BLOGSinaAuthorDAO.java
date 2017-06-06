package com.sicdlib.dao.imple;

import com.sicdlib.dao.IBLOGSinaAuthorDAO;
import com.sicdlib.dao.IBaseDAO;
import com.sicdlib.dto.entity.BlogSinaAuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by init on 2017/6/3.
 */
@Repository("blogSinaAuthorDAO")
public class BLOGSinaAuthorDAO  implements IBLOGSinaAuthorDAO{
    @Autowired
    private IBaseDAO baseDAO;
    @Override
    public Boolean saveBLOGSinaAuthor(BlogSinaAuthorEntity blogSinaAuthor) {
        try{
                baseDAO.save(blogSinaAuthor);
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
