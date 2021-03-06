package com.sicdlib.dao.imple;

import com.sicdlib.dao.IBaseDAO;
import com.sicdlib.dao.IHotWordsDAO;
import com.sicdlib.dto.TbHotWordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YH on 2017/6/1.
 */
@Repository("hotWordsDAO")
public class HotWordsDAO implements IHotWordsDAO {
    @Autowired
    @Qualifier("baseDAO")
    private IBaseDAO baseDAO;

    @Override
    public List<TbHotWordEntity> getHotWordsEntityList(String eventID, int limit) {
        String hql = "FROM TbHotWordEntity hotWords WHERE hotWords.eventId = '" + eventID + "' order by hotWords.heat desc";

        if (limit != 0) {
            return baseDAO.find(hql, 0, limit);
        }
        return baseDAO.find(hql);
    }

    @Override
    public void batchSaveHotWords(List<TbHotWordEntity> hotWordsList) {
        baseDAO.batchSave(hotWordsList);
    }

    @Override
    public void saveHotWords(TbHotWordEntity hotWords) {
        baseDAO.save(hotWords);
    }
}
