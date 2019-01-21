package cm.lx.dao;


import cm.lx.bean.WagesAssist;

import java.util.List;
import java.util.Map;

public interface WagesAssistDao {

    Integer getWagesAssistAutoId();

    Integer insert(WagesAssist wagesAssist);

    WagesAssist getById(Integer id);

    WagesAssist getByCarRecordId(Integer id);

    Integer deleteById(Integer id);

    Integer deleteByMap(Map map);

    Integer updateById(WagesAssist wagesAssist);

    List<WagesAssist> getListByMap(Map map);
}
